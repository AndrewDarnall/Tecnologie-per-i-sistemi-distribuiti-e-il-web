package com.drew.dbase.dbasedemo.data;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.drew.dbase.dbasedemo.models.Book;
import java.util.List;

@Repository
public class BookRepository implements BookBaseRepository {
    
    private JdbcTemplate jdbctemplate;

    @Autowired
    public BookRepository(JdbcTemplate jdbctemplate) {
        this.jdbctemplate = jdbctemplate;
    }


    @Override
    public int count() {
        return jdbctemplate.queryForObject("select count(*) from Books", Integer.class);
    }

    @Override
    public int save(Book book) {
        return jdbctemplate.update("insert into Books(title,author,rating,price) values(?, ?, ?, ?)",book.getTitle(),book.getAuthor(),book.getRating(),book.getPrice());
    }

    @Override
    public int update(Book book) {
        // This prepared statement returns problems when it has multiple things to change!
        return jdbctemplate.update("update Books set title = ? where id = ?", book.getTitle(),book.getId());
    }

    @Override
    public List<Book> getSelection() {
        return jdbctemplate.query("select * from Books order by rand() limit 1", (res, nRows) -> new Book(res.getString("title"), res.getString("author"),res.getInt("rating"),res.getDouble("price")));
    }

    @Override
    public List<Book> findAll() {
        // For more info on the result set methods, please look for the official docuemntation
        return jdbctemplate.query("select * from Books", (res, rowNum) -> new Book(res.getLong("id"),res.getString("title"),res.getString("author"),res.getInt("rating"),res.getDouble("price")));
    }

    @Override
    public int remove(Book book) {
        return jdbctemplate.update("delete from Books where title = ?", book.getTitle());
    }

}
// This contains all of the CRUD operations (The most BASIC ones), this is the power of the Spring Data layer abstraction!
// Wait untill you see all of the Big Data implications