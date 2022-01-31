package br.com.projetoFinalMicroservicesDevelopment.utils;

import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.ADD_EXPENSE_SUCCESS;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.projetoFinalMicroservicesDevelopment.domain.response.Response;
import br.com.projetoFinalMicroservicesDevelopment.domain.response.ResponseError;
import br.com.projetoFinalMicroservicesDevelopment.domain.response.ResponseObject;
import br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum;

public class ExpenseManagementUtilsTest {
	
	private static final ResponseMessageEnum RESPONSE_MESSAGE_ENUM = ADD_EXPENSE_SUCCESS;
	private static final HttpStatus HTTP_STATUS = HttpStatus.OK;
	private static final Object DATA = new Object();
	
	@Test
	public void convertStringToInt_InvalidStringTest() {
		int intValue = ExpenseManagementUtils.convertStringToInt(10, "asd");
		
		Assert.assertEquals(intValue, 10);
	}
	
	@Test
	public void covnertStringToInt_ValidStringTest() {
		int intValue = ExpenseManagementUtils.convertStringToInt(10, "123");
		
		Assert.assertEquals(intValue, 123);
	}
	
	@Test
	public void responseWithDataTest() {
		ResponseEntity<ResponseObject> response = ExpenseManagementUtils.responseWithData(RESPONSE_MESSAGE_ENUM, HTTP_STATUS, DATA);
		
		Assert.assertEquals(response.getStatusCode(), HTTP_STATUS);
		Assert.assertEquals(response.getBody().getInternalMessage(), RESPONSE_MESSAGE_ENUM.getInternalMessage());
		Assert.assertEquals(response.getBody().getUserMessage(), RESPONSE_MESSAGE_ENUM.getUserMessage());
		Assert.assertEquals(response.getBody().getData(), DATA);
	}
	
	@Test
	public void responseWithoutData() {
		ResponseEntity<Response> response = ExpenseManagementUtils.responseWithoutData(RESPONSE_MESSAGE_ENUM, HTTP_STATUS);
		
		Assert.assertEquals(response.getStatusCode(), HTTP_STATUS);
		Assert.assertEquals(response.getBody().getInternalMessage(), RESPONSE_MESSAGE_ENUM.getInternalMessage());
		Assert.assertEquals(response.getBody().getUserMessage(), RESPONSE_MESSAGE_ENUM.getUserMessage());
	}
	
	@Test
	public void responseWithError() {
		ResponseEntity<ResponseError> response = ExpenseManagementUtils.responseWithError(RESPONSE_MESSAGE_ENUM, HTTP_STATUS);
		
		Assert.assertEquals(response.getBody().getInternalMessage(), RESPONSE_MESSAGE_ENUM.getInternalMessage());
		Assert.assertEquals(response.getBody().getUserMessage(), RESPONSE_MESSAGE_ENUM.getUserMessage());
	}
}