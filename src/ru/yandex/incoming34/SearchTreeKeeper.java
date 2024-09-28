package ru.yandex.incoming34;

import ru.yandex.incoming34.treeNodes.AbstractTreeNode;
import ru.yandex.incoming34.treeNodes.RootNode;

import java.util.*;

public class SearchTreeKeeper<T extends AbstractTreeNode> {

    private static SearchTreeKeeper instance;
    private final List<T> searchTree;

    private SearchTreeKeeper(List<T> searchTree) {
        this.searchTree = Collections.unmodifiableList(searchTree);
    }

    public static SearchTreeKeeper of (Set nodes){
        if (Objects.isNull(instance)) instance = new SearchTreeKeeper(Collections.singletonList(nodes));
        return instance;
    }

    public List<T> getSearchTree() {
        return searchTree;
    }
}
