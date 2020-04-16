package com.skcc.mongodb.exam04.services;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.project;
import static org.springframework.data.mongodb.core.aggregation.Aggregation.sort;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.stereotype.Service;

import com.skcc.mongodb.exam04.entity.BookTitle;
import com.skcc.mongodb.exam04.entity.OrdersPerCustomer;
import com.skcc.mongodb.exam04.repository.OrderRepository;


@Service
public class AggregationService {
	@Autowired
	private OrderRepository repository;
	
	@Autowired 
	MongoOperations operations;
	
	public List<OrdersPerCustomer> totalOrdersPerCustomer(Sort sort) {
		return repository.totalOrdersPerCustomer(sort);
	}
	public Long totalOrdersForCustomer(String customerId) {
		return repository.totalOrdersForCustomer(customerId);
	}
	
	public List<BookTitle> shouldRetrieveOrderedBookTitles() {

		Aggregation aggregation = newAggregation( //
				sort(Direction.ASC, "volumeInfo.title"), //
				project().and("volumeInfo.title").as("title"));

		AggregationResults<BookTitle> result = operations.aggregate(aggregation, "books", BookTitle.class);
		return result.getMappedResults();
	}
}
