package WebServices.OrderManagement.Controller;

import WebServices.OrderManagement.Config.JWTAuthentication;
import WebServices.OrderManagement.Config.JWTService;
import WebServices.OrderManagement.Entity.AuthenticationTokenEntity;
import WebServices.OrderManagement.Entity.UserEntity;
import WebServices.OrderManagement.Services.AuthenticationTokenService;
import WebServices.OrderManagement.Services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.Random;

/**
 * This class is the controller for the User Management web service.
 *
 * The class provides methods for signup, and Login User.
 */


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

    /**
     * This method creates a new user.
     *
     * @param user the user information.
     * @apiNote the api gets the user information from the username and checks if it is available.
     * @exception ResponseStatusException return an exception to usernamePassword is already been used.
     * @return user information.
     */
    @PostMapping("/signup")
    @Operation(
            description = "new user signup",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "new user was created",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 1, \"username\": \"admin@gmail.com\", \"password\": \"admin\", \"firstName\": \"admin\", \"lastName\": \"test\", \"createdAt\": \"2023-06-29T14:54:05.848\", \"enabled\": true, \"roles\": [], \"authenticationTokens\": [] }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "404",ref = "notFoundApi")
            }
    )
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


    /**
     * This method Updates a product information.
     *
     * @param claimedUser the username and password of the user.
     * @apiNote the api gets the user from the username and checks the passwords and add the roles if there is any
     * for this demo the roles were not included. and the default role is admin where the user can access all apis.
     * @exception UsernameNotFoundException return an exception to usernamePassword not found.
     * @return user information.
     */
    @PostMapping("/login")
    @Operation(
            description = "Login user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "user Logged in successfully",
                            content = @Content(
                                    mediaType = "application/json",
                                    examples = {
                                            @ExampleObject(
                                                    value = "{ \"id\": 2, \"username\": \"john@example.com\", \"password\": \"password123\", \"firstName\": \"John\", \"lastName\": \"Doe\", \"createdAt\": \"2023-07-01T10:00:00\", \"enabled\": true, \"roles\": [], \"authenticationTokens\": [ { \"id\": 3, \"token\": \"eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJqb2huQGV4YW1wbGUuY29tIiwiaWF0IjoxNjg4MDY3OTMxLCJleHAiOjE2ODgxNTQzMzF9.vJb8ZFE40uny34n3oRw7gyzTEQscPvG3RMJZJpyGsvA\", \"expired\": false } ] }"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(responseCode = "401",description = "user was not found",content = @Content(
                            mediaType = "application/json",
                            examples = {
                                    @ExampleObject(
                                            value = "Invalid username or password"
                                    )
                            }
                    ))
            }
    )
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


