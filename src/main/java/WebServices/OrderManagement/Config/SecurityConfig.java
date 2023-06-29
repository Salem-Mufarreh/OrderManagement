package WebServices.OrderManagement.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

private final JwtAuthenticationFilter JwtAuthenticationFilter;

    public SecurityConfig(WebServices.OrderManagement.Config.JwtAuthenticationFilter jwtAuthenticationFilter) {
        JwtAuthenticationFilter = jwtAuthenticationFilter;
    }

    //seed the default username and password so anyone can log in for demo
    @Bean
    public UserDetailsService userDetailsService(){
        return username -> {
            UserDetails user = User.builder()
                    .username("admin")
                    .password(passwordEncoder().encode("password"))
                    .roles("ADMIN")
                    .build();
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), user.getAuthorities());
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/api/user/**").permitAll()
                        .requestMatchers(
                                "/api/v1/auth/**",
                                "/v2/api-docs",
                                "/v3/api-docs",
                                "/v3/api-docs/**",
                                "/swagger-resources",
                                "/swagger-resources/**",
                                "/configuration/ui",
                                "/configuration/security",
                                "/swagger-ui/**",
                                "/webjars/**",
                                "/swagger-ui.html"

                        ).permitAll()

                        .anyRequest().authenticated()


                ).addFilterBefore(JwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Use PasswordEncoderFactories to create a PasswordEncoder with the default hashing algorithm
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
