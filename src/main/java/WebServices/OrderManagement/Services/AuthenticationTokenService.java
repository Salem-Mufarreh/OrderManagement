package WebServices.OrderManagement.Services;

import WebServices.OrderManagement.Entity.AuthenticationTokenEntity;

import java.util.List;

public interface AuthenticationTokenService {
    AuthenticationTokenEntity CreatAuthenticationToken(AuthenticationTokenEntity tokenEntity);
    AuthenticationTokenEntity GetTokenById(Long id);
    List<AuthenticationTokenEntity> GetAllTokens();
    AuthenticationTokenEntity UpdateToken(Long id, AuthenticationTokenEntity tokenEntity);
    void DeleteToken(Long id);
}
