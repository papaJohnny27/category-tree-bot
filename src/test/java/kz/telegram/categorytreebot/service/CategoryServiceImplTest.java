package kz.telegram.categorytreebot.service;

import kz.telegram.categorytreebot.dto.CategoryDto;
import kz.telegram.categorytreebot.exception.CategoryAlreadyExistsException;
import kz.telegram.categorytreebot.exception.CategoryNotFoundException;
import kz.telegram.categorytreebot.mapper.CategoryMapper;
import kz.telegram.categorytreebot.model.Category;
import kz.telegram.categorytreebot.repository.CategoryRepository;
import kz.telegram.categorytreebot.service.impl.CategoryServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static kz.telegram.categorytreebot.TestDataUtil.aCategory;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CategoryServiceImplTest {

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Mock
    private CategoryRepository categoryRepository;

    @Test
    void getAllParentByUserId_expectOk() {
        final long userId = Long.parseLong(randomNumeric(10));
        final List<Category> expectedCategories = getCategories(userId);
        when(categoryRepository.findAllByUserIdAndParentIsNull(userId))
                .thenReturn(expectedCategories);

        final List<CategoryDto> result = categoryService.getAllParentByUserId(userId);

        assertEquals(1, result.size());
        assertThat(result.get(0)).usingRecursiveComparison()
                .isEqualTo(CategoryMapper.mapToDto(expectedCategories.get(0)));
    }

    @Test
    void addCategory_whenCategoryExist_thenThrowException() {
        final String name = randomAlphabetic(10);
        final Long userId = Long.parseLong(randomNumeric(10));
        when(categoryRepository.existsByNameAndUserId(name, userId)).thenReturn(true);

        final CategoryAlreadyExistsException exception = assertThrows(CategoryAlreadyExistsException.class,
                () -> categoryService.addCategory(name, "", userId));

        assertEquals(String.format("Category %s already exist", name), exception.getMessage());
    }

    @Test
    void addCategory_whenPassedParentNameNotExist_thenThrowException() {
        final String name = randomAlphabetic(10);
        final String parentName = randomAlphabetic(10);
        final Long userId = Long.parseLong(randomNumeric(10));
        when(categoryRepository.existsByNameAndUserId(name, userId)).thenReturn(false);
        when(categoryRepository.findByNameAndUserId(parentName, userId)).thenReturn(Optional.empty());

        final CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                () -> categoryService.addCategory(name, parentName, userId));

        assertEquals(String.format("Category %s not found", parentName), exception.getMessage());
    }

    @Test
    void addCategory_whenParentNameIsPassed_thenSaveWithParent() {
        final String name = randomAlphabetic(10);
        final String parentName = randomAlphabetic(10);
        final Long userId = Long.parseLong(randomNumeric(10));
        final Category parentCategory = aCategory(userId);
        final ArgumentCaptor<Category> captor = ArgumentCaptor.forClass(Category.class);
        when(categoryRepository.existsByNameAndUserId(name, userId)).thenReturn(false);
        when(categoryRepository.findByNameAndUserId(parentName, userId)).thenReturn(Optional.of(parentCategory));

        categoryService.addCategory(name, parentName, userId);

        verify(categoryRepository).save(captor.capture());
    }

    @Test
    void deleteCategory_whenCategoryNotExist_thenThrowException() {
        final String name = randomAlphabetic(10);
        final Long userId = Long.parseLong(randomNumeric(10));
        when(categoryRepository.existsByNameAndUserId(name, userId)).thenReturn(false);

        final CategoryNotFoundException exception = assertThrows(CategoryNotFoundException.class,
                () -> categoryService.deleteCategory(name, userId));

        assertEquals(String.format("Category %s not found", name), exception.getMessage());
    }

    @Test
    void deleteCategory_expectOk() {
        final String name = randomAlphabetic(10);
        final Long userId = Long.parseLong(randomNumeric(10));
        when(categoryRepository.existsByNameAndUserId(name, userId)).thenReturn(true);

        categoryService.deleteCategory(name, userId);

        verify(categoryRepository).deleteByNameAndUserId(name, userId);
    }

    private List<Category> getCategories(Long userId) {
        return List.of(aCategory(userId));
    }


}
