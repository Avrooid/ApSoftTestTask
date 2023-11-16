package ru.Avrooid.ApSoftTestTask.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;
import ru.Avrooid.ApSoftTestTask.dataStructure.Tree;
import ru.Avrooid.ApSoftTestTask.dataStructure.TreeNode;
import ru.Avrooid.ApSoftTestTask.models.ProcessedFile;
import ru.Avrooid.ApSoftTestTask.repositories.FileRepository;
import ru.Avrooid.ApSoftTestTask.utils.ConverterUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @InjectMocks
    private FileService fileService;

    @Mock
    private FileRepository fileRepository;

    @BeforeEach
    void setUp() {
    }

    /**
     * Тест метода {@link FileService#getFileAndReturnData(MultipartFile)}
     */
    @Test
    void testGetFileAndReturnData() {
        String lines = "GREATEST MAN IN ALIVE\n" +
                "#Chapter one\n" +
                "this story about awesome dude that call name is Jack";
        MultipartFile multipartFile = new MockMultipartFile("file", "temp", null, lines.getBytes());
        Tree result = fileService.getFileAndReturnData(multipartFile);
        Tree expectedTree = makeExpectedTree();

        Assertions.assertNotNull(result);
        assertEquals(expectedTree, result);
    }

    /**
     * Тест метода {@link FileService#getFileAndReturnId(MultipartFile)}
     */
    @Test
    void testGetFileAndReturnId() {
        MockMultipartFile mockFile = new MockMultipartFile("file", "test.txt", "text/plain", "Hello, World!".getBytes());
        ProcessedFile processedFile = new ProcessedFile("data".getBytes());
        processedFile.setId(1L);

        when(fileRepository.save(any())).thenReturn(processedFile);
        Long fileId = fileService.getFileAndReturnId(mockFile);

        verify(fileRepository, times(1)).save(any());
        assertEquals(1L, fileId);
    }

    /**
     * тест метода {@link FileService#getTreeFromDB(Long)}
     */
    @Test
    void testGetTreeFromDB() {
        String lines = "GREATEST MAN IN ALIVE\n" +
                "#Chapter one\n" +
                "this story about awesome dude that call name is Jack";
        MultipartFile multipartFile = new MockMultipartFile("file", "temp", null, lines.getBytes());
        Tree result = fileService.getFileAndReturnData(multipartFile);
        ProcessedFile processedFile = new ProcessedFile(ConverterUtils.convertTo(result).getBytes());

        when(fileRepository.getReferenceById(anyLong())).thenReturn(processedFile);
        Tree actualTree = fileService.getTreeFromDB(1L);
        Tree expectedTree = makeExpectedTree();
        assertEquals(expectedTree, actualTree);
    }

    private Tree makeExpectedTree() {
        TreeNode<String> root = new TreeNode<>("GREATEST MAN IN ALIVE");

        TreeNode<String> treeNode = new TreeNode<>("Chapter one");
        treeNode.setData(List.of("this story about awesome dude that call name is Jack"));
        treeNode.setParent(root);
        treeNode.setLevel(1);

        Tree tree = new Tree();
        tree.setRoot(root);
        root.setChildren(List.of(treeNode));

        return tree;
    }
}