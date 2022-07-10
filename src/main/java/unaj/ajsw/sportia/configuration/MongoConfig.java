package unaj.ajsw.sportia.configuration;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.mongo.MongoProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories({"unaj.ajsw.sportia.repository"})
public class MongoConfig {

    private String uri;

    public MongoConfig(@Value("${spring.data.mongodb.uri}") String uri) {
        this.uri = uri;
    }

    protected String getDatabaseName() {
        return "sportiadb";
    }

    private MongoProperties properties = new MongoProperties();

    @Bean
    public MongoClient mongoClient(){
        return MongoClients.create(uri);
    }

}
