package WebServices.OrderManagement.Services.Implementation;

import WebServices.OrderManagement.Entity.AuthenticationTokenEntity;
import WebServices.OrderManagement.Repositories.AuthenticationTokenRepo;
import WebServices.OrderManagement.Services.AuthenticationTokenService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
/**
 * This class implements the AuthenticationTokenService interface.
 *
 * The class provides methods for creating, retrieving, updating, and deleting Token.
 */
@Service
public class AuthenticationTokensImpl implements AuthenticationTokenService {
    private final AuthenticationTokenRepo _AuthenticationTokenRepo;

    public AuthenticationTokensImpl(AuthenticationTokenRepo authenticationTokenRepo) {
        _AuthenticationTokenRepo = authenticationTokenRepo;
    }

    @Override
    public AuthenticationTokenEntity GetToken(String token) {
        AuthenticationTokenEntity tokenEntity = _AuthenticationTokenRepo.findByToken(token).get();
        if (tokenEntity != null)
        {
            return tokenEntity;
        }
        return null;
    }

    @Override
    public AuthenticationTokenEntity GetTokenByUser(Long id) {
        AuthenticationTokenEntity token = _AuthenticationTokenRepo.findByUserId(id).get();
        if (token != null){
            return token;
        }
        return null;
    }

    @Override
    public AuthenticationTokenEntity CreatAuthenticationToken(AuthenticationTokenEntity tokenEntity) {
        if (tokenEntity!= null){
            tokenEntity = _AuthenticationTokenRepo.save(tokenEntity);
            return tokenEntity;
        }
        return null;
    }

    @Override
    public AuthenticationTokenEntity GetTokenById(Long id) {
        AuthenticationTokenEntity token = _AuthenticationTokenRepo.findById(id).orElseThrow(()->new ResponseStatusException(HttpStatus.NOT_FOUND,"Token not found"));
        if (token != null){
            return token;
        }
        return null;
    }

    @Override
    public List<AuthenticationTokenEntity> GetAllTokens() {
        List<AuthenticationTokenEntity> list = _AuthenticationTokenRepo.findAll();
        return list;
    }

    @Override
    public AuthenticationTokenEntity UpdateToken(Long id, AuthenticationTokenEntity tokenEntity) {
        AuthenticationTokenEntity token = GetTokenById(id);
        if (token != null){
            token.setToken(tokenEntity.getToken());
            token.setUser(tokenEntity.getUser());
            token.setExpired(tokenEntity.getExpired());
            tokenEntity = _AuthenticationTokenRepo.save(token);
            return tokenEntity;
        }
        return null;
    }

    @Override
    public void DeleteToken(Long id) {
        AuthenticationTokenEntity token = GetTokenById(id);
        if (token != null){
            _AuthenticationTokenRepo.delete(token);
        }
    }
}
