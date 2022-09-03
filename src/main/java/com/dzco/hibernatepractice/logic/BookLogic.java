package com.dzco.hibernatepractice.logic;

import com.dzco.hibernatepractice.entity.Author;
import com.dzco.hibernatepractice.entity.Book;
import com.dzco.hibernatepractice.helper.AuthorHelper;
import com.dzco.hibernatepractice.helper.BookHelper;
import com.dzco.hibernatepractice.util.MenuUtil;
import com.dzco.hibernatepractice.util.TableLogic;
import com.dzco.hibernatepractice.util.TableLogicUtil;

import java.util.List;
import java.util.Scanner;

public class BookLogic extends TableLogicUtil implements TableLogic<Book> {

    private final BookHelper bookHelper;
    private final AuthorHelper authorHelper;
    public BookLogic() {
        super();
        bookHelper = new BookHelper();
        authorHelper = new AuthorHelper();
    }
    public BookLogic(Scanner in) {
        super(in);
        bookHelper = new BookHelper();
        authorHelper = new AuthorHelper();
    }
    @Override
    public void readRow() {
        System.out.print("Введите идентификатор книги: ");
        int id = MenuUtil.readInt(0, in);
        Book book = bookHelper.getBook(id);
        if (book == null) {
            System.out.println("Книга не найдена!");
            return;
        }
        System.out.println(book.getName());
    }

    @Override
    public void createRow() {
        System.out.print("Введите название книги: ");
        String bookName = in.nextLine().trim();
        if (bookName.isEmpty()) {
            System.out.println("Не удалось создать книгу! Введено пустое название.");
            return;
        }
        System.out.print("Введите имя автора: ");
        String authorName = in.nextLine().trim();
        if (authorName.isEmpty()) {
            System.out.println("Не удалось создать книгу! Введено пустое имя автора.");
            return;
        }
        if (bookHelper.createBook(bookName, authorHelper.getAuthor(authorName))) {
            System.out.println("Книга успешно создана!");
            return;
        }
        System.out.println("Не удалось создать книгу!");
    }

    @Override
    public void editRow() {
        printAll();
        System.out.print("Введите идентификатор книги: ");
        int id = MenuUtil.readInt(0, in);
        System.out.print("Введите название книги (нажмите Enter чтобы оставить без изменений): ");
        String bookName = in.nextLine().trim();
        System.out.print("Введите имя автора (нажмите Enter чтобы оставить без изменений): ");
        String authorName = in.nextLine().trim();
        if (bookHelper.updateBook(id, bookName, authorName)) {
            System.out.println("Автор обновлён успешно!");
            return;
        }
        System.out.println("Не удалось обновить книгу!");
    }

    @Override
    public void deleteRow() {
        printAll();
        System.out.print("Введите идентификатор книги: ");
        int id = MenuUtil.readInt(0, in);
        if (bookHelper.deleteBook(id)) {
            System.out.println("Кника удалена успешно!");
            return;
        }
        System.out.println("Не удалось удалить книгу!");
    }

    @Override
    public void printAll() {
        List<Book> books = bookHelper.getBookList();
        if (books == null) {
            System.out.println("Таблица пуста!");
            return;
        }
        for (Book book: books) {
            System.out.println(book);
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
