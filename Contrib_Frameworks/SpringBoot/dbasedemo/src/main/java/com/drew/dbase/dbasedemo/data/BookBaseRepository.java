// Having the proper package visibility is FUNDAMENTAL
package com.drew.dbase.dbasedemo.data;

import java.util.List;

import com.drew.dbase.dbasedemo.models.Book;

public interface BookBaseRepository {
    
    // Only the operations to be done to the data source need to be present
    int count();
    int save(Book book);
    int update(Book book);
    List<Book> getSelection();
    List<Book> findAll();
    int remove(Book book);

}