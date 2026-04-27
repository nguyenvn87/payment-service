package com.uit.config;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	public static final String[] PERMITTED_URL = new String[]{
			"/test/**",
			"/file/**",
			"/public/**",
			"/swagger-ui/**",
			"/v3/api-docs/**",
	};

	@Autowired
	CorsProperties corsProperties;


//	 @Bean
//	 SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//		 http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
//				 .authorizeHttpRequests((requests) -> requests
//						 .requestMatchers(PERMITTED_URL).permitAll()
//						 .anyRequest().permitAll()
//				 );
////				 .oauth2ResourceServer(oauth2 -> oauth2
////						 .jwt(jwt -> jwt
////								 .jwtAuthenticationConverter(jwtAuthenticationConverter())
////						 )
////				 );
//
//		 return http.build();
//	 }

	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				.cors(Customizer.withDefaults())
				.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests((requests) -> requests
						.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
						.requestMatchers(PERMITTED_URL).permitAll()
						.anyRequest().permitAll()
				);

		return http.build();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource(CorsProperties corsProps) {
		CorsConfiguration config = new CorsConfiguration();

		config.setAllowedOrigins(corsProps.getAllowedOrigins());
		config.setAllowedMethods(corsProps.getAllowedMethods());
		config.setAllowedHeaders(corsProps.getAllowedHeaders());

		config.setAllowCredentials(true);

		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", config);

		return source;
	}

	 private JwtAuthenticationConverter jwtAuthenticationConverter() {
	        JwtGrantedAuthoritiesConverter jwtGrantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
	        jwtGrantedAuthoritiesConverter.setAuthoritiesClaimName("authorities");
	        jwtGrantedAuthoritiesConverter.setAuthorityPrefix("");
	        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
	        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(jwtGrantedAuthoritiesConverter);
	        return jwtAuthenticationConverter;
	 }

	 @Bean
	 public RestTemplate restTemplate() {
	        return new RestTemplate();
	    }
}
