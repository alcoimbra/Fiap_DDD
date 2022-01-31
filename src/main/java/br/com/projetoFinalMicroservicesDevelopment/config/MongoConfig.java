package br.com.projetoFinalMicroservicesDevelopment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.MongoClient;

@Configuration
public class MongoConfig extends AbstractMongoConfiguration {
	
	@Value("${mongo.host}")
	private String MONGO_HOST;
	
	@Value("${mongo.port}")
	private Integer MONGO_PORT;
	
	@Value("${mongo.database}")
	private String MONGO_DATABASE;
	
	@Bean
	@Override
	public MongoClient mongoClient() {
		return new MongoClient(MONGO_HOST, MONGO_PORT);
	}
	
	@Override
	protected String getDatabaseName() {
		return MONGO_DATABASE;
	}
}