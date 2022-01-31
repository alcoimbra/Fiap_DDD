package br.com.projetoFinalMicroservicesDevelopment.controller;

import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.ADD_EXPENSE_SUCCESS;
import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.SEARCH_EXPENSE_BY_USER_CODE_AND_DATE_SUCCESS;
import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.SEARCH_EXPENSE_BY_USER_CODE_SUCCESS;
import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.UPDATE_EXPENSE_SUCCESS;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projetoFinalMicroservicesDevelopment.domain.Expense;
import br.com.projetoFinalMicroservicesDevelopment.domain.response.Response;
import br.com.projetoFinalMicroservicesDevelopment.domain.response.ResponseObject;
import br.com.projetoFinalMicroservicesDevelopment.exceptions.ExpenseException;
import br.com.projetoFinalMicroservicesDevelopment.service.ExpenseService;
import br.com.projetoFinalMicroservicesDevelopment.utils.ExpenseManagementUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@RestController
@RequestMapping("/expense-management")
@Api(value = "Gasto")
public class ExpenseController {
	
	@Autowired
	private ExpenseService expenseService;
	
	@ApiOperation(value = "Adicionar Gasto")
	@PostMapping("/expenses")
	public ResponseEntity<Response> addExpense(
			@ApiParam(value = "Novo Gasto", required = true) @RequestBody Expense expense
	){
		this.expenseService.addNewExpense(expense);
		
		return ExpenseManagementUtils.responseWithoutData(ADD_EXPENSE_SUCCESS, HttpStatus.OK);
	}
	
	@ApiOperation("Buscar Gastos por Usuário")
	@GetMapping("/expense/userCode/{userCode}")
	public ResponseEntity<ResponseObject> getExpenseByUserCode(
			@ApiParam(value = "Código do Cliente", required = true) @PathVariable long userCode
	){
		List<Expense> expenseByUserCode = this.expenseService.searchExpensesByUserCode(userCode);
		
		return ExpenseManagementUtils.responseWithData(SEARCH_EXPENSE_BY_USER_CODE_SUCCESS, HttpStatus.OK, expenseByUserCode);
	}
	
	@ApiOperation("Buscar Gastos por Usuário e Data")
	@GetMapping("/expense/userCode/{userCode}/date/{date}")
	public ResponseEntity<?> getExpenseByUserCodeAndDate(
			@ApiParam(value = "Código do Cliente", required = true) @PathVariable long userCode,
			@ApiParam(value = "Data a ser Pesquisada", required = true) @PathVariable String date
	){
		List<Expense> expenseByUserCodeAndDate;
		
		try {
			expenseByUserCodeAndDate = this.expenseService.searchExpensesByUserCodeAndDate(userCode, date);
			
			return ExpenseManagementUtils.responseWithData(SEARCH_EXPENSE_BY_USER_CODE_AND_DATE_SUCCESS, HttpStatus.OK, expenseByUserCodeAndDate);
		} catch (ExpenseException e) {
			return ExpenseManagementUtils.responseWithError(e.getResponseMessageEnum(), e.getStatusCode());
		}
	}
	
	@ApiOperation("Atualizar Gastos")
	@PutMapping("/expense/{id}")
	public ResponseEntity<?> updateExpense(
			@ApiParam(value = "ID do Gasto a ser Atualizado", required = true) @PathVariable String id,
			@ApiParam(value = "Gasto Atualizado", required = true) @RequestBody Expense expense
	){
		try {
			this.expenseService.updateExpense(id, expense);
			
			return ExpenseManagementUtils.responseWithoutData(UPDATE_EXPENSE_SUCCESS, HttpStatus.OK);
		} catch (ExpenseException e) {
			return ExpenseManagementUtils.responseWithError(e.getResponseMessageEnum(), e.getStatusCode());
		}
	}
}