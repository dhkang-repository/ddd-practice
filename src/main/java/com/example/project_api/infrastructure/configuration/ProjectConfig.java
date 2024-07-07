package com.example.project_api.infrastructure.configuration;

import com.example.project_api.infrastructure.util.SecretKeys;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpMethod;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;

@Configuration
public class ProjectConfig {
    private final Logger logger = LoggerFactory.getLogger(ProjectConfig.class);
    @Bean
    public ObjectMapper objectMapper () {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        return objectMapper;
    }


    @Bean
    public SecretKeys appkeys(ObjectMapper objectMapper, @Value("${appkey.location}") String appkeyLocation) throws IOException {
        ClassPathResource resource = new ClassPathResource(appkeyLocation);
        Path path = Paths.get(resource.getURI());
        List<String> content = Files.readAllLines(path);
        logger.info("create setcret-keys : {}", objectMapper.writeValueAsString(content));
        return new SecretKeys(new HashSet<>(content));
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();

        config.setAllowCredentials(false);

        config.addAllowedHeader("*");
        config.addAllowedOrigin("*");

        List<HttpMethod> methodList = List.of(
                HttpMethod.GET, HttpMethod.HEAD, HttpMethod.POST, HttpMethod.DELETE,
                HttpMethod.PATCH, HttpMethod.PUT, HttpMethod.TRACE, HttpMethod.OPTIONS);

        for(HttpMethod method : methodList) {
            config.addAllowedMethod(method.name());
        }

        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }
}
