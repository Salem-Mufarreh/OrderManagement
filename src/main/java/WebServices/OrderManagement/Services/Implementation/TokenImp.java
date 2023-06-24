package WebServices.OrderManagement.Services.Implementation;

import WebServices.OrderManagement.Entity.AuthenticationTokenEntity;
import WebServices.OrderManagement.Repositories.AuthenticationTokenRepo;
import WebServices.OrderManagement.Services.AuthenticationTokenService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TokenImp implements AuthenticationTokenService {
    private final AuthenticationTokenRepo _Authentication;

    public TokenImp(AuthenticationTokenRepo authentication) {
        _Authentication = authentication;
    }

    @Override
    public AuthenticationTokenEntity CreatAuthenticationToken(AuthenticationTokenEntity tokenEntity) {

        return null;
    }

    @Override
    public AuthenticationTokenEntity GetTokenById(Long id) {
        return null;
    }

    @Override
    public List<AuthenticationTokenEntity> GetAllTokens() {
        return null;
    }

    @Override
    public AuthenticationTokenEntity UpdateToken(Long id, AuthenticationTokenEntity tokenEntity) {
        return null;
    }

    @Override
    public void DeleteToken(Long id) {

    }


}
