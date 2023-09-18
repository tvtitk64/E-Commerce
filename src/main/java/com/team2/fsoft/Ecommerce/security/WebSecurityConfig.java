package com.team2.fsoft.Ecommerce.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
public class WebSecurityConfig {
    final  UserDetailService userDetailService;

    public WebSecurityConfig(UserDetailService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Bean
    public JWTAuthenticationFilter jwtAuthenticationFilter() {
        return new JWTAuthenticationFilter();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // Password encoder, để Spring Security sử dụng mã hóa mật khẩu người dùng
        return new BCryptPasswordEncoder();
    }

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    public AuthenticationManager authenticationManager(HttpSecurity http, BCryptPasswordEncoder passwordEncoder, UserDetailService userDetailService)
            throws Exception {
        return http.getSharedObject(AuthenticationManagerBuilder.class)
                .userDetailsService(userDetailService)
                .passwordEncoder(passwordEncoder)
                .and().build();

    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((authorizeHttpRequests) ->
                        authorizeHttpRequests

                                .requestMatchers("/admin/**")
                                .hasAnyAuthority("ADMIN")
                                .requestMatchers("/user/**")
                                .hasAnyAuthority("USER", "ADMIN")
                                .requestMatchers("/shopper/**")
                                .hasAnyAuthority("SHOPPER", "ADMIN")
                                .requestMatchers("/shipper/**")
                                .hasAnyAuthority("SHIPPER", "ADMIN")
                                .requestMatchers("/wallet/**","/moneyTransfer/**")
                                .hasAnyAuthority("SHIPPER", "ADMIN","USER","SHOPPER")
                                .requestMatchers("/auth/**", "/products/**", "/swagger-ui/**", "/register/**","/product_details/**","/orders/**")
                                .permitAll()
                                .anyRequest()
                                .authenticated()
                )
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(Customizer.withDefaults());


        http.addFilterBefore(jwtAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
        http.cors(withDefaults());

        return http.build();
    }


}