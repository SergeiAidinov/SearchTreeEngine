package ru.yandex.incoming34.treeNodes;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;

public class RootNode<T extends AbstractTreeNode<T, P>, P> extends AbstractTreeNode<T, P>{

    private static RootNode instance;
    private final Set<T> children = new HashSet<>();

    private RootNode() {
    }
    public static RootNode getinstance(){
        if (Objects.isNull(instance)) instance = new RootNode();
        return instance;
    }

    @Override
    public Set<Predicate<P>> getConditions() {
        return Set.of();
    }

    public Set<T> getChildren() {
        return children;
    }

    @Override
    public void setParent(AbstractTreeNode _parent) {

    }

    @Override
    public AbstractTreeNode getParent() {
        return null;
    }


}
