package kz.telegram.categorytreebot.mapper;

import kz.telegram.categorytreebot.TestDataUtil;
import kz.telegram.categorytreebot.dto.CategoryDto;
import kz.telegram.categorytreebot.model.Category;
import org.junit.jupiter.api.Test;

import java.util.stream.Collectors;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;
import static org.assertj.core.api.Assertions.assertThat;

public class CategoryMapperTest {

    @Test
    void mapToDto_expectOk() {
        final Long userId = Long.parseLong(randomNumeric(10));
        final Category category = TestDataUtil.aCategory(userId);

        final CategoryDto categoryDto = CategoryMapper.mapToDto(category);

        assertThat(categoryDto).usingRecursiveComparison().isEqualTo(convert(category));
    }

    private CategoryDto convert(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .children(category.getChildren().stream().map(this::convert).collect(Collectors.toSet()))
                .build();
    }

}
