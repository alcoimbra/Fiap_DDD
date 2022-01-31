package br.com.projetoFinalMicroservicesDevelopment.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.projetoFinalMicroservicesDevelopment.domain.Expense;

@Repository
public interface ExpenseRepository extends MongoRepository<Expense, String>{
	
	List<Expense> findByUserCode(Long userCode);
	List<Expense> findUserCodeAndDateBetween(Long userCode, Date startData, Date endData);
}