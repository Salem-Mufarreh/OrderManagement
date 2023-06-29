package WebServices.OrderManagement.Config;

import WebServices.OrderManagement.Entity.AuthenticationTokenEntity;
import WebServices.OrderManagement.Entity.UserEntity;
import WebServices.OrderManagement.Services.AuthenticationTokenService;
import WebServices.OrderManagement.Services.UserService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Service
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JWTService _JwtService;
    private final UserService _UserService;
    private final AuthenticationTokenService _TokenService;

    public JwtAuthenticationFilter(JWTService jwtService, UserService userService, AuthenticationTokenService tokenService) {
        _JwtService = jwtService;
        _UserService = userService;
        _TokenService = tokenService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = extractToken(request);
            String username = _JwtService.extractUsername(token);
            UserEntity user = _UserService.findUserByEmail(username);
            if (token != null && _JwtService.isTokenValid(token,user)) {


                // Create an authentication object and set it in the security context
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        catch (Exception ex){

        }

        filterChain.doFilter(request, response);
    }
    private String extractToken(HttpServletRequest request) {
        // Extract the token from the Authorization header
        String headerValue = request.getHeader("Authorization");
        if (headerValue != null && headerValue.startsWith("Bearer ")) {
            return headerValue.substring(7); // Remove "Bearer " prefix
        }
        return null;
    }
}
