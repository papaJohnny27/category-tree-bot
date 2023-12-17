package kz.telegram.categorytreebot.service;

import kz.telegram.categorytreebot.dto.CategoryDto;

import java.util.List;

public interface CategoryService {

    /**
     * get all parent categories for specified user
     * @param userId userId
     * @return parent categories
     */
    List<CategoryDto> getAllParentByUserId(Long userId);

    /**
     * add child category for parent, if parent category is empty, then create new parent category
     * @param name category to create
     * @param parentName parent category
     * @param userId userId
     */
    void addCategory(String name, String parentName, Long userId);

    /**
     * remove category for specified user, if category has children, they are also will be removed
     * @param name category to remove
     * @param userId userId
     */
    void deleteCategory(String name, Long userId);

}
