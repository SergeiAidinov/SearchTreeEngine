package ru.yandex.incoming34.treeNodes.nodes;

import ru.yandex.incoming34.treeNodes.AbstractTreeNode;
import ru.yandex.incoming34.treeNodes.ParentNode;

import java.util.HashSet;
import java.util.Set;

@ParentNode(parentName = VolumeNode.class)
public class SectionNode<T extends AbstractTreeNode> extends AbstractTreeNode {

    private final Set<T> children = new HashSet<>();
    private T parent;

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
