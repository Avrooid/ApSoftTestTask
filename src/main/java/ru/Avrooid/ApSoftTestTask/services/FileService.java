package ru.Avrooid.ApSoftTestTask.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.Avrooid.ApSoftTestTask.dataStructure.Tree;
import ru.Avrooid.ApSoftTestTask.dataStructure.TreeNode;
import ru.Avrooid.ApSoftTestTask.models.ProcessedFile;
import ru.Avrooid.ApSoftTestTask.repositories.FileRepository;
import ru.Avrooid.ApSoftTestTask.utils.ConverterUtils;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс логики при работе с файловым контроллером
 */
@Service
@Slf4j
public class FileService {

    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    /**
     * Получает файл из контроллера, конвертирует его и возвращает структурированный json
     * @param file файл
     * @return дерево json
     */
    public Tree getFileAndReturnData(MultipartFile file) {
        try {
            File convertFile = convert(file);
            Tree tree = getDataFromFile(convertFile);
            log.info("Get json from file: " + file.getOriginalFilename());
            return tree;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Получает файл из контроллера, конвертирует, сохраняет его в БД и возвращает id, под которым сохранился файл
     * @param file файл
     * @return id
     */
    public Long getFileAndReturnId(MultipartFile file) {
        try {
            File convertFile = convert(file);
            Tree tree = getDataFromFile(convertFile);
            String jsonTree = ConverterUtils.convertTo(tree);
            ProcessedFile myFile = new ProcessedFile(jsonTree.getBytes());
            ProcessedFile savedFile = fileRepository.save(myFile);
            log.info("Saved in DB file: " + file.getOriginalFilename());
            return savedFile.getId();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Находит файл по id и возвращает его в виде структурированного json
     * @param id id файла
     * @return Дерево в виде json
     */
    public Tree getTreeFromDB(Long id) {
        ProcessedFile processedFile = fileRepository.getReferenceById(id);
        log.info("Get json from file in DB");
        return ConverterUtils.convertFrom(new String(processedFile.getJsonData()));
    }

    /**
     * Конвертирует MultipartFile в File
     * @param file файл для конвертации
     * @return File
     */
    private File convert(MultipartFile file) throws IOException {
        File fileToConvert = new File(file.getOriginalFilename());
        try (FileOutputStream fileOutputStream = new FileOutputStream(fileToConvert)) {
            fileOutputStream.write(file.getBytes());
        }

        return fileToConvert;
    }

    /**
     * Разбирает файл и делает структурированное дерево
     * @param file файл для разбора
     * @return Дерево(json)
     */
    private Tree getDataFromFile(File file) throws IOException {
        Tree tree = new Tree();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))){
            List<String> lines = bufferedReader.lines().toList();
            TreeNode<String> root = new TreeNode<>(lines.get(0));
            tree.setRoot(root);

            TreeNode<String> previousNode = root;
            for (String line : lines.subList(1, lines.toArray().length)) {
                int levelNodeHash = getCountHash(line);

                if (levelNodeHash == 0) {
                    saveDataInNode(previousNode, line);
                    continue;
                }

                String nodeValue = line.substring(levelNodeHash);
                TreeNode<String> currentNode = new TreeNode<>(nodeValue);
                currentNode.setLevel(levelNodeHash);

                if (levelNodeHash == previousNode.getLevel()) {
                    addNodeSameLevel(previousNode, currentNode);
                }
                else if (levelNodeHash > previousNode.getLevel()) {
                    addChild(previousNode, currentNode);
                }
                else if (levelNodeHash < previousNode.getLevel()) {
                    addNodeUpperLevel(previousNode, currentNode, levelNodeHash);
                }
                previousNode = currentNode;
            }
        }
        return tree;
    }

    /**
     * Сохраняет строку в поле data
     * @param previousNode предыдущий узел
     * @param line строка
     */
    private void saveDataInNode(TreeNode<String> previousNode, String line) {
        if (previousNode.getData() == null) {
            previousNode.setData(new ArrayList<>());
        }
        previousNode.getData().add(line);
    }

    /**
     * Находит родителя, которому надо добавить дочерний узел и добавляет его
     * @param previousNode предыдущий узел
     * @param currentNode текущий узел
     * @param levelNodeHash уровень вложенности
     */
    private void addNodeUpperLevel(TreeNode<String> previousNode, TreeNode<String> currentNode, int levelNodeHash) {
        while(levelNodeHash != previousNode.getLevel() + 1) {
            previousNode = previousNode.getParent();
        }
        if (previousNode.getChildren() == null) {
            previousNode.setChildren(new ArrayList<>());
        }
        previousNode.getChildren().add(currentNode);
        currentNode.setParent(previousNode);
    }

    /**
     * Добавляет узел на этот же уровень вложенности
     * @param previousNode предыдущий узел
     * @param currentNode текущий узел
     */
    private void addNodeSameLevel(TreeNode<String> previousNode, TreeNode<String> currentNode) {
        previousNode.getParent().getChildren().add(currentNode);
        currentNode.setParent(previousNode.getParent());
    }

    /**
     * Добавляет дочерний узел
     * @param previousNode предыдущий узел
     * @param currentNode текущий узел
     */
    private void addChild(TreeNode<String> previousNode, TreeNode<String> currentNode) {
        if (previousNode.getChildren() == null) {
            previousNode.setChildren(new ArrayList<>());
        }
        previousNode.getChildren().add(currentNode);
        currentNode.setParent(previousNode);
    }

    /**
     * Находит количество '#' в начале строки
     * @param line строка
     * @return количество '#'
     */
    private int getCountHash(String line) {
        int count = 0;
        for (Character character : line.toCharArray()) {
            if (character.equals('#')) {
                count++;
            } else {
                break;
            }
        }

        return count;
    }

}
