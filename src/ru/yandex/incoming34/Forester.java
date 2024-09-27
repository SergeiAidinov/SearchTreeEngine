package ru.yandex.incoming34;

import ru.yandex.incoming34.treeNodes.AbstractTreeNode;
import ru.yandex.incoming34.treeNodes.ParentNode;
import ru.yandex.incoming34.treeNodes.RootNode;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.nio.file.Files.walk;

@SuppressWarnings({"unchecked"})
public class Forester<T extends AbstractTreeNode> {

    private static Forester instance;
    private final RootNode rootNode =  RootNode.getinstance();
    List<Class<T>> validElements;

    private Forester() {
    }

    public static Forester getInstance() {
        if (Objects.isNull(instance)) instance = new Forester();
        return instance;
    }

    public Set<T> growTree() {
        plantTree();
        recursiveCircuit((T) rootNode);
        return rootNode.getChildren();
    }

    public void recursiveCircuit(T node) {
        Set<T> children = node.getChildren();
        for (T childNode : children) {
            if (childNode.getChildren().isEmpty()) {
                goThroughCollection(childNode);
                recursiveCircuit(childNode);
            }
        }
    }

    private void plantTree() {
        final List<Path> filePaths = collectPaths();
        validElements = Collections.unmodifiableList(removeInvalidElements(filePaths));
        goThroughCollection((T) RootNode.getinstance());
    }

    private List<Class<T>> removeInvalidElements(List<Path> filePaths) {
        final List<Class<T>> classes = new ArrayList<>();
        for (Path path : filePaths) {
            Class<T> klass = loadNode(path);
            if (Objects.nonNull(klass)) classes.add(klass);
        }
        classes.removeIf(clazz -> clazz.getSuperclass() != AbstractTreeNode.class
                || !clazz.isAnnotationPresent(ParentNode.class)
                || Modifier.isAbstract(clazz.getModifiers()));
        return classes;
    }

    private void goThroughCollection(T node) {
        for (Class<T> klass : validElements) {
            if (klass.getAnnotation(ParentNode.class).parentName().equals(node.getClass())) {
                try {
                    node.getChildren().add(klass.getDeclaredConstructor().newInstance());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
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
}
