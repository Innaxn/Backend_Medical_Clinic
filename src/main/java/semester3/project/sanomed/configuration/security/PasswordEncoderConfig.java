package semester3.project.sanomed.configuration.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import semester3.project.sanomed.Generated;

@Configuration
@Generated
public class PasswordEncoderConfig {
    @Bean
    public PasswordEncoder createBCryptPasswordEncoder()
        {
            return new BCryptPasswordEncoder();
        }
}