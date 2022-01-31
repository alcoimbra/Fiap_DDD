package br.com.projetoFinalMicroservicesDevelopment.service;

import java.util.List;

import br.com.projetoFinalMicroservicesDevelopment.domain.Expense;
import br.com.projetoFinalMicroservicesDevelopment.exceptions.ExpenseException;

public interface ExpenseService {
	
	void addNewExpense(Expense newExpense);
	List<Expense> searchExpensesByUserCode(Long userCode);
	List<Expense> searchExpensesByUserCodeAndDate(Long userCode, String date) throws ExpenseException;
	void updateExpense(String id, Expense newExpense) throws ExpenseException;
}