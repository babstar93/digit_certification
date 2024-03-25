package digit.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import javax.sql.DataSource;

@Configuration
public class JdbcTemplateConfig {
    final private DataSource dataSource;
    public JdbcTemplateConfig(DataSource dataSource){
        this.dataSource = dataSource;
    }

//    @Autowired
//    protected DataSource dataSource;

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource);
    }
}
