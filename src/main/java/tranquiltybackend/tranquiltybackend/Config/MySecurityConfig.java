package tranquiltybackend.tranquiltybackend.Config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.List;

@Configuration
public class MySecurityConfig {
    @Autowired
    private JwtAuthenticationEntryPoint point;
    @Autowired
    private JwtAuthenticationFilter filter;


	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

//        http.cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(new CorsConfigurationSource() {
//            @Override
//            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
//                CorsConfiguration corsConfiguration = new CorsConfiguration();
////                corsConfiguration.addAllowedOrigin("http://localhost:4200");
//                // iske andar hum methods ko bhi allow kar sakte hai
//                // multiple url bhi allow kar sakte hai
//                corsConfiguration.setAllowedOriginPatterns(List.of("*"));
//                corsConfiguration.setAllowedMethods(List.of("*"));
//                corsConfiguration.setAllowCredentials(true);
//                corsConfiguration.setAllowedHeaders(List.of("*"));
//                corsConfiguration.setMaxAge(4000L);
//
//                return corsConfiguration;
//
//
//
//            }
//        }));

		http.csrf(csrf -> csrf.disable())
        .authorizeRequests().
        requestMatchers("/generate-token").permitAll().requestMatchers("/user/").permitAll().requestMatchers("/user/email/{email}").permitAll()
        .requestMatchers("/user/email/otp/{otp}/{username}").permitAll()
        .requestMatchers("/user/password-change").permitAll()

        .anyRequest()
        .authenticated()
        .and().exceptionHandling(ex -> ex.authenticationEntryPoint(point))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
return http.build();
	}
	
}
