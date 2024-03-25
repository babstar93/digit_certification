package digit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;

import javax.sql.DataSource;

import static org.mockito.Mockito.mock;

@Configuration
public class TestConfiguration {
    @Bean
    @SuppressWarnings("unchecked")
    public KafkaTemplate<String, Object> kafkaTemplate() {
        return mock(KafkaTemplate.class);
    }

    @Bean
    public DataSource dataSource() {
        return mock(DataSource.class);
    }
}