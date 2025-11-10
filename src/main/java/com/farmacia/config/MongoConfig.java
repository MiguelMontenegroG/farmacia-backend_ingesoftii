package com.farmacia.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.farmacia.repository")
public class MongoConfig {

    @Bean
    public MongoClient mongoClient() {
        String mongoUri = System.getenv("MONGODB_URI");
        if (mongoUri == null || mongoUri.isEmpty()) {
            // Valor por defecto sin el error tipográfico
            mongoUri = "mongodb+srv://miguelamontenegrog_db_user:0Ge4uLI7XogtK2Pe@cluster0.7ouiwfq.mongodb.net/farmacia?retryWrites=true&w=majority";
        }
        
        // Asegurarse de que no haya una llave de cierre adicional al final
        if (mongoUri.endsWith("}")) {
            mongoUri = mongoUri.substring(0, mongoUri.length() - 1);
        }
        
        return MongoClients.create(mongoUri);
    }

    @Bean
    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "farmacia");
    }
}