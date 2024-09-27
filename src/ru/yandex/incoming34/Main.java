package ru.yandex.incoming34;

public class Main {
    public static void main(String[] args) {
        SearchTreeKeeper searchTreeKeeper = SearchTreeKeeper.of(Forester.getInstance().growTree());
        System.out.println(searchTreeKeeper);
    }
}