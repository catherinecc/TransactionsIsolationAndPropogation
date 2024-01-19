package com.catherine.springTransactions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import com.catherine.springTransactions.entity.Product;
import com.catherine.springTransactions.repository.ProductRepository;
import com.catherine.springTransactions.service.ProductService;

@SpringBootApplication
public class TransactionsDemoApplication {

	
	
	public static void main(String[] args)  {
		ConfigurableApplicationContext run = SpringApplication.run(TransactionsDemoApplication.class, args);
	
		ProductService service = run.getBean(ProductService.class);
		
//		 Product p = new Product("haircareForCE");
//		 service.addProductWithCheckedException(p);
//		 p = new Product("haircareForUCE");
//		service.addProductWithUncheckedException(p);
		
		service.addProductsConcurrently();
		
	}

}
