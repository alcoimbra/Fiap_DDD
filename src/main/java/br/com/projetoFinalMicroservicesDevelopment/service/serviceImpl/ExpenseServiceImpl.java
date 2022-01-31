package br.com.projetoFinalMicroservicesDevelopment.service.serviceImpl;

import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.EXPENSE_BADLY_FORMATTED_DATE;
import static br.com.projetoFinalMicroservicesDevelopment.exceptions.enums.ResponseMessageEnum.EXPENSE_NOT_FOUND;

import java.text.ParseException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import br.com.projetoFinalMicroservicesDevelopment.domain.Expense;
import br.com.projetoFinalMicroservicesDevelopment.exceptions.ExpenseException;
import br.com.projetoFinalMicroservicesDevelopment.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService{
	
	@Autowired
	private Expense expense;

	@Async
	@Override
	public void addNewExpense(Expense newExpense) {
		this.expense.add(newExpense);
	}

	@Override
	public List<Expense> searchExpensesByUserCode(Long userCode) {
		return this.expense.searchByUserCode(userCode);
	}

	@Override
	public List<Expense> searchExpensesByUserCodeAndDate(Long userCode, String date) throws ExpenseException {
		try {
			return this.expense.searchByUserCodeAndDate(userCode, date);
		} catch (ParseException e) {
			throw new ExpenseException(HttpStatus.BAD_REQUEST, EXPENSE_BADLY_FORMATTED_DATE);
		}
	}

	@Override
	public void updateExpense(String id, Expense newExpense) throws ExpenseException {
		if (!this.expense.update(id, newExpense)) {
			throw new ExpenseException(HttpStatus.NOT_FOUND, EXPENSE_NOT_FOUND);
		}
	}
}