package ru.yandex.incoming34;

import ru.yandex.incoming34.treeNodes.AbstractTreeNode;
import ru.yandex.incoming34.treeNodes.ParentNode;
import ru.yandex.incoming34.treeNodes.RootNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.nio.file.Files.walk;

public class TreeKeeper<T extends AbstractTreeNode> {

    private static TreeKeeper instance;
    //private final Set<T> searchTree = new HashSet<>();
    private final RootNode rootNode =  RootNode.getinstance();

    private TreeKeeper() {
        growTree();
        System.out.println();
    }

    private void growTree() {
        List<Path> qq = plantTree();
        recursiveCircuit((T) rootNode, qq);



    }

    public void recursiveCircuit(T node, List<Path> restFilePaths) {
        List<Path> diminishedPaths = null;
        Set<T> children = node.getChildren();
        for (T childNode : children) {
            if (childNode.getChildren().isEmpty()) {
                diminishedPaths = goThroughCollection(childNode, /*parentClass, */restFilePaths);
                recursiveCircuit(childNode, diminishedPaths);
            }
        }
    }

    private List<Path> plantTree() {
        final List<Path> filePaths = collectPaths();
        Class<T> parentClass = (Class<T>) rootNode.getClass();
        return goThroughCollection((T) RootNode.getinstance(), /*parentClass,*/ filePaths);
    }

    private List<Path> goThroughCollection(T node,/* T parentClass, */List<Path> filePaths) {
        final ListIterator<Path> listIterator = filePaths.listIterator();
        while (listIterator.hasNext()) {
            Path onePath = listIterator.next();
            Class<T> klass = loadNode(onePath);
            if (Objects.isNull(klass)) {
                listIterator.remove();
                continue;
            }
            if (klass.isAnnotationPresent(ParentNode.class)
                    && klass.getAnnotation(ParentNode.class).parentName().equals(node.getClass())) {
                try {
                    node.getChildren().add(klass.newInstance());
                    listIterator.remove();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        return filePaths;
    }

    private Class<T> loadNode(Path onePath) {
        Class<T> klass = null;
        try {
            String fileName = onePath.toString().substring(
                            onePath.toString().lastIndexOf("ru"),
                            onePath.toString().lastIndexOf("."))
                    .replace(File.separator, ".");
            klass = (Class<T>) Class.forName(fileName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return klass;
    }

    private List<Path> collectPaths() {
        final String nodeDirectory = System.getenv("PWD") + File.separator + "src"
                + File.separator
                + "ru"
                + File.separator
                + "yandex"
                + File.separator
                + "incoming34"
                + File.separator
                + "treeNodes"
                + File.separator
                + "nodes";
        List<Path> filePaths = new ArrayList<>();
        try (Stream<Path> paths = walk(Paths.get(nodeDirectory))) {
            paths.filter(Files::isRegularFile).forEach(filePaths::add);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return filePaths;
    }

    public static TreeKeeper getInstance() {
        if (Objects.isNull(instance)) instance = new TreeKeeper();
        return instance;
    }


}
