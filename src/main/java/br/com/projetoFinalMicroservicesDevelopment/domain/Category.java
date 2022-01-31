package br.com.projetoFinalMicroservicesDevelopment.domain;

import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.stereotype.Component;

import br.com.projetoFinalMicroservicesDevelopment.repository.CategoryRepository;

@Component
public class Category {
	
	@Id
	private String id;
	private String detail;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	private String generateID() {
		return UUID.randomUUID().toString();
	}
	
	public Category() {
		
	}
	
	public Category(String detail) {
		this.id = this.generateID();
		this.detail = detail;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}
	
	public void save(Category category) {
		Category newCategory = new Category(category.getDetail());
		this.categoryRepository.save(newCategory);
	}
	
	public List<Category> searchByDetailSubstring(String detailSubstring){
		return this.categoryRepository.findByDetailLike(detailSubstring);
	}
}