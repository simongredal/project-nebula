package gruppe8.nebula.controllers;

import gruppe8.nebula.repositories.AccountRepository;
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
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.argon2.Argon2PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {
    private final AccountRepository accountRepository;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public WebSecurity(AccountRepository accountRepository){
        this.accountRepository = accountRepository;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http
            .csrf().disable()
            .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/js/*","task", "/css/*", "/images/*", "/fonts/*").permitAll()
                .antMatchers(HttpMethod.GET,"/","teams", "/login*", "/signup*").permitAll()
                .antMatchers(HttpMethod.POST,"/login", "/signup").permitAll()
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

    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(daoAuthenticationProvider());
    }

    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setPasswordEncoder(new Argon2PasswordEncoder());
        authenticationProvider.setUserDetailsService(accountRepository);
        return authenticationProvider;
    }

    @Bean
    public Argon2PasswordEncoder passwordEncoder() {
        return new Argon2PasswordEncoder(16, 32 , 1, 32768, 5);
    }
}
