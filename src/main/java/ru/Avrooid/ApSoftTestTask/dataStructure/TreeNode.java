package ru.Avrooid.ApSoftTestTask.dataStructure;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Objects;

/**
 * Узел в дереве
 */
public class TreeNode<T> {

    /**
     * Заголовок
     */
    @JsonProperty("value")
    private String value;

    /**
     * Строки, не попавшие в заголовок
     */
    @JsonProperty("data")
    private List<T> data;

    /**
     * Ссылка на родителя
     */
    @JsonIgnore
    private TreeNode<T> parent;

    /**
     * Ссылка на потомков
     */
    @JsonProperty("children")
    private List<TreeNode<T>> children;

    /**
     * Уровень вложенности
     */
    @JsonIgnore
    private int level;

    public TreeNode(String value) {
        this.value = value;
    }

    public TreeNode(@JsonProperty("value") String value,
                    @JsonProperty("data") List<T> data,
                    @JsonProperty("children") List<TreeNode<T>> children) {
        this.value = value;
        this.data = data;
        this.children = children;
    }


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public TreeNode<T> getParent() {
        return parent;
    }

    public void setParent(TreeNode<T> parent) {
        this.parent = parent;
    }

    public List<TreeNode<T>> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode<T>> children) {
        this.children = children;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TreeNode<?> treeNode = (TreeNode<?>) o;
        return Objects.equals(value, treeNode.value) && Objects.equals(data, treeNode.data) && Objects.equals(children, treeNode.children);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value, data, children);
    }
}
