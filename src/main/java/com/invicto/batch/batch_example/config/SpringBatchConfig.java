package com.invicto.batch.batch_example.config;


import com.invicto.batch.batch_example.Model.Employee;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@EnableBatchProcessing
@Configuration
public class SpringBatchConfig {


    @Bean
    public Job job(JobBuilderFactory factory, StepBuilderFactory stepFactory, ItemReader<Employee> reader, ItemProcessor<Employee,Employee> etlprocessor, ItemWriter<Employee> writer){

        Step step = stepFactory.get("ETL-file-load").<Employee,Employee>chunk(100).reader(reader).processor(etlprocessor).writer(writer).build();
        factory.get("ETL_Load").incrementer(new RunIdIncrementer()).start(step).build();

        return  factory.get("Load").incrementer(new RunIdIncrementer()).start(step).build();
    }

    @Bean
    public LineMapper<Employee> lineMapper(){

        DefaultLineMapper<Employee> mapper = new DefaultLineMapper<>();
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter(",");
        tokenizer.setStrict(false);
        tokenizer.setNames(new String[]{"id","name","dept","salary"});
        BeanWrapperFieldSetMapper<Employee> fieldsMapper = new BeanWrapperFieldSetMapper<>();
        fieldsMapper.setTargetType(Employee.class);
        mapper.setLineTokenizer(tokenizer);
        mapper.setFieldSetMapper(fieldsMapper);
        return mapper;

    }

    @Bean
    public FlatFileItemReader<Employee> fileItemReader(@Value("${input}") Resource resource, LineMapper<Employee> mapper){

        FlatFileItemReader<Employee> reader = new FlatFileItemReader<>();
        reader.setResource(resource);
        reader.setName("csvReader");
        reader.setLinesToSkip(1);
        reader.setLineMapper(mapper);
        return reader;

    }



}
