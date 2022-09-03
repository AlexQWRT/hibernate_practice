package com.dzco.hibernatepractice.logic;

import com.dzco.hibernatepractice.entity.Author;
import com.dzco.hibernatepractice.helper.AuthorHelper;
import com.dzco.hibernatepractice.util.MenuUtil;
import com.dzco.hibernatepractice.util.TableLogic;
import com.dzco.hibernatepractice.util.TableLogicUtil;

import java.util.List;
import java.util.Scanner;

public class AuthorLogic extends TableLogicUtil implements TableLogic<Author> {
    private final AuthorHelper helper;
    public AuthorLogic() {
        super();
        helper = new AuthorHelper();
    }
    public AuthorLogic(Scanner in) {
        super(in);
        helper = new AuthorHelper();
    }

    @Override
    public void readRow() {
        System.out.print("Введите идентификатор автора: ");
        int id = MenuUtil.readInt(in);
        Author author = helper.getAuthor(id);
        if (author == null) {
            System.out.println("Автор не найден!");
            return;
        }
        System.out.println(author.getName());
    }

    @Override
    public void createRow() {
        System.out.print("Введите имя автора: ");
        String name = in.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Не удалось добавить автора! Введено пустое имя.");
            return;
        }
        if (helper.createAuthor(name)) {
            System.out.println("Автор добавлен успешно!");
            return;
        }
        System.out.println("Не удалось добавить автора!");
    }

    @Override
    public void editRow() {
        printAll();
        System.out.println("Введите идентификатор поэта: ");
        int id = MenuUtil.readInt(0, in);
        System.out.print("Введите имя автора: ");
        String name = in.nextLine().trim();
        if (name.isEmpty()) {
            System.out.println("Не удалось обновить автора! Введено пустое имя.");
            return;
        }
        if (helper.updateAuthor(id, name)) {
            System.out.println("Автор обновлён успешно!");
            return;
        }
        System.out.println("Не удалось обновить автора!");
    }

    @Override
    public void deleteRow() {
        printAll();
        System.out.println("Введите идентификатор поэта: ");
        int id = MenuUtil.readInt(0, in);
        if (helper.deleteAuthor(id)) {
            System.out.println("Автор удалён успешно!");
            return;
        }
        System.out.println("Не удалось удалить автора!");
    }

    @Override
    public void printAll() {
        List<Author> authors = helper.getAuthorList();
        if (authors == null) {
            System.out.println("Таблица пуста!");
            return;
        }
        for (Author author: authors) {
            System.out.println(author);
        }
    }

    @Override
    public void executeTableMenu() {
        int choice = 0;

        while((choice = showTableMenu()) != 0) {
            switch(choice) {
                case 1:
                    readRow();
                    break;
                case 2:
                    createRow();
                    break;
                case 3:
                    editRow();
                    break;
                case 4:
                    deleteRow();
                    break;
                case 5:
                    printAll();
                    break;
            }
        }
    }
}
