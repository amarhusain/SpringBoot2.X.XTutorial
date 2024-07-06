package com.its.test.config;


import com.its.test.entity.BookEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

@Configuration
public class BatchConfig {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    public BatchConfig(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Bean
    public Job importBooksJob(ItemReader<BookEntity> itemReader,
                              ItemProcessor<BookEntity, BookEntity> itemProcessor,
                              ItemWriter<BookEntity> itemWriter) {

        Step step = this.stepBuilderFactory.get("ETL-file-load")
                .<BookEntity, BookEntity>chunk(10)
                .reader(itemReader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();

        return this.jobBuilderFactory.get("importBooksJob")
                .incrementer(new RunIdIncrementer())
                .start(step)
                .build();
    }



    @Bean
    public FlatFileItemReader<BookEntity> fileItemReader() {
        return new FlatFileItemReaderBuilder<BookEntity>()
                .name("bookReader")
                .resource(new ClassPathResource("book_data.csv"))
                .delimited()
                .names(new String[]{"title", "author", "year"})
                .fieldSetMapper(new BeanWrapperFieldSetMapper<BookEntity>() {{
                    setTargetType(BookEntity.class);
                }})
                .build();
    }

}
