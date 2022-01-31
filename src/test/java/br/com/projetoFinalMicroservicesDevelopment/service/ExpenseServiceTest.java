package br.com.projetoFinalMicroservicesDevelopment.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;

import br.com.projetoFinalMicroservicesDevelopment.domain.Expense;
import br.com.projetoFinalMicroservicesDevelopment.exceptions.ExpenseException;
import br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum;
import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.*;
import br.com.projetoFinalMicroservicesDevelopment.service.serviceImpl.ExpenseServiceImpl;

public class ExpenseServiceTest {
	
	private static final long USER_CODE = 1232;
	private static final Date DATE = new Date();
	private static final String DATE_STRING = "29012022";
	private static final String DATE_STRING_INVALID = "011";
	private static final double VALUE = 124.2;
	private static final String DESCRIPTION = "Teste";
	private static final String DETAIL = "Detalhes";
	
	private List<Expense> expenseResult;
	
	@InjectMocks
	private ExpenseServiceImpl expenseServiceImpl = new ExpenseServiceImpl();
	
	@Mock
	private Expense expense;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		this.expenseResult = new ArrayList<>();
		this.expenseResult.add(new Expense());
	}
	
	@Test
	public void searchExpensesByUserCodeAndDate_InvalidDataTest() throws ParseException {
		List<Expense> expenses = new ArrayList<>();
		ResponseMessageEnum responseMessageEnumExpected = EXPENSE_BADLY_FORMATTED_DATE;
		HttpStatus httpStatusExpected = HttpStatus.BAD_REQUEST;
		Expense expense = new Expense(DESCRIPTION, VALUE, USER_CODE, DATE);
		
		expense.add(expense);
		
		Mockito.when(this.expense.searchByUserCodeAndDate(USER_CODE, DATE_STRING_INVALID)).thenThrow(ParseException.class);
		
		try {
			this.expenseServiceImpl.searchExpensesByUserCodeAndDate(USER_CODE, DATE_STRING_INVALID);
		} catch (ExpenseException e) {
			ResponseMessageEnum responseMessageEnum = e.getResponseMessageEnum();
			
			Assert.assertEquals(responseMessageEnum.getInternalMessage(), responseMessageEnumExpected.getInternalMessage());
			Assert.assertEquals(responseMessageEnum.getUserMessage(), responseMessageEnumExpected.getUserMessage());
			Assert.assertEquals(e.getStatusCode(), httpStatusExpected);
		}
	}
	
	@Test
	public void searchExpensesByUserCodeTest() {
		Mockito.when(this.expense.searchByUserCode(USER_CODE)).thenReturn(this.expenseResult);
		
		Assert.assertEquals(this.expenseServiceImpl.searchExpensesByUserCode(USER_CODE), this.expenseResult);
	}
	
	@Test
	public void updateExpenseTest() {
		boolean isOk = true;
		String uuid = UUID.randomUUID().toString();
		Expense expense = new Expense();
		
		Mockito.when(this.expense.update(uuid, expense)).thenReturn(true);
		
		try {
			this.expenseServiceImpl.updateExpense(uuid, expense);
		} catch (ExpenseException e) {
			isOk = false;
		}
		
		Assert.assertTrue(isOk);
	}
	
	@Test
	public void updateExpense_NotFoundExpenseTest() {
		HttpStatus httpStatusExpected = HttpStatus.NOT_FOUND;
		ResponseMessageEnum responseMessageEnum = EXPENSE_NOT_FOUND;
		String uuid = UUID.randomUUID().toString();
		Expense expense = new Expense();
		
		Mockito.when(this.expense.update(uuid, expense)).thenReturn(false);
		
		try {
			this.expenseServiceImpl.updateExpense(uuid, expense);
		} catch (ExpenseException e) {
			Assert.assertEquals(e.getStatusCode(), httpStatusExpected);
			Assert.assertEquals(e.getResponseMessageEnum().getUserMessage(), responseMessageEnum.getUserMessage());
		}
	}
}