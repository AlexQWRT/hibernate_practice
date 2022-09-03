package com.dzco.hibernatepractice.util;

import java.util.List;

public interface TableLogic<T> {
    int showTableMenu();
    void readRow();
    void createRow();
    void editRow();
    void deleteRow();
    void printAll();
    void executeTableMenu();
}
