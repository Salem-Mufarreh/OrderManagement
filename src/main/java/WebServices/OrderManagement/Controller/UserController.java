package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Config.JWTAuthentication;
import WebServices.OrderManagement.Config.JWTService;
import WebServices.OrderManagement.Entity.AuthenticationTokenEntity;
import WebServices.OrderManagement.Entity.UserEntity;
import WebServices.OrderManagement.Services.AuthenticationTokenService;
import WebServices.OrderManagement.Services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService _UserService;
    @Autowired
    private final JWTAuthentication _Authentication;
    @Autowired
    private final JWTService _JwtService;
    private final AuthenticationTokenService _TokenService;

    public UserController(UserService userService, JWTAuthentication authentication, JWTService jwtService, AuthenticationTokenService tokenService) {
        _UserService = userService;
        _Authentication = authentication;
        _JwtService = jwtService;
        _TokenService = tokenService;
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody UserEntity user) {
        try {
            UserEntity signedUser = _UserService.findUserByEmail(user.getUsername());
            if (signedUser == null) {

                user.setRoles(new HashSet<>());
                user.setAuthenticationTokens(new HashSet<>());
                signedUser = _UserService.CreateUser(user);

                signedUser.getAuthenticationTokens().add(generateToken(signedUser));
                return ResponseEntity.ok(signedUser);
            } else {
                return ResponseEntity.badRequest().body("Username already exists");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserEntity claimedUser) {
        try {
            UserEntity user = _UserService.findUserByEmail(claimedUser.getUsername());
            if (user != null && user.getPassword().equals(claimedUser.getPassword())) {
                AuthenticationTokenEntity token = _TokenService.GetTokenByUser(user.getId());
                if (token != null && token.getExpired()== true){
                    _TokenService.DeleteToken(token.getId());
                }
                else{
                    user.getAuthenticationTokens().add(token);
                    return ResponseEntity.ok(user);
                }
                user.getAuthenticationTokens().add(generateToken(user));
                return ResponseEntity.ok(user);
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
            }
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    private UserDetails convertToUserDetails(UserEntity userO){
        UserDetails userDetails = new User(
                userO.getUsername(),
                userO.getPassword(),
                true,
                true,
                true,
                true,
                AuthorityUtils.createAuthorityList(userO.getRoles().toArray(new String[0]))
        );
        return userDetails;
    }
    private String generateAuthToken(UserEntity user) {
        UserDetails userDetails = convertToUserDetails(user);
        return _JwtService.generateToken(userDetails);
    }
    private AuthenticationTokenEntity generateToken(UserEntity user){
        AuthenticationTokenEntity token = new AuthenticationTokenEntity();
        token.setExpired(false);
        token.setId(new Random().nextLong());
        token.setUser(_UserService.GetUserById(user.getId()));
        token.setToken(generateAuthToken(user));
        return _TokenService.CreatAuthenticationToken(token);
    }

}


