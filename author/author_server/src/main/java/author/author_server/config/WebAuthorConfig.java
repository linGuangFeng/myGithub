package author.author_server.config;

import author.author_server.constants.FromLoginConstant;
import author.author_server.properties.SecurityProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 网页安全配置
 */
@Configuration
public class WebAuthorConfig extends WebSecurityConfigurerAdapter {
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }



    @Autowired
    private SecurityProperties securityProperties;
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //表单登录,loginPage为登录请求的url,loginProcessingUrl为表单登录处理的URL
                .formLogin()
//                .loginPage(FromLoginConstant.LOGIN_PAGE)
                .loginProcessingUrl(FromLoginConstant.LOGIN_PROCESSING_URL)

                //允许访问
                .and().authorizeRequests().antMatchers(
                     FromLoginConstant.LOGIN_PROCESSING_URL,
                     FromLoginConstant.LOGIN_PAGE,
                     securityProperties.getOauthLogin().getOauthLogin(),
                     securityProperties.getOauthLogin().getOauthGrant()
                ).permitAll().anyRequest().authenticated()

                //禁用跨站伪造
                .and().csrf().disable();
    }
}
