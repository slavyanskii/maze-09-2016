package ru.mail.park.conf;

import org.apache.commons.dbcp2.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by viacheslav on 25.10.16.
 */
@Configuration
public class Config {

    @Bean
    public BasicDataSource dataSource() throws URISyntaxException {
        final URI dbUri = new URI(System.getenv("JAWSDB_URL"));

        final String username = dbUri.getUserInfo().split(":")[0];
        final String password = dbUri.getUserInfo().split(":")[1];
        final String dbUrl = "jdbc:mysql://" + dbUri.getHost() + dbUri.getPath();

        final BasicDataSource basicDataSource = new BasicDataSource();
        basicDataSource.setUrl(dbUrl);
        basicDataSource.setUsername(username);
        basicDataSource.setPassword(password);

        return basicDataSource;
    }
}
