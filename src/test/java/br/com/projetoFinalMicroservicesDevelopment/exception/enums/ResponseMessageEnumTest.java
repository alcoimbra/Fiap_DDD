package br.com.projetoFinalMicroservicesDevelopment.exception.enums;

import org.junit.Assert;
import org.junit.Test;

import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.*;

public class ResponseMessageEnumTest {
	
	@Test
	public void ResponseMessageEnumTest() {
		Assert.assertNotNull(UNKNOWN_ERROR.getUserMessage());
		Assert.assertNotNull(UNKNOWN_ERROR.getInternalMessage());
		Assert.assertNotNull(UNKNOWN_ERROR.getCode());
	}
}