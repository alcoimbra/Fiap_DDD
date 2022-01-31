package br.com.projetoFinalMicroservicesDevelopment.controller;

import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.ADD_EXPENSE_SUCCESS;
import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.EXPENSE_BADLY_FORMATTED_DATE;
import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.EXPENSE_NOT_FOUND;
import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.SEARCH_EXPENSE_BY_USER_CODE_SUCCESS;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.projetoFinalMicroservicesDevelopment.domain.Expense;
import br.com.projetoFinalMicroservicesDevelopment.domain.response.Response;
import br.com.projetoFinalMicroservicesDevelopment.domain.response.ResponseObject;
import br.com.projetoFinalMicroservicesDevelopment.exceptions.ExpenseException;
import br.com.projetoFinalMicroservicesDevelopment.service.ExpenseService;

public class ExpenseControllerTest {
	
	private static final String DESCRIPTION = "descrição";
	private static final Double VALUE = 198.23;
	private static final Long USER_CODE = 129L;
	private static final Date DATE = new Date();
	private static final String INCORRECT_DATE = "123456";
	
	@InjectMocks
	private ExpenseController expenseController = new ExpenseController();
	
	@Mock
	private ExpenseService expenseService;
	
	private Expense expense;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
		
		this.expense = new Expense(DESCRIPTION, VALUE, USER_CODE, DATE);
	}
	
	@Test
	public void addExpenseTest() {
		ResponseEntity<Response> response = this.expenseController.addExpense(this.expense);
		
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(response.getBody().getStatusCode(), HttpStatus.OK.value());
		Assert.assertEquals(response.getBody().getInternalMessage(), ADD_EXPENSE_SUCCESS.getInternalMessage());
		Assert.assertEquals(response.getBody().getUserMessage(), ADD_EXPENSE_SUCCESS.getUserMessage());
	}
	
	@Test
	public void getExpenseByUserCodeTest() {
		long userCode = USER_CODE;
		List<Expense> list = new ArrayList<>();
		list.add(this.expense);
		
		Mockito.when(this.expenseService.searchExpensesByUserCode(userCode)).thenReturn(list);
		
		ResponseEntity<ResponseObject> response = this.expenseController.getExpenseByUserCode(userCode);
		
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(response.getBody().getStatusCode(), HttpStatus.OK.value());
		Assert.assertEquals(response.getBody().getData(), list);
		Assert.assertEquals(response.getBody().getInternalMessage(), SEARCH_EXPENSE_BY_USER_CODE_SUCCESS.getInternalMessage());
		Assert.assertEquals(response.getBody().getUserMessage(), SEARCH_EXPENSE_BY_USER_CODE_SUCCESS.getUserMessage());
	}
	
	@Test
	public void getExpenseByUserCodeAndDateTest() throws ExpenseException {
		long userCode = USER_CODE;
		String date = DATE.toString();
		List<Expense> list = new ArrayList<>();
		
		list.add(this.expense);
		
		Mockito.when(this.expenseService.searchExpensesByUserCodeAndDate(userCode, date)).thenReturn(list);
		
		ResponseEntity<?> response = this.expenseController.getExpenseByUserCodeAndDate(userCode, date);
		
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void getExpenseByUserCodeAndDate_WithDataIncorrectTest() throws ExpenseException {
		long userCode = USER_CODE;
		String date = INCORRECT_DATE;
		
		Mockito.when(this.expenseService.searchExpensesByUserCodeAndDate(userCode, date))
				.thenThrow(new ExpenseException(HttpStatus.BAD_REQUEST, EXPENSE_BADLY_FORMATTED_DATE));
		
		ResponseEntity<?> response = this.expenseController.getExpenseByUserCodeAndDate(userCode, date);
		
		Assert.assertEquals(response.getStatusCode(), HttpStatus.BAD_REQUEST);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void updateExpenseTest() {
		String id = this.expense.getId();
		
		ResponseEntity<?> response = this.expenseController.updateExpense(id, expense);
		
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void updateExpense_ExpenseWithoutIdTest() throws ExpenseException {
		String id = this.expense.getId();
		
		Mockito.doThrow(new ExpenseException(HttpStatus.NOT_FOUND, EXPENSE_NOT_FOUND))
				.when(this.expenseService).updateExpense(id, expense);
		
		ResponseEntity<?> response = this.expenseController.updateExpense(id, expense);
		
		Assert.assertEquals(response.getStatusCode(), HttpStatus.NOT_FOUND);
		Assert.assertNotNull(response.getBody());
	}
}