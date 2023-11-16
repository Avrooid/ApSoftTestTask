package ru.Avrooid.ApSoftTestTask.controllers;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.Avrooid.ApSoftTestTask.dataStructure.Tree;
import ru.Avrooid.ApSoftTestTask.services.FileService;

/**
 * Контроллер для сохранения файлов в БД и получения файлов в виде структурированного Json
 */
@RestController
@RequestMapping("/load/file")
public class FileController {

    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    /**
     * Получение файла и преобразование его в структурированный json
     * @param file текстовый файл
     * @return Дерево в виде json
     */
    @PostMapping("/json")
    public Tree getJSON(@RequestParam("file") MultipartFile file) {
        return fileService.getFileAndReturnData(file);
    }

    /**
     * Сохранение файла в БД
     * @param file текстовый файл
     * @return id под которым сохранился файл
     */
    @PostMapping("/id")
    public Long getId(@RequestParam("file") MultipartFile file) {
        return fileService.getFileAndReturnId(file);
    }

    /**
     * Получение структурированного json из уже существующего файла
     * @param id id файла из БД
     * @return Дерево в виде json
     */
    @GetMapping("/{id}")
    public Tree getJsonFromDB(@PathVariable Long id) {
        return fileService.getTreeFromDB(id);
    }
}
