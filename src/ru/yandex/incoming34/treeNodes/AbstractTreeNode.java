package ru.yandex.incoming34.treeNodes;

import java.util.Set;
public abstract class AbstractTreeNode<T extends AbstractTreeNode> {
    public abstract Set<T> getChildren();

    public abstract void setParent(AbstractTreeNode _parent);

    public abstract AbstractTreeNode getParent();
   /* public abstract void setParent(T _parent);
    public abstract T getParent();*/
}
