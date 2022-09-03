package com.dzco.hibernatepractice.logic;

import com.dzco.hibernatepractice.logic.AuthorLogic;
import com.dzco.hibernatepractice.logic.BookLogic;
import com.dzco.hibernatepractice.util.MenuUtil;

import java.util.Scanner;

public class StartLogic {
    private Scanner in;
    public StartLogic() {
        in = new Scanner(System.in);
    }

    private int showStartMenu() {
        System.out.println("=====Выберите таблицу для работы=====");
        System.out.println("1. author");
        System.out.println("2. book");
        System.out.println("0. Выход");
        return MenuUtil.readInt(0, 2, in);
    }

    public void executeStartMenu() {
        int choice = 0;

        while((choice = showStartMenu()) != 0) {
            switch(choice) {
                case 1:
                    new AuthorLogic(in).executeTableMenu();
                    break;
                case 2:
                    new BookLogic(in).executeTableMenu();
                    break;
            }
        }
    }

}
