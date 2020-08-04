package me.zhengjie.mongo.generator;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * entity字段名称节点
 *
 *
 **/
@Getter
@Setter
public class TreeNode {

    /**
     * 树的层级
     */
    private int seq;

    /**
     * 字段名称
     */
    private String fieldName;

    /**
     * 父节点
     */
    private TreeNode parant;

    /**
     * 所有的子节点
     */
    private List<TreeNode> subTreeNodeList;

    /**
     * 默认构造
     *
     * @param fieldName
     */
    public TreeNode(String fieldName) {
        this.fieldName = fieldName;
        this.subTreeNodeList = new ArrayList<>(0);
    }

    public TreeNode(int seq, String fieldName) {
        this.seq = seq;
        this.fieldName = fieldName;
        this.subTreeNodeList = new ArrayList<>(0);
    }

    public TreeNode(int seq, String fieldName, TreeNode parant) {
        this.seq = seq;
        this.fieldName = fieldName;
        this.parant = parant;
        this.subTreeNodeList = new ArrayList<>(0);
    }

    @Override
    public String toString() {
        return "TreeNode{" +
                "seq=" + seq +
                ", fieldName='" + fieldName + '\'' +
                ", parant=" + parant +
                ", subTreeNodeList=" + subTreeNodeList +
                '}';
    }
}
