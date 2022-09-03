package com.dzco.hibernatepractice.util;

import java.util.Scanner;

public class TableLogicUtil{
    protected Scanner in;
    protected TableLogicUtil() { in = new Scanner(System.in); }
    protected TableLogicUtil(Scanner in) { this.in = in; }

    public int showTableMenu() {
        System.out.println("=====Выберите действие=====");
        System.out.println("1. Просмотреть запись");
        System.out.println("2. Создать запись");
        System.out.println("3. Редактировать запись");
        System.out.println("4. Удалить запись");
        System.out.println("5. Просмотреть все записи");
        System.out.println("0. Назад");
        return MenuUtil.readInt(0, 5, in);
    }

}
