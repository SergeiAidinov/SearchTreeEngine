package ru.yandex.incoming34;

import ru.yandex.incoming34.treeNodes.AbstractTreeNode;
import ru.yandex.incoming34.treeNodes.RootNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchTreeConvertor<T extends AbstractTreeNode<T>> {

    private final List<T> leaves = new ArrayList<>();

    public void convertToUnmodifiableList(RootNode rootNode) {
        collectLeaves(rootNode.getChildren());
        for (T node : leaves) {

        }
        System.out.println();
    }

    private void collectLeaves(Set<T> children) {
        for (T child : children) {
            if (child.getChildren().isEmpty()) {
                leaves.add(child);
            } else {
                collectLeaves(child.getChildren());
            }
        }
    }
}
