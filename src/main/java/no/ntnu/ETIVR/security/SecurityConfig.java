package no.ntnu.ETIVR.security;

import no.ntnu.ETIVR.security.extra.TokenEndpointEntry;
import no.ntnu.ETIVR.security.extra.JsonTokenFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private UserDetailsService userDetailsService;

    private TokenEndpointEntry tokenEndpointEntry;

    private JsonTokenFilter jsonTokenFilter;

  /**
   * Makes an instance of the SecurityConfig class.
   * @param securityService the security service.
   */
  public SecurityConfig(SecurityService securityService, JsonTokenFilter jsonTokenFilter, TokenEndpointEntry tokenEndpointEntry) {
      checkIfObjectIsNull(securityService, "security service");
      this.userDetailsService = securityService;
      this.tokenEndpointEntry = tokenEndpointEntry;
      this.jsonTokenFilter = jsonTokenFilter;
  }

    /**
     * This method will be called automatically by the framework to find out what authentication to use.
     * Here we tell that we want to load users from a database
     *
     * @param auth Authentication builder
     * @throws Exception m8 what
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }


    /**
     * Configure the authorization rules
     * @param http HTTP Security object
     * @throws Exception gets thrown if the config wants to troll us.
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // Set up the authorization requests, starting from most restrictive at the top, to least restrictive on bottom
        http.csrf().disable().cors().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests().antMatchers("/authenticate").permitAll().anyRequest().authenticated().and()
                .exceptionHandling().authenticationEntryPoint(tokenEndpointEntry).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .addFilterBefore(jsonTokenFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * This method is called to decide what encryption to use for password checking
     * @return The password encryptor
     */
    @Bean
    ///Todo: fix this later
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Checks if an object is null.
     *
     * @param object the object you want to check.
     * @param error  the error message the exception should have.
     * @throws IllegalArgumentException gets thrown if the object is null.
     */
    private void checkIfObjectIsNull(Object object, String error) {
        if (object == null) {
            throw new IllegalArgumentException("The " + error + " cannot be null.");
        }
    }
}
