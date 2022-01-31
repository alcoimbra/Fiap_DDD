package br.com.projetoFinalMicroservicesDevelopment.service.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projetoFinalMicroservicesDevelopment.domain.Category;
import br.com.projetoFinalMicroservicesDevelopment.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private Category category;

	@Override
	public void saveCategory(Category newCategory) {
		this.category.save(newCategory);
	}

	@Override
	public List<Category> searchCategoryByDetailSubstring(String detailSubstring) {
		return this.category.searchByDetailSubstring(detailSubstring);
	}
	
}