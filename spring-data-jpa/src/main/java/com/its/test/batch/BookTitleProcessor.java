package com.its.test.batch;

import com.its.test.entity.BookEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;


@Slf4j
@Component
public class BookTitleProcessor implements ItemProcessor<BookEntity, BookEntity> {

    @Override
    public BookEntity process(BookEntity bookEntity) throws Exception {
        log.info("processing title for {}", bookEntity);
        bookEntity.setTitle(bookEntity.getTitle().toUpperCase());
        return bookEntity;
    }
}
