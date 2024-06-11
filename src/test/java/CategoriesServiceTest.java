import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.testeo.models.CategoryModel;
import com.example.testeo.repositories.CategoryRepository;
import com.example.testeo.service.CategoryService;

public class CategoriesServiceTest {

    @Mock
    private CategoryRepository categoryRepository;

    @InjectMocks
    private CategoryService categoryService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void saveCategoryTest() {
        CategoryModel category = new CategoryModel();
        category.setName("Celular");

        when(categoryRepository.save(any(CategoryModel.class))).thenReturn(category);
        CategoryModel savedCategory = categoryService.saveCategory(category);
        assertEquals("Celular", savedCategory.getName(), "El nombre de la categoría debe ser 'Celular'");
    }

    @Test
    public void categoryByIdTtest() {
        CategoryModel category = new CategoryModel();
        category.setId(1L);
        category.setName("Celular");

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Optional<CategoryModel> foundCategory = categoryService.findById(1L);
        assertTrue(foundCategory.isPresent(), "Debería encontrar la categoría");
        assertEquals("Celular", foundCategory.get().getName(), "El nombre de la categoría debe ser 'Celular'");
    }
}