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
    private final RootNode rootNode = RootNode.getinstance();

    private TreeKeeper() {
        this.tree = growTree();
    }

    private Set<? extends AbstractTreeNode> growTree() {
        final List<Path> filePaths = collectPaths();
        Class<? extends AbstractTreeNode> parentClass = rootNode.getClass();
        while (true) {
            int initialQuantity = filePaths.size();
            ListIterator<Path> listIterator = filePaths.listIterator();
            if (listIterator.hasNext()) {
                Path onePath = listIterator.next();
                listIterator.remove();
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
                if (Objects.isNull(klass)) continue;
                if (klass.isAnnotationPresent(ParentNode.class)
                        && klass.getAnnotation(ParentNode.class).parentName().equals(parentClass)) {
                    try {
                        rootNode.getChildren().add(klass.newInstance());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

            }
            if (filePaths.size() >= initialQuantity) break;

        }
        return Collections.EMPTY_SET;
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

    private final Set<? extends AbstractTreeNode> tree;

    public static TreeKeeper getInstance() {
        if (Objects.isNull(instance)) instance = new TreeKeeper();
        return instance;
    }


}
