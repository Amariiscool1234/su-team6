package LeagueFinder.LeagueFinder.security;

import LeagueFinder.LeagueFinder.security.CustomUserDetailsService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http)
            throws Exception {

        http
            .userDetailsService(userDetailsService)

            .authorizeHttpRequests(auth -> auth

                // Pages everyone can visit
                .requestMatchers(
                    "/",
                    "/index.html",
                    "/about.html",
                    "/contact.html",
                    "/leagues.html",
                    "/league-details.html",
                    "/login.html",
                    "/register.html",
                    "/auth/register",

                    // Public API documentation and endpoints
                    "/api-docs.html",
                      "/leagues",
                         "/leagues/**",
                        "/providers",
                       "/providers/**",
                      "/customers",
                       "/customers/**",
                       "/teams",
                       "/teams/**",
                        "/games",
                     "/games/**",
                     "/reviews",
                      "/reviews/**",
                      "/team-registrations",
                       "/team-registrations/**",

                    // Static files
                    "/css/**",
                    "/js/**",
                    "/images/**",
                    "/favicon.ico",
                    "/error"
                ).permitAll()

                // Customer-only pages
                .requestMatchers(
                    "/player-dashboard.html",
                    "/player-profile.html",
                    "/review.html"
                ).hasRole("CUSTOMER")

                // Provider-only pages
                .requestMatchers(
                    "/provider-profile.html",
                    "/organizer-dashboard.html",
                    "/create.html"
                ).hasRole("PROVIDER")

                // Customer API access
                .requestMatchers(
                    "/team-registrations/**",
                    "/reviews/**"
                ).hasRole("CUSTOMER")

                // Provider API access
                .requestMatchers(
                    "/providers/**"
                ).hasRole("PROVIDER")

                // Teams can be viewed publicly
                .requestMatchers(
                    "/teams",
                    "/teams/**"
                ).permitAll()

                // Logged-in users can access their account
                .requestMatchers(
                    "/auth/me"
                ).authenticated()

                // Anything not listed requires login
                .anyRequest().authenticated()
            )

            .formLogin(form -> form
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .defaultSuccessUrl("/player-dashboard.html", true)
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler((request, response, authentication) -> {

                    boolean isProvider = authentication
                        .getAuthorities()
                        .stream()
                        .anyMatch(authority ->
                            authority.getAuthority().equals("ROLE_PROVIDER")
                        );

                    if (isProvider) {
                        response.sendRedirect("/provider-profile.html");
                    } else {
                        response.sendRedirect("/player-dashboard.html");
                    }
                })
                .failureUrl("/login.html?error=true")
                .permitAll()
            )

            .logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/index.html")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .permitAll()
            )

            .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
