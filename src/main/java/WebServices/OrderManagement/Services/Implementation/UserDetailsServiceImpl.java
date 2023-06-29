package WebServices.OrderManagement.Services.Implementation;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


public class UserDetailsServiceImpl {
/*    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Here, you can fetch the user details from your data source (e.g., database)
        // and create a UserDetails object with the fetched information
        // For simplicity, we'll create a default user with a hardcoded username, password, and role

        if ("admin".equals(username)) {
            return User.builder()
                    .username("admin")
                    .password("{noop}password")
                    .roles("ADMIN")
                    .build();
        }

        throw new UsernameNotFoundException("User not found");
    }*/
}
