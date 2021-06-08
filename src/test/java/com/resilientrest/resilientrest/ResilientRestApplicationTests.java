package com.resilientrest.resilientrest;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.util.Assert;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.HttpStatusCodeException;

@Import(Config.class)
@SpringBootTest
class ResilientRestApplicationTests {

	@Test
	void contextLoads() {
	}

	@Autowired
	private ResilientApi api;

	@MockBean
	private BackendService backendService;

	@Autowired
	private CircuitBreaker apiCircuitBreaker;

	@Test
	public void testCircuitBreaker() throws Exception{
		Mockito.when(backendService.hitBackendService()).thenThrow(HttpServerErrorException.class)
				.thenThrow(HttpServerErrorException.class);
		System.out.println(api.hitResilientApi());
		Assert.isTrue(apiCircuitBreaker.getState() == CircuitBreaker.State.CLOSED);
		System.out.println(api.hitResilientApi());
		Assert.isTrue(apiCircuitBreaker.getState() == CircuitBreaker.State.OPEN);

	}

}
