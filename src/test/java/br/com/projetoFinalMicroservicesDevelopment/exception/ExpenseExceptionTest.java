package br.com.projetoFinalMicroservicesDevelopment.exception;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;

import br.com.projetoFinalMicroservicesDevelopment.exceptions.ExpenseException;
import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.*;

public class ExpenseExceptionTest {
	
	private static final String MESSAGE = "teste";
	
	@Test
	public void expenseExceptionConstructor1Test() {
		ExpenseException expenseException = new ExpenseException();
		
		Assert.assertEquals(expenseException.getStatusCode(), HttpStatus.INTERNAL_SERVER_ERROR);
		Assert.assertEquals(expenseException.getResponseMessageEnum(), UNKNOWN_ERROR);
	}
	
	@Test
	public void expenseExceptionConstructor2Test() {
		ExpenseException expenseException = new ExpenseException(HttpStatus.OK, MESSAGE, EXPENSE_BADLY_FORMATTED_DATE);
		
		Assert.assertEquals(expenseException.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(expenseException.getResponseMessageEnum(), EXPENSE_BADLY_FORMATTED_DATE);
		Assert.assertEquals(expenseException.getMessage(), MESSAGE);
	}
	
	@Test
	public void expenseExceptionConstructor3Test() {
		ExpenseException expenseException = new ExpenseException(HttpStatus.OK, EXPENSE_BADLY_FORMATTED_DATE);
		
		Assert.assertEquals(expenseException.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(expenseException.getResponseMessageEnum(), EXPENSE_BADLY_FORMATTED_DATE);
		
		expenseException.setMessage(MESSAGE);
		expenseException.setResponseMessageEnum(UNKNOWN_ERROR);
		expenseException.setStatusCode(HttpStatus.BAD_GATEWAY);
		
		Assert.assertEquals(expenseException.getMessage(), MESSAGE);
		Assert.assertEquals(expenseException.getResponseMessageEnum(), UNKNOWN_ERROR);
		Assert.assertEquals(expenseException.getStatusCode(), HttpStatus.BAD_GATEWAY);
	}
}