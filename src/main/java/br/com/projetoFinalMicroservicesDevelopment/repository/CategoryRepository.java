package br.com.projetoFinalMicroservicesDevelopment.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import br.com.projetoFinalMicroservicesDevelopment.domain.Category;

@Repository
public interface CategoryRepository extends MongoRepository<Category, String>{
	
	List<Category> findByDetail(String detail);
	List<Category> findByDetailLike(String detail);
}