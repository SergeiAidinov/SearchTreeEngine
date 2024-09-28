package ru.yandex.incoming34.treeNodes.nodes;

import ru.yandex.incoming34.treeNodes.AbstractTreeNode;
import ru.yandex.incoming34.treeNodes.ParentNode;
import ru.yandex.incoming34.treeNodes.RootNode;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

@ParentNode(parentName = RootNode.class)
public class VolumeNode<T extends AbstractTreeNode<T, P>, P> extends AbstractTreeNode<T, P> {

    private final Set<T> children = new HashSet<>();
    private T parent;

    @Override
    public Set<Predicate<P>> getConditions() {
        return Set.of();
    }

    @Override
    public Set<T> getChildren() {
        return children;
    }

    @Override
    public void setParent(AbstractTreeNode _parent) {
        parent = (T) _parent;
    }

    @Override
    public AbstractTreeNode getParent() {
        return parent;
    }


}
