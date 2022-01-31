package br.com.projetoFinalMicroservicesDevelopment.controller;

import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetoFinalMicroservicesDevelopment.domain.Category;
import br.com.projetoFinalMicroservicesDevelopment.domain.response.Response;
import br.com.projetoFinalMicroservicesDevelopment.domain.response.ResponseObject;
import br.com.projetoFinalMicroservicesDevelopment.service.CategoryService;
import br.com.projetoFinalMicroservicesDevelopment.utils.ExpenseManagementUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/expense-management")
@Api(value = "Categoria")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	@ApiOperation("Adicionar uma Nova Categoria")
	@PostMapping("/categories")
	public ResponseEntity<Response> addCategory(
			@ApiParam(value = "Nova Categoria", required = true) @RequestBody Category category
	){
		this.categoryService.saveCategory(category);
		
		return ExpenseManagementUtils.responseWithoutData(ADD_CATEGORY_SUCCESS, HttpStatus.OK);
	}
	
	public ResponseEntity<ResponseObject> suggestionCategory(
			@ApiParam(value = "Substring da Categoria", required = true) @PathVariable String detailSubstring
	){
		List<Category> categories = this.categoryService.searchCategoryByDetailSubstring(detailSubstring);
		
		return ExpenseManagementUtils.responseWithData(SUGGESTION_CATEGORY_SUCCESS, HttpStatus.OK, categories);
	}
}