package author.author_server.config;

import author.author_server.properties.ClientLoadProperties;
import author.author_server.properties.ClientProperties;
import author.author_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;

/**
 * 认证服务器配置
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter{
    @Autowired
    UserService userService;

    @Autowired
    @Qualifier("authenticationManagerBean")
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory connectionFactory;
    @Resource
    private ClientLoadProperties clientLoadProperties;

    /**
     * 定义令牌端点上的安全性约 束
     *
     * @param oauthServer oauthServer defines the security constraints on the token endpoint.
     * @throws Exception exception
     */
    @Override
    public void configure(final AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
        oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
        oauthServer.allowFormAuthenticationForClients();

    }

    /**
     * 定义token的存储方式
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore tokenStore() {
        return new RedisTokenStore(connectionFactory);
    }

    /**
     * 用于定义客户端详细信息服务的配置程序。可以初始化客户端详细信息，也可以只引用现有商店。
     *
     * @param clients a configurer that defines the client details service. Client details can be initialized, or you can just refer to an existing store.
     * @throws Exception exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder builder = clients.inMemory();
        if (!StringUtils.isEmpty(clientLoadProperties.getClients())) {
            for (ClientProperties config : clientLoadProperties.getClients()) {
                builder
                        //设置客户端和密码
                        .withClient(config.getClientId()).secret(config.getClientSecret())
                        //设置token有效期
                        .accessTokenValiditySeconds(7 * 24 * 3600)
                        //设置refreshToken有效期
                        .refreshTokenValiditySeconds(7 * 24 * 3600)
                        //支持的认证方式
                        .authorizedGrantTypes("refresh_token", "authorization_code", "password").autoApprove(false)
                        //授权域
                        .scopes("app","write");
            }
        }

    }


    /**
 * 定义授权和令牌端点以及令牌服务
 **/
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)//指定认证管理器（只要启动认证服务器，原本的默认拦截将会失效，因此，再把默认的拦截在配上，个人理解）
                .userDetailsService(userService) // 认证类配置，必须同时配置自定义的登录请求路径，此认证类才会生效
        ;
    }
}
