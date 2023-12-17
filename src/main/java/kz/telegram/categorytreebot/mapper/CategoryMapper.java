package kz.telegram.categorytreebot.mapper;

import kz.telegram.categorytreebot.dto.CategoryDto;
import kz.telegram.categorytreebot.model.Category;

import java.util.stream.Collectors;

public class CategoryMapper {

    public static CategoryDto mapToDto(Category category) {

        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .children(category.getChildren().stream().map(CategoryMapper::mapToDto).collect(Collectors.toSet()))
                .build();
    }

}
