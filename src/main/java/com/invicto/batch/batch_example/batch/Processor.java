package com.invicto.batch.batch_example.batch;


import com.invicto.batch.batch_example.Model.Employee;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class Processor implements ItemProcessor<Employee,Employee> {

    private static Map<String,String> DEPT_NAMES = new HashMap<String,String>();


    public Processor() {

        DEPT_NAMES.put("001","Technology");
        DEPT_NAMES.put("002","Networking");
        DEPT_NAMES.put("003","Analytics");
        DEPT_NAMES.put("004","Accounts");
        DEPT_NAMES.put("005","Facilities");
    }

    @Override
    public Employee process(Employee employee) throws Exception {

        String dept_code = employee.getDept();
        String dept = DEPT_NAMES.get(dept_code);
        employee.setDept(dept);
        return employee;
    }
}
