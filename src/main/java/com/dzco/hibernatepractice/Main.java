package com.dzco.hibernatepractice;

import com.dzco.hibernatepractice.logic.StartLogic;
import com.dzco.hibernatepractice.util.HibernateUtil;

public class Main {
    public static void main(String[] args) {
        HibernateUtil.getSessionFactory();
        new StartLogic().executeStartMenu();
    }
}
