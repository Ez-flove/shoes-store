package com.example.demo;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.example.demo.models.User;

import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired private UserDetailsService userDetailsService;
	@Autowired
	private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
	@Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http	.csrf(cr->cr.disable())
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers("/", "/img/**", "/css/**", "/login", "/signup").permitAll()
						.requestMatchers(HttpMethod.POST, "/**").permitAll()
						.requestMatchers("/admin/**").hasRole("ADMIN")
						.requestMatchers("/customer/**").hasRole("USER")
						.requestMatchers("/shipper/**").hasRole("SHIPPER")

				).formLogin((f) -> f.loginPage("/login")
						.
						successHandler(new AuthenticationSuccessHandler() {
							@Override
							public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
								UserDetails userDetails = (UserDetails) authentication.getPrincipal();
								Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
								if (roles.contains("ROLE_ADMIN")) {
									response.sendRedirect("/admin");
									System.out.println(userDetails.getAuthorities());
								}
								if (roles.contains("ROLE_USER")) {
									response.sendRedirect("/customer");
									System.out.println(userDetails.getAuthorities());
								}
								if (roles.contains("ROLE_SHIPPER")) {
									response.sendRedirect("/shipper");
									System.out.println(userDetails.getAuthorities());
								}
							}

						})
				)
				.logout((l) -> l.logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/").invalidateHttpSession(true)
						.clearAuthentication(true));
		return http.build();
	}
	@Autowired
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }
}
