package projekti;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@EnableWebSecurity
@Configuration
public class DevelopmentSecurityConfiguration extends WebSecurityConfigurerAdapter {

    /*@Override
    public void configure(WebSecurity sec) throws Exception {
        // Pyyntöjä ei tarkasteta
        sec.ignoring().antMatchers("/**");
        
    }*/

    @Autowired
    private CustomUserDetailsService userDetailsService;

    protected void configure(HttpSecurity http) throws Exception {
        
        http
            .authorizeRequests()
                .antMatchers("/testi").permitAll()
                .antMatchers("/*.css").permitAll()
                .antMatchers("/*.ttf").permitAll()
                .antMatchers("/allpersons").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")   
                .permitAll()
                .defaultSuccessUrl("/profile/", true)
                .and()
            .logout()
                .permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

        
        
        
        
        
        /*.csrf().disable()
            .authorizeRequests()
            .antMatchers("/admin/**").hasRole("ADMIN")
            .antMatchers("/anonymous*").anonymous()
            .antMatchers("/").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin()
            .loginPage("/")
            .loginProcessingUrl("/perform_login")
            .defaultSuccessUrl("/users/testiurl", true)
            //.failureUrl("/login.html?error=true")
            //.failureHandler(authenticationFailureHandler())
            .and()
            .logout();
            //.logoutUrl("/perform_logout")
            //.deleteCookies("JSESSIONID")
           // .logoutSuccessHandler(logoutSuccessHandler());



                /*.antMatchers("/").permitAll()
                .antMatchers("/users/*").authenticated()
                .antMatchers("/login").permitAll();
                //.antMatchers("/access").permitAll()
                //.antMatchers("/to", "/to/*").permitAll()
                //.anyRequest().authenticated().and()
                //.formLogin().permitAll().and()
                //.logout().permitAll();*/
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder((passwordEncoder()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
