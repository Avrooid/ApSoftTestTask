package ru.Avrooid.ApSoftTestTask.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.Avrooid.ApSoftTestTask.dataStructure.Tree;

/**
 *  Вспомогательный класс-конвертер
 */
public final class ConverterUtils {

    /**
     * Конвертирует дерево в строку для хранения в Бд
     * @param tree Дерево
     */
    public static String convertTo(Tree tree) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.writeValueAsString(tree);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Конвертирует из строки в дерево для отображения в виде структурированного json
     * @param jsonData файл в виде строки
     * @return Дерево
     */
    public static Tree convertFrom(String jsonData) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonData, Tree.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
