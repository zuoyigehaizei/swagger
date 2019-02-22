package com.example.demoswagger.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import javax.sql.DataSource;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//认证服务器，用来认证用户分发token,并存储
@Configuration
@EnableAuthorizationServer
public class MyAuthorizationServerConfigurer extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private AuthenticationManager authenticationManager;
//    @Autowired
//    RedisConnectionFactory redisConnectionFactory;

    @Autowired
    private DataSource dataSource;


    @Override
    //配置令牌端点的安全约束
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        //允许表单登录
        security.allowFormAuthenticationForClients();
    }


    @Override
    //配置客户端详情服务 两种客户端模式 password 和 client
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("client_1")
                //client
                .resourceIds("abc")
                .authorizedGrantTypes("client_credentials","refresh_token")
                .scopes("select")
                .authorities("client")
                .secret("123456")
                //password
                .and().withClient("client_2")
                .resourceIds("abc")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("select")
                .authorities("client")
                //由于设置的加密方式，所以client密码和用户的password都需要加密
                //client密码
                .secret(new BCryptPasswordEncoder().encode("123456"));
    }

    @Bean
    public TokenStore tokenStore() {
        return new JdbcTokenStore(dataSource);
    }

    //设置token 比如token的有效时长等
    @Bean
    public AuthorizationServerTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setAccessTokenValiditySeconds(1000000);
        defaultTokenServices.setRefreshTokenValiditySeconds(200000);
        defaultTokenServices.setSupportRefreshToken(true);
        defaultTokenServices.setReuseRefreshToken(false);
        defaultTokenServices.setTokenStore(tokenStore());
        return defaultTokenServices;
    }

    @Override
    //配置授权以及令牌的访问端点和令牌服务
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        //redis token存储
//        endpoints
////                .tokenStore(new RedisTokenStore(redisConnectionFactory))
//                .authenticationManager(authenticationManager);
        //数据库存储
        endpoints.tokenStore(tokenStore())
                //token转换，使用jwt
                .accessTokenConverter(accessTokenConverter())
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.GET);
        //以下为数据库存储token的表结构
        /**
         * DROP TABLE IF EXISTS `oauth_access_token`;
         * CREATE TABLE `oauth_access_token` (
         *   `token_id` varchar(256) DEFAULT NULL,
         *   `token` blob,
         *   `authentication_id` varchar(256) DEFAULT NULL,
         *   `user_name` varchar(256) DEFAULT NULL,
         *   `client_id` varchar(256) DEFAULT NULL,
         *   `authentication` blob,
         *   `refresh_token` varchar(256) DEFAULT NULL
         * ) ENGINE=MyISAM DEFAULT CHARSET=utf8;
         *
         * DROP TABLE IF EXISTS `oauth_refresh_token`;
         * CREATE TABLE `oauth_refresh_token` (
         *   `token_id` varchar(256) DEFAULT NULL,
         *   `token` blob,
         *   `authentication` blob
         * ) ENGINE=MyISAM DEFAULT CHARSET=utf8;*/
    }

    //jwt 对称加密
    //jwt转换器
//    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        //加密秘钥
        converter.setSigningKey("123");
        return converter;
    }

    //jwt 非对称加密  没搞定
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        //私钥
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("server.keystore"),"87654321".toCharArray());
//        converter.setKeyPair(keyStoreKeyFactory.getKeyPair("server"));
//        //公钥
//        Resource resource = new ClassPathResource("public.txt");
//        String publicKey = null;
//        try {
//            publicKey = inputStream2String(resource.getInputStream());
//        } catch (final IOException e) {
//            throw new RuntimeException(e);
//        }
//        converter.setVerifierKey(publicKey);
//        return converter;
//    }
//
//        String inputStream2String(InputStream is) throws IOException {
//        BufferedReader in = new BufferedReader(new InputStreamReader(is));
//        StringBuffer buffer = new StringBuffer();
//        String line = "";
//        while ((line = in.readLine()) != null) {
//            buffer.append(line);
//        }
//        return buffer.toString();
//    }
}
