package ru.yandex.incoming34.treeNodes;

import java.util.Set;
import java.util.function.Predicate;

public abstract class AbstractTreeNode<T extends AbstractTreeNode<T, P>, P> {
    public abstract Set<Predicate<P>> getConditions();
    public abstract Set<T> getChildren();
    public abstract void setParent(AbstractTreeNode _parent);
    public abstract AbstractTreeNode getParent();
}
