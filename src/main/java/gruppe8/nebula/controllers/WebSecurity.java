package gruppe8.nebula.controllers;

import gruppe8.nebula.repositories.AccountRepository;
import gruppe8.nebula.services.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

/*
Spring Security goes through a "FilterChain".
A lot of processing is happening before a request reach our controller
https://www.marcobehler.com/guides/spring-security
 */


@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final AccountService accountService;
    private final Logger log;

    public WebSecurity(AccountService accountService){
        this.accountService = accountService;
        this.log = LoggerFactory.getLogger(this.getClass());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                // antMatchers allows us to decide which pages are accessible without having logged in. We've split it up into our post and get mappings
                .antMatchers(HttpMethod.GET,"/js/*","task", "/css/*", "/images/*", "/fonts/*").permitAll() // static content
                .antMatchers(HttpMethod.GET,"/", "/login*", "/signup*", "/contact").permitAll() // HTML templates
                .antMatchers(HttpMethod.POST,"/login", "/signup").permitAll()
                // Everything else requires a log in
                .anyRequest().authenticated()
            .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/login?success")
                .failureForwardUrl("/login?error")
                .usernameParameter("email")
                .passwordParameter("password")
                .permitAll();
    }

    /*
    Authenticates a user which tries to log in
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }
    /*
    The bean below defines the "contract" for which the above method authenticates.
    The password gets encrypted with the Argon2PasswordEncoder.
    The accountRepository implements userDetailService, which returns a single user object
     */
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new Argon2PasswordEncoder());
        authenticationProvider.setUserDetailsService(accountService);
        return authenticationProvider;
    }

    @Bean
    public static Argon2PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32 , 1, 32768, 5);
    }
}
