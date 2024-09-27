package ru.yandex.incoming34.treeNodes;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class RootNode<T extends AbstractTreeNode> extends AbstractTreeNode{

    private static RootNode instance;
    private final Set<T> children = new HashSet<>();

    private RootNode() {
    }
    public static RootNode getinstance(){
        if (Objects.isNull(instance)) instance = new RootNode();
        return instance;
    }

    public Set<T> getChildren() {
        return children;
    }
}
