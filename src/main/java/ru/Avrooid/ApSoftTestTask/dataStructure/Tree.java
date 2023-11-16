package ru.Avrooid.ApSoftTestTask.dataStructure;

import java.util.List;
import java.util.Objects;

/**
 * Дерево
 */
public class Tree {

    /**
     * Корневой узел
     */
    private TreeNode<String> root;

    public TreeNode<String> getRoot() {
        return root;
    }

    public void setRoot(TreeNode<String> root) {
        this.root = root;
    }

    /**
     * Метод для поиска узла по тексту(навигация)
     * @param text строка для поиска
     * @return найденный узел
     */
    public TreeNode<String> findNodeByText(String text) {
        return recursiveFind(root, text);
    }

    /**
     * Рекурсивный поиск узла в дереве
     * @param currentNode текущий узел
     * @param text строка для поиска
     * @return найденный узел
     */
    private TreeNode<String> recursiveFind(TreeNode<String> currentNode, String text) {
        if (currentNode == null)
            return null;

        if (currentNode.getValue().equals(text) || (currentNode.getData() != null && currentNode.getData().contains(text))) {
            return currentNode;
        }

        if (currentNode.getChildren() != null) {
            for (TreeNode<String> child : currentNode.getChildren()) {
                TreeNode<String> node = recursiveFind(child, text);
                if (node != null)
                    return node;
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Tree tree = (Tree) o;
        return Objects.equals(root, tree.root);
    }

    @Override
    public int hashCode() {
        return Objects.hash(root);
    }
}
