package it.uniroma3.siw.authentication;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import static it.uniroma3.siw.model.Credentials.ADMIN_ROLE;
import static it.uniroma3.siw.model.Credentials.LEADER_ROLE;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
	public class AuthConfiguration {

    @Autowired
    private DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .authoritiesByUsernameQuery("SELECT username, role from credentials WHERE username=?")
                .usersByUsernameQuery("SELECT username, password, 1 as enabled FROM credentials WHERE username=?");
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    protected SecurityFilterChain configure(final HttpSecurity httpSecurity) throws Exception{
        httpSecurity
                .csrf().and().cors().disable()
                .authorizeHttpRequests()

                .requestMatchers(HttpMethod.GET,"/","/about" ,"/index", "/registerScelta", "/registerLeader", "/register","/css/**", "/images/**", "favicon.ico").permitAll()
                .requestMatchers(HttpMethod.POST,"/about" ,"/registerScelta", "/registerLeader" ,"/register", "/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/leadersAdmin", "/formNewLeader", "/deleteLeaders").hasAnyAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.POST, "/leader", "/deleteLeaders/{idLeader}").hasAnyAuthority(ADMIN_ROLE)
                .requestMatchers(HttpMethod.GET, "/addImage/*", "/formNewTrip", "/addLeader/*", "/updateTrip/*", "/setLeader/**", "/formAddStop/*").hasAnyAuthority(ADMIN_ROLE, LEADER_ROLE)
                .requestMatchers(HttpMethod.POST, "/addImage/*", "/deleteImage/**", "/trip", "/deleteTrip/*", "/updateTrip/*", "/stop/*", "/deleteStop/**").hasAnyAuthority(ADMIN_ROLE, LEADER_ROLE)

                .anyRequest().authenticated()

                .and().formLogin()
                .loginPage("/login")
                .permitAll()
                .defaultSuccessUrl("/success", true)
                .failureUrl("/login?error=true")

                .and()
                .logout()

                .logoutUrl("/logout")

                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .clearAuthentication(true).permitAll().and()
                .exceptionHandling()
                .accessDeniedHandler(accessDeniedHandler());
        return httpSecurity.build();
    }
    
    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            response.sendRedirect("/");
        };
    }
    
}
