package br.com.projetoFinalMicroservicesDevelopment.domain;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import br.com.projetoFinalMicroservicesDevelopment.repository.CategoryRepository;

@RunWith(MockitoJUnitRunner.class)
public class CategoryTest {
	
	private static final String ID = "123";
    private static final String DETAIL = "teste";
    
    private List<Category> categoriesResult;
    private Category categoryObject;

    @InjectMocks
    private Category categoryDomain = new Category();

    @Mock
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.categoryObject = new Category(DETAIL);
        this.categoriesResult = new ArrayList<>();
        this.categoriesResult.add(this.categoryObject);
    }

    @Test
    public void category_EmptyConstructorTest() {
        Category category = new Category();
        category.setId(ID);
        category.setDetail(DETAIL);

        Assert.assertEquals(category.getId(), ID);
        Assert.assertEquals(category.getDetail(), DETAIL);
    }

    @Test
    public void category_ConstructorWithParamsTest() {
        Category category = new Category(DETAIL);
        Assert.assertEquals(category.getDetail(), DETAIL);
    }

    @Test
    public void saveTest() {
        boolean isOk = true;
        try {
            this.categoryDomain.save(this.categoryObject);
        } catch (Exception e) {
            isOk = false;
        }
        Assert.assertTrue(isOk);
    }

    @Test
    public void searchByDetailSubstringTest() {
        Mockito.when(categoryRepository.findByDetailLike(this.categoryObject.getDetail()))
                .thenReturn(this.categoriesResult);

        String substring = this.categoryObject.getDetail();
        List<Category> result = this.categoryDomain.searchByDetailSubstring(substring);
        Assert.assertEquals(result, categoriesResult);
    }
}