package com.example.ProjectV2.config;


import com.example.ProjectV2.repository.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AdminRepository adminRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public SecurityConfig(AdminRepository adminRepository, BCryptPasswordEncoder passwordEncoder) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.authorizeRequests().anyRequest().permitAll();
        http
//                .csrf().disable()
//                .authorizeHttpRequests().requestMatchers("*/admin/**").authenticated()
//                .and()
//                .authorizeHttpRequests().anyRequest().permitAll()
//                .and().formLogin();

                .csrf().disable()
                .authorizeHttpRequests().requestMatchers("*/admin/**").hasRole("ROLE_ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers("*/expert/**").hasRole("ROLE_EXPERT")
                .and()
                .authorizeHttpRequests().requestMatchers("*/customer/**").hasRole("ROLE_CUSTOMER")
                .and()
                .httpBasic();

//                .authorizeHttpRequests().requestMatchers("*/admin/**").authenticated()
//                .and()
//                .authorizeHttpRequests().anyRequest().permitAll()
//                .and()
//                .httpBasic();

//                .authorizeHttpRequests().anyRequest().authenticated()
//                .and()
//                .formLogin();


//                .authorizeHttpRequests().requestMatchers("").hasRole("admin")                  /******/

//                .authorizeHttpRequests().requestMatchers("").hasAnyRole()

//                .authorizeHttpRequests().requestMatchers("*/admin/**").permitAll();

//                .authorizeHttpRequests().anyRequest().permitAll();


//                .authorizeHttpRequests().requestMatchers("*/admin/**").hasAnyRole("ADMIN").anyRequest().authenticated();       //ok


        return http.build();
    }

    @Bean
    public AuthenticationManager authenticateManager(AuthenticationConfiguration authenticationConfiguration) {
        try {
            return authenticationConfiguration.getAuthenticationManager();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth)
            throws Exception {
        auth.userDetailsService((username) -> adminRepository
                        .findAdminByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("This %s notFound!", username))))
                .passwordEncoder(passwordEncoder);
    }


}
