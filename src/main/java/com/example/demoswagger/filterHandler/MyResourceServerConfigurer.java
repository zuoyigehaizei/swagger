package com.example.demoswagger.filterHandler;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

//资源服务器用来验证token
@Configuration
@EnableResourceServer
public class MyResourceServerConfigurer extends ResourceServerConfigurerAdapter {

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources.resourceId("abc").stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/abc/**").authenticated();//配置order访问控制，必须认证过后才可以访问
    }

//    //非对称加密
//    @Bean
//    public JwtAccessTokenConverter accessTokenConverter() {
//        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
//        Resource resource = new ClassPathResource("server.pfx");
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
//    String inputStream2String(InputStream is) throws IOException {
//        BufferedReader in = new BufferedReader(new InputStreamReader(is));
//        StringBuffer buffer = new StringBuffer();
//        String line = "";
//        while ((line = in.readLine()) != null) {
//            buffer.append(line);
//        }
//        return buffer.toString();
//    }
}
