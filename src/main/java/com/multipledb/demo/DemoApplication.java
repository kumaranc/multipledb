package com.multipledb.demo;

import com.multipledb.demo.dbtest1.Data;
import com.multipledb.demo.dbtest1.dbTestService;
import com.multipledb.demo.dbtest2.DBService;
import com.multipledb.demo.dbtest2.DataTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}


	@Autowired
	DBService testService;

	@Override
	public void run(String... args) throws Exception {
		DataTest data =
				testService.createData("kumaran", "IT", "chennai", "11-01-1993");
		System.out.println(data.toString());
	}
}
