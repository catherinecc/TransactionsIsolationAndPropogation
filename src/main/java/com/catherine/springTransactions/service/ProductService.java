package com.catherine.springTransactions.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.catherine.springTransactions.entity.Product;
import com.catherine.springTransactions.repository.ProductRepository;

@Service
public class ProductService {
	
	@Autowired
	ProductRepository repo;
	
	
	public boolean addProductWithUncheckedException(Product p) {
		repo.addProductWithUncheckedException(p);
		return true;
	}
	
	public boolean addProductWithCheckedException(Product p) {
try {
		//repo.addProductWithCheckedException(p);
}
	catch(Exception e) {
		e.printStackTrace();
	}
		return true;
	}
	
	
	public void addProductsConcurrently() {
		try {
			repo.addProductConcurrently();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}