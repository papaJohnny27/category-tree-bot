package kz.telegram.categorytreebot.bot;

import kz.telegram.categorytreebot.dto.CategoryDto;
import kz.telegram.categorytreebot.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class ViewTreeCommandHandler implements TelegramBotCommandHandler {

    public static final String VIEW_TREE_COMMAND = "/viewTree";

    private final CategoryService categoryService;

    @Override
    public String handle(String command, Long userId) {

        List<CategoryDto> categories = categoryService.getAllParentByUserId(userId);

        StringBuilder builder = new StringBuilder();
        return buildTree(builder, categories, "", false, 0);
    }

    @Override
    public boolean shouldHandle(String command) {
        return command.startsWith(VIEW_TREE_COMMAND);
    }

    private String buildTree(StringBuilder builder, List<CategoryDto> categories, String prefix, boolean isLast, int level) {

        categories.sort(Comparator.comparing(CategoryDto::getId));
        for (int i = 0; i < categories.size(); i++) {

            CategoryDto category = categories.get(i);

            String name = category.getName();
            int position = 0;
            if (i == categories.size()-1) {
                position = -1;
            }

            String tab = "";
            if (isLast) {
                tab = prefix + "\t";
            } else if (level != 0) {
                tab = prefix + "|\t";
            }
            writeEntry(builder, name, position, tab);

            buildTree(builder, new ArrayList<>(category.getChildren()), tab, position == -1, level+1);
        }

        return builder.toString();
    }

    private void writeEntry(StringBuilder builder, String name, int position, String tab){
        String prefix = "";
        if (position == -1) {
            prefix = "└───";
        } else {
            prefix = "├───";
        }
        builder.append(tab).append(prefix).append(name).append("\n");
    }
}
