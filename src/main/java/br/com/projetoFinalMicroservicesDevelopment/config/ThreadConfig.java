package br.com.projetoFinalMicroservicesDevelopment.config;

import java.util.concurrent.Executor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import br.com.projetoFinalMicroservicesDevelopment.utils.ExpenseManagementUtils;

@Configuration
public class ThreadConfig {
	
	public static final String THREAD_NAME_PREFIX = "projetoFinalMicroservicesDevelopment";
	
	@Value("${thread.async_core_pool_size}")
	private String ASYNC_CORE_POOL_SIZE;
	
	@Value("${thread.async_max_pool_size}")
	private String ASYNC_MAX_POOL_SIZE;
	
	@Bean
	public Executor asyncExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(
				ExpenseManagementUtils.convertStringToInt(10, ASYNC_CORE_POOL_SIZE)
		);
		
		threadPoolTaskExecutor.setMaxPoolSize(
				ExpenseManagementUtils.convertStringToInt(100, ASYNC_CORE_POOL_SIZE)
		);
		
		threadPoolTaskExecutor.setQueueCapacity(Integer.MAX_VALUE);
		threadPoolTaskExecutor.setThreadNamePrefix(THREAD_NAME_PREFIX);
		threadPoolTaskExecutor.initialize();
		
		return threadPoolTaskExecutor;
	}
}