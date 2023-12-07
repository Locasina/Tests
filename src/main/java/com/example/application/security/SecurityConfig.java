package com.example.application.security;

import com.example.application.data.entity.User;
import com.example.application.data.repository.UserRepository;
import com.example.application.service.UserSevice;
import com.example.application.views.LoginView;
import com.vaadin.flow.spring.security.VaadinWebSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.PasswordManagementConfigurer;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@EnableWebSecurity
@Configuration
public class SecurityConfig extends VaadinWebSecurity {

//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }

    @Autowired
    UserRepository userRepository;
    @Autowired
    private UserSevice userSevice;



    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.requestMatchers(new AntPathRequestMatcher("/public/**"))
                .permitAll());
        super.configure(http);
        setLoginView(http, LoginView.class);
        http.userDetailsService(userSevice);

    }
    public void configure(WebSecurity web) throws Exception {
        super.configure(web);
    }
    @Bean
    public static NoOpPasswordEncoder passwordEncoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }

//    @Bean
//    public UserDetailsService users() {
//        List<UserDetails> result =
//                StreamSupport.stream(userRepository.findAll().spliterator(), false)
//                        .collect(Collectors.toList());
//        List<UserDetails> result2 = new ArrayList<>();
//        for (int i = 0; i < result.size(); i++) {
//            UserDetails user = result.get(i);
//            UserDetails user1 = org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
//                    .username(user.getUsername())
//                    .password(user.getPassword())
//                    .roles("USER").build();
//            result2.add(user1);
//        }
////        UserDetails user = User.withDefaultPasswordEncoder()
////                .username("user")
////                // password = password with this hash, don't tell anybody :-)
////                .password("password")
////                .roles("USER")
////                .build();
////        UserDetails admin = User.builder()
////                .username("admin")
////                .password("{bcrypt}$2a$10$GRLdNijSQMUvl/au9ofL.eDwmoohzzS7.rmNSJZ.0FxO/BTk76klW")
////                .roles("USER", "ADMIN")
////                .build();
//        return new InMemoryUserDetailsManager(result2);
//    }

}