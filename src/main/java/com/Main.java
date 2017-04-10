package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main {
	public static void main(String[] args) {
		SpringApplication.run(Main.class, args);
		System.out.println("Hello world");
		
		print();
	}
	
	static strictfp public void print() {
		System.out.println(22/7.0);
	}
}
