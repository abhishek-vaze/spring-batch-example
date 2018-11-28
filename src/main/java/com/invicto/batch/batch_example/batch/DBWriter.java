package com.invicto.batch.batch_example.batch;

import com.invicto.batch.batch_example.Model.Employee;
import com.invicto.batch.batch_example.repository.EmployeeRepository;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DBWriter implements ItemWriter<Employee> {

    @Autowired
    EmployeeRepository repository;

    @Override
    public void write(List<? extends Employee> employees) throws Exception {
        System.out.println("Saved users "+employees);
        repository.saveAll(employees);
    }
}
