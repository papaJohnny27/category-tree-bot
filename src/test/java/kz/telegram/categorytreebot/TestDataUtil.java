package kz.telegram.categorytreebot;

import kz.telegram.categorytreebot.dto.CategoryDto;
import kz.telegram.categorytreebot.model.Category;

import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang3.RandomStringUtils.randomAlphabetic;
import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class TestDataUtil {

    public static Category aCategory(Long userId) {
        return Category.builder()
                .id(Long.parseLong(randomNumeric(10)))
                .userId(userId)
                .name(randomAlphabetic(10))
                .children(new HashSet<>())
                .children(Set.of(
                        Category.builder()
                                .id(Long.parseLong(randomNumeric(10)))
                                .name(randomAlphabetic(10))
                                .userId(userId)
                                .children(new HashSet<>())
                                .build(),
                        Category.builder()
                                .id(Long.parseLong(randomNumeric(10)))
                                .name(randomAlphabetic(10))
                                .userId(userId)
                                .children(new HashSet<>())
                                .build()))
                .build();
    }


}
