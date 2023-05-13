package no.ntnu.ETIVR;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Steinar Hjelle Midthus
 * @version 0.1
 */
public class WebConfig implements WebMvcConfigurer {

    /**
     * Adds CORS mapping
     * @param registry !TODO documentation on this
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }
}
