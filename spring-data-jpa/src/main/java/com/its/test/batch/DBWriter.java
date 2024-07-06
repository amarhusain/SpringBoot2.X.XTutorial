package com.its.test.batch;


import com.its.test.dao.BookRepository;
import com.its.test.dao.UserRepository;
import com.its.test.entity.BookEntity;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<BookEntity> {

    private final BookRepository bookRepository;

    public DBWriter(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void write(List<? extends BookEntity> books) throws Exception {
        bookRepository.saveAll(books);
    }
}
