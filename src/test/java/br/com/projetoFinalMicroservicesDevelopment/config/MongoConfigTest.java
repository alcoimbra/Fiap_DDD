package br.com.projetoFinalMicroservicesDevelopment.config;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.InjectMocks;

public class MongoConfigTest {
	
	@InjectMocks
	private MongoConfig mongoConfig = new MongoConfig();
	
	@Test
	public void mongoClientTest() {
		Assert.assertNotNull(mongoConfig.mongoClient());
	}
	
	@Test
	public void mongoDatabaseTest() {
		Assert.assertNull(mongoConfig.getDatabaseName());
	}
}