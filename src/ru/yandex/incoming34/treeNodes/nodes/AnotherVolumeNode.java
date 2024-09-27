package ru.yandex.incoming34.treeNodes.nodes;

import ru.yandex.incoming34.treeNodes.AbstractTreeNode;
import ru.yandex.incoming34.treeNodes.ParentNode;
import ru.yandex.incoming34.treeNodes.RootNode;

import java.util.HashSet;
import java.util.Set;

@ParentNode(parentName = RootNode.class)
public class AnotherVolumeNode<T extends AbstractTreeNode> extends AbstractTreeNode {

    private final Set<T> children = new HashSet<>();

    @Override
    public Set<T> getChildren() {
        return children;
    }
}
