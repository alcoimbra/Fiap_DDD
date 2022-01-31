package br.com.projetoFinalMicroservicesDevelopment.domain;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.text.ParseException;
import java.util.*;


import br.com.projetoFinalMicroservicesDevelopment.exceptions.ExpenseException;
import br.com.projetoFinalMicroservicesDevelopment.repository.CategoryRepository;
import br.com.projetoFinalMicroservicesDevelopment.repository.ExpenseRepository;

@RunWith(MockitoJUnitRunner.class)
public class ExpenseTest {
	
	private static final String ID = "123";
	private static final String DESCRIPTION = "descricao";
	private static final double VALUE = 124.2;
	private static final long USER_CODE = 142;
	private static final Date DATE = new Date();
	private static final String CATEGORY_DETAIL = "teste";
	private static final Category CATEGORY = new Category(CATEGORY_DETAIL);
	private static final String DATE_STRING = "29012022";
	private static final String DATE_STRING_INVALID = "011";
	private static final String DETAIL = "Detalhes";
	
	@InjectMocks
	private Expense expenseDomain = new Expense();
	
	@Mock
	private ExpenseRepository expenseRepository;
	
	@Mock
	private CategoryRepository categoryRepository;
	
	@Before
	public void init() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void expenseEmptyConstructorTest() {
		String expectedToString = String.format(
				"Expense={ "
					+ "Id: " + ID
					+ "Descrição: " + DESCRIPTION
					+ "Valor: " + VALUE
					+ "Código do Usuário: " + USER_CODE
					+ "Data: " + DATE
					+ " }"
		);
		
		Expense expense = new Expense();
		expense.setId(ID);
		expense.setCategory(CATEGORY);
		expense.setDescription(DESCRIPTION);
		expense.setValue(VALUE);
		expense.setUserCode(USER_CODE);
		expense.setDate(DATE);
		
		Assert.assertEquals(expense.getId(), ID);
		Assert.assertEquals(expense.getCategory(), CATEGORY);
		Assert.assertEquals(expense.getUserCode(), USER_CODE);
		Assert.assertEquals(expense.getDate(), DATE);
		Assert.assertEquals(expense.getDescription(), DESCRIPTION);
		Assert.assertTrue(expense.getValue() == VALUE);
		Assert.assertEquals(expense.toString(), expectedToString);
	}
	
	@Test
	public void expenseContructorWithAllAttrTest() {
		Expense expense = new Expense(
									DESCRIPTION,
									VALUE,
									USER_CODE,
									DATE,
									CATEGORY
								);
		
		Assert.assertEquals(expense.getCategory(), CATEGORY);
		Assert.assertEquals(expense.getUserCode(), USER_CODE);
		Assert.assertEquals(expense.getDate(), DATE);
		Assert.assertEquals(expense.getDescription(), DESCRIPTION);
		Assert.assertTrue(expense.getValue() == VALUE);
	}
	
	@Test
	public void expenseConstructorWithoutCategoryAttrTest() {
		Expense expense = new Expense(
									DESCRIPTION,
									VALUE,
									USER_CODE,
									DATE
								);
		
		Assert.assertEquals(expense.getUserCode(), USER_CODE);
		Assert.assertEquals(expense.getDate(), DATE);
		Assert.assertEquals(expense.getDescription(), DESCRIPTION);
		Assert.assertTrue(expense.getValue() == VALUE);
	}
	
	@Test
	public void add_WithCategoryNullTest() {
		List<Expense> expenses = new ArrayList<>();
		Expense expense = new Expense(DESCRIPTION, VALUE, USER_CODE, DATE);
		expenses.add(expense);
		
		Mockito.when(this.expenseRepository.findByUserCode(USER_CODE)).thenReturn(expenses);
		
		this.expenseDomain.add(expense);
		
		List<Expense> result = this.expenseDomain.searchByUserCode(USER_CODE);
		Assert.assertEquals(result, expenses);
	}
	
	@Test
	public void add_WhitExpenseWithCategoryTest() {
		boolean isOk = true;
		Expense expense = new Expense(DESCRIPTION, VALUE, USER_CODE, DATE);
		Category category = new Category();
		List<Category> categories = new ArrayList<>();
		
		category.setDetail(DETAIL);
		expense.setCategory(category);
		categories.add(new Category(DETAIL));
		
		Mockito.when(categoryRepository.findByDetail(category.getDetail())).thenReturn(categories);
		
		try {
			this.expenseDomain.add(expense);
		} catch (Exception e) {
			isOk = false;
		}
		
		Assert.assertTrue(isOk);
	}
	
	@Test
	public void searchByUserCodeAndDateTest() throws ParseException {
		List<Expense> expenses = new ArrayList<>();
		Expense expense = new Expense(DESCRIPTION, VALUE, USER_CODE, DATE);
		
		expenses.add(expense);
		
		Assert.assertNotNull(this.expenseDomain.searchByUserCodeAndDate(USER_CODE, DATE_STRING));
	}
	
	@Test
	public void searchByUserCodeAndDate_InvalidDataTest() {
		List<Expense> expenses;
		boolean parseException = false;
		
		try {
			expenses = this.expenseDomain.searchByUserCodeAndDate(USER_CODE, DATE_STRING_INVALID);
		} catch (ParseException e) {
			parseException = true;
		}
		
		Assert.assertTrue(parseException);
	}
	
	@Test
	public void update_FoundExpenseTest() throws ExpenseException {
		boolean isOk = true;
		String uuid = UUID.randomUUID().toString();
		Expense expense = new Expense();
		Optional<Expense> optionalExpense = Optional.of(expense);
		
		Mockito.when(this.expenseRepository.findById(uuid)).thenReturn(optionalExpense);
		
		try {
			this.expenseDomain.update(uuid, expense);
		} catch (Exception e) {
			isOk = false;
		}
		
		Assert.assertTrue(isOk);
	}
	
	
	@Test
	public void update_NotFoundExpenseTest() {
		String uuid = UUID.randomUUID().toString();
		Expense expense = new Expense();
		Optional<Expense> optionalExpense = Optional.empty();
		
		Mockito.when(this.expenseRepository.findById(uuid)).thenReturn(optionalExpense);
		
		Assert.assertFalse(this.expenseDomain.update(uuid, expense));
	}
}