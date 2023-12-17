package kz.telegram.categorytreebot.service.impl;

import kz.telegram.categorytreebot.dto.CategoryDto;
import kz.telegram.categorytreebot.exception.CategoryAlreadyExistsException;
import kz.telegram.categorytreebot.exception.CategoryNotFoundException;
import kz.telegram.categorytreebot.mapper.CategoryMapper;
import kz.telegram.categorytreebot.model.Category;
import kz.telegram.categorytreebot.repository.CategoryRepository;
import kz.telegram.categorytreebot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    @Override
    public List<CategoryDto> getAllParentByUserId(Long userId) {
        return categoryRepository.findAllByUserIdAndParentIsNull(userId).stream()
                .map(CategoryMapper::mapToDto).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void addCategory(String name, String parentName, Long userId) {

        if (categoryRepository.existsByNameAndUserId(name, userId)) {
            throw new CategoryAlreadyExistsException(String.format("Category %s already exist", name));
        }

        Category parent = null;
        if (StringUtils.isNotBlank(parentName)) {
            parent = categoryRepository.findByNameAndUserId(parentName, userId)
                    .orElseThrow(() -> new CategoryNotFoundException(String.format("Category %s not found", parentName)));
        }

        Category category = Category.builder()
                .name(name)
                .parent(parent)
                .userId(userId)
                .build();
        categoryRepository.save(category);
    }

    @Override
    @Transactional
    public void deleteCategory(String name, Long userId) {

        if (!categoryRepository.existsByNameAndUserId(name, userId)) {
            throw new CategoryNotFoundException(String.format("Category %s not found", name));
        }

        categoryRepository.deleteByNameAndUserId(name, userId);
    }
}
