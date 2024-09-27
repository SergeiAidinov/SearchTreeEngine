package ru.yandex.incoming34.treeNodes;

import java.util.Set;

public abstract class AbstractTreeNode<T extends AbstractTreeNode> {

    public abstract Set<T> getChildren();
}
