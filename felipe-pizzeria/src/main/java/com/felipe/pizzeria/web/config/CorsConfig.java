package com.felipe.pizzeria.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class CorsConfig {
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        //para saber que origenes son permitidos desde mi backend
        corsConfiguration.setAllowedOrigins(Arrays.asList("http://localhost:4200"));
        //metodos que se pueden consumir desde un origen cruzado
        corsConfiguration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
        //headers pueden llegar atraves de los cors, con * permite todos los encabezados que vengan atraves de cors
        corsConfiguration.setAllowedHeaders(Arrays.asList("*"));


        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        //le decimos que le permitimos la configuracion a todos los controladores
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }
}
