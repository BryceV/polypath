package base.security.config;

import base.security.jwt.JwtAuthenticationTokenFilter;
import base.security.jwt.JwtAuthorizationExceptionHandler;
import base.security.user.PolyPathUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.data.repository.query.SecurityEvaluationContextExtension;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration // Tell Spring this is a configuration class
@EnableWebSecurity // Tell Spring to enable security as a web application
@EnableGlobalMethodSecurity(prePostEnabled=true) // Enable @Pre @PostAuthorize
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthorizationExceptionHandler unauthorizedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
          .csrf()
            .disable()
          .exceptionHandling().authenticationEntryPoint(unauthorizedHandler) // Custom exception handler. Used to return 401s.
            .and()
          .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and()
          .headers().cacheControl();// disable page caching

        http
          .authorizeRequests()
          .antMatchers(HttpMethod.GET, "/api/courses/**").permitAll() // to allow for course search
          .antMatchers(HttpMethod.GET, "/api/quarters/**").permitAll() // to allow for quarters data
          .antMatchers(HttpMethod.GET, "/api/flowcharts/official").permitAll() // to allow for quarters data
          .antMatchers("/api/flowcharts/**/publish").hasRole("CATALOG_ADMIN")
          .antMatchers("/api/**").authenticated()  // We only need to protect the api routes
          .anyRequest().permitAll();

        // Custom JWT based security filter -- Check for JWT tokens
        http
          .addFilterBefore(authenticationTokenFilterBean(), UsernamePasswordAuthenticationFilter.class);
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth, PolyPathUserDetailsService polyPathUserDetailsService) throws Exception {
        auth
          .userDetailsService(polyPathUserDetailsService)
          .passwordEncoder(new BCryptPasswordEncoder());
    }

    @Bean
    public SecurityEvaluationContextExtension securityEvaluationContextExtension() {
        return new SecurityEvaluationContextExtension();
    }

    @Bean
    public JwtAuthenticationTokenFilter authenticationTokenFilterBean() throws Exception {
        return new JwtAuthenticationTokenFilter();
    }
}

