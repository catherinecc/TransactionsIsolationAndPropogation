package com.catherine.springTransactions.repository;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.catherine.springTransactions.entity.Product;


@Repository
public class ProductRepository {
	
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	
	@Transactional
	public void addProductWithUncheckedException(Product p) {
		
		String sql =  "insert into db_example.products (Pname, category, price) values (?,?,?)";
		jdbcTemplate.update(sql, p.getName(),null,null);
throw new RuntimeException();
	}
	
	
	
	@Transactional
	public void addProductWithCheckedException(Product p) throws Exception {
	String sql =   "insert into db_example.products (Pname, category, price) values (?,?,?)";
		jdbcTemplate.update(sql,p.getName());
throw new Exception();
	}
	
	
	
	@Transactional(isolation = Isolation.SERIALIZABLE)
	public void addProductConcurrently() throws Exception {
	    try (Connection connection = jdbcTemplate.getDataSource().getConnection()) {
	        boolean autoCommit = connection.getAutoCommit();
	        System.out.println("Auto-Commit: " + autoCommit);

	        String count = "SELECT COUNT(*) FROM db_example.products";

	        System.out.println("Before: " + jdbcTemplate.queryForObject(count, Long.class));

	        String sql = "INSERT INTO db_example.products (Pname, category, price) VALUES (?, ?, ?)";

	        List<CompletableFuture<Void>> futures = new ArrayList<>();

	        for (int i = 1; i <= 5; i++) {
	            int finalI = i;
	            CompletableFuture<Void> future = CompletableFuture.runAsync(() ->
	                    jdbcTemplate.update(sql, Thread.currentThread().getName(), "concurrentThread", null));
	            futures.add(future);
	        }

	        // Wait for all CompletableFuture instances to complete
	        CompletableFuture<Void> allOf = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
	        allOf.join(); // Wait for completion

	        // Query the count within the same transaction
	        System.out.println("After: " + jdbcTemplate.queryForObject(count, Long.class));
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
}


