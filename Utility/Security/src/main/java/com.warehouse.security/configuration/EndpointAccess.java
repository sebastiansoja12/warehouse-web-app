package com.warehouse.security.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(
        prePostEnabled = true
)
public class EndpointAccess extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().and().cors()
                .disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests()
                .antMatchers("/v2/api/routes/**")
                .permitAll()
                .antMatchers("/v2/api/shipments/**")
                .permitAll()
                .antMatchers("/v2/api/barcodes/**")
                .permitAll()
                .antMatchers("/v2/api/csv/**")
                .permitAll()
                .antMatchers("/v2/api/payments/**")
                .permitAll()
                .antMatchers("/v2/api/reroutes/**")
                .permitAll()
                .antMatchers("/v2/api/auth/**")
                .permitAll()
                .antMatchers("/v2/api/depots/**")
                .permitAll()
                .antMatchers("/v2/api/addresses/**")
                .permitAll()
                .antMatchers("/v2/api/suppliers/**")
                .permitAll()
                .antMatchers("/v2/api/deliveries/**")
                .permitAll()
                .and().authorizeRequests();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
