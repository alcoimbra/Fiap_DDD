package br.com.projetoFinalMicroservicesDevelopment.controller;

import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.*;

import java.util.ArrayList;
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

import br.com.projetoFinalMicroservicesDevelopment.domain.Category;
import br.com.projetoFinalMicroservicesDevelopment.domain.response.Response;
import br.com.projetoFinalMicroservicesDevelopment.domain.response.ResponseObject;
import br.com.projetoFinalMicroservicesDevelopment.service.CategoryService;

public class CategoryControllerTest {
	
	private static final String CATEGORY_DETAIL = "Detail";
	
	private Category category;
	
	@InjectMocks
	private CategoryController categoryController = new CategoryController();
	
	@Mock
	private CategoryService categoryService;
	
	@Before
	public void init() {
		category = new Category(CATEGORY_DETAIL);
		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void suggestionCategoryTest() {
		String detailSubstring = "teste";
		List<Category> categories = new ArrayList<>();
		
		Mockito.when(this.categoryService.searchCategoryByDetailSubstring(detailSubstring)).thenReturn(categories);
		
		ResponseEntity<ResponseObject> response = this.categoryController.suggestionCategory(detailSubstring);
		
		Assert.assertEquals(response.getStatusCodeValue(), HttpStatus.OK);
		Assert.assertNotNull(response.getBody().getData());
	}
	
	public void addCategoryTest() {
		ResponseEntity<Response> response = this.categoryController.addCategory(category);
		
		Assert.assertEquals(response.getStatusCode(), HttpStatus.OK);
		Assert.assertEquals(response.getBody().getStatusCode(), HttpStatus.OK.value());
		Assert.assertEquals(response.getBody().getUserMessage(), ADD_CATEGORY_SUCCESS.getUserMessage());
		Assert.assertEquals(response.getBody().getInternalMessage(), ADD_CATEGORY_SUCCESS.getInternalMessage());
	}
}