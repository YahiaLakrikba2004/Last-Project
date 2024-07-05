package YahiaLakrikba.GestioneEventi.config;

import com.cloudinary.Cloudinary;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServerConfig {

    @Value("${cloudinary.cloud-name}")
    private String cloudinaryCloudName;

    @Value("${cloudinary.api-key}")
    private String cloudinaryApiKey;

    @Value("${cloudinary.api-secret}")
    private String cloudinaryApiSecret;

    @Bean
    public Cloudinary cloudinaryUploader() {
        Cloudinary cloudinary = new Cloudinary();
        cloudinary.config.cloudName = cloudinaryCloudName;
        cloudinary.config.apiKey = cloudinaryApiKey;
        cloudinary.config.apiSecret = cloudinaryApiSecret;
        return cloudinary;
    }
}
