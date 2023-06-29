package WebServices.OrderManagement.Config;

import WebServices.OrderManagement.Entity.UserEntity;
import WebServices.OrderManagement.Repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JWTAuthentication implements UserDetailsService {
    @Autowired
    private UserRepo _UserRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity user = _UserRepository.findByUsername(username).orElseThrow();
        if (user == null){
            throw new UsernameNotFoundException("User not found: " + username);
        }
        else{
            return User.builder()
                    .username(user.getUsername())
                    .password(user.getPassword())
                    .roles(user.getRoles().toArray(new String[0]))
                    .build();
        }
    }
}
