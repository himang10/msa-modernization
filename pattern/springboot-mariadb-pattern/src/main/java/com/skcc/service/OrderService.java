package com.skcc.service;

import com.google.gson.Gson;
import com.skcc.data.entity.Order;
import com.skcc.data.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public List<Order> findOrderByAccountId(long accountId) {
        return this.orderRepository.findByAccountId(accountId);
    }

    public Order createOrder(String bodyParam) {
        Gson gson = new Gson();
        try {
            Order param = gson.fromJson(bodyParam, Order.class);
            return this.orderRepository.save(param);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateAccountInfo(long accountId, String accountInfo) {
        Optional<Order>  info = this.orderRepository.findById(accountId);
        info.ifPresent(data -> {
            data.setAccountInfo(accountInfo);
        });
    }

    public void deleteOrder(long accountId) {
        Optional<Order>  info = this.orderRepository.findById(accountId);
        info.ifPresent(data -> {
            this.orderRepository.deleteById(accountId);
        });
    }
}

