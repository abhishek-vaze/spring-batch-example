package com.invicto.batch.batch_example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.HashMap;

@SpringBootApplication
public class BatchExampleApplication {

	public static void main(String[] args) {
		ApplicationContext context = SpringApplication.run(BatchExampleApplication.class, args);

		/* Trigger point to start the batch */

		JobLauncher launcher =  context.getBean(JobLauncher.class);
		Job job  = context.getBean(Job.class);
		HashMap<String, JobParameter> map = new HashMap<>();
		map.put("time",new JobParameter(System.currentTimeMillis()));
		JobParameters param = new JobParameters(map);
		try {
			launcher.run(job, param);
		}
		catch(Exception ex){
			System.out.println(ex.getMessage());
		}
	}
}
