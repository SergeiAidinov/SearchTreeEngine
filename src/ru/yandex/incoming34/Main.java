package ru.yandex.incoming34;

import ru.yandex.incoming34.treeNodes.nodes.AnotherSectionNode;

import java.util.Set;

public class Main {
    public static void main(String[] args) {
        SearchTreeKeeper searchTreeKeeper = SearchTreeKeeper.of(Forester.getInstance().growTree());
        System.out.println(searchTreeKeeper);
    }
}