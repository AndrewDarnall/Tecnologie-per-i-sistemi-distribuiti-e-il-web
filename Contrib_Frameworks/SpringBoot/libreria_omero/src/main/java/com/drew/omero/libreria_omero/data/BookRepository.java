package com.drew.omero.libreria_omero.data;

import com.drew.omero.libreria_omero.models.Bbooks;
import org.springframework.data.jpa.repository.JpaRepository;
// To build JPQL queries -> Making it MUCH faster than JDBC
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface BookRepository extends JpaRepository<Bbooks, Long>{
    
    // This is all that it takes to query, almost like magic
    List<Bbooks> findByTitle(String title);
    List<Bbooks> findAll();

    @Query(value = "select * from Bbooks order by rand() limit 1", nativeQuery = true)
    List<Bbooks> getSuggestion();

    @Query(value = "select count(*) from Bbooks", nativeQuery = true)
    long count(); // mind the return type!

    @Query(value = "insert into Bbooks(title,author,rating,price) values (?1, ?2, ?3, ?4)", nativeQuery = true)
    void save(String title, String author, int rating, double price);

    // Senza il transactional si hanno problemi, occhio al contesto per la query modificante !
    @Transactional
    @Modifying(clearAutomatically=true, flushAutomatically=true)
    @Query(value = "update Bbooks set title = ?2 where id = ?1", nativeQuery = true)
    void update(long id, String title, String author, int rating, double price);

    @Transactional
    @Modifying(clearAutomatically=true, flushAutomatically=true)
    @Query(value = "delete from Bbooks where id = ?1", nativeQuery = true)
    void remove(long id);

    @Query(value = "select * from Bbooks where title = ?1", nativeQuery = true)
    List<Bbooks> search(String title);

}