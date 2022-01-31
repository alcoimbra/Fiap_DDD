package br.com.projetoFinalMicroservicesDevelopment.service;

import java.util.List;

import br.com.projetoFinalMicroservicesDevelopment.domain.Category;

public interface CategoryService {
	
	void saveCategory(Category newCategory);
	List<Category> searchCategoryByDetailSubstring(String detailSubstring);
}