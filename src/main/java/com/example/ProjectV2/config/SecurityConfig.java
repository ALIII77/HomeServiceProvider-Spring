package com.example.ProjectV2.config;


import com.example.ProjectV2.repository.AdminRepository;
import com.example.ProjectV2.repository.CustomerRepository;
import com.example.ProjectV2.repository.ExpertRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    private final AdminRepository adminRepository;
    private final PasswordEncoder passwordEncoder;
    private final ExpertRepository expertRepository;
    private final CustomerRepository customerRepository;

    public SecurityConfig(AdminRepository adminRepository, PasswordEncoder passwordEncoder, ExpertRepository expertRepository, CustomerRepository customerRepository) {
        this.adminRepository = adminRepository;
        this.passwordEncoder = passwordEncoder;
        this.expertRepository = expertRepository;
        this.customerRepository = customerRepository;
    }


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .csrf().disable()


                .authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN")
                .and()
                .authorizeHttpRequests().requestMatchers("/expert/**").hasRole("EXPERT")
                .and()
                .authorizeHttpRequests().requestMatchers("/customer/pay-credit/**").permitAll()
                .and()
                .authorizeHttpRequests().requestMatchers("/customer/**").hasRole("CUSTOMER")
                .and()
                .authorizeHttpRequests().anyRequest().permitAll()
                .and()
                .httpBasic();


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
                .passwordEncoder(passwordEncoder)
                .and()
                .userDetailsService((username) -> expertRepository
                        .findExpertByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("This %s notFound!", username))))
                .passwordEncoder(passwordEncoder)
                .and()
                .userDetailsService((username) -> customerRepository
                        .findCustomerByUsername(username)
                        .orElseThrow(() -> new UsernameNotFoundException(String.format("This %s notFound!", username))))
                .passwordEncoder(passwordEncoder);
    }


}
