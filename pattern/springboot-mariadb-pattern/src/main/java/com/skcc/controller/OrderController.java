package com.skcc.controller;

import com.skcc.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Callable;

@RestController
@RequestMapping(value = "/v1", produces = {MediaType.APPLICATION_JSON_VALUE})
public class OrderController {


    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }


    // select
    @RequestMapping(value = "/orders/{accountId}", method = RequestMethod.GET)
    public Callable<Object> findOrderByAccountId(@PathVariable long accountId) {
        return () -> {
            return orderService.findOrderByAccountId(accountId);
    };
    }

    // create
    @RequestMapping(value = "/orders", method = RequestMethod.POST)
    public Callable<Object> createOrder(@RequestBody String bodyParam) {
        return () -> {
            return orderService.createOrder(bodyParam);
        };
    }

    // update
    @RequestMapping(value = "/orders/{accountId}/{accountInfo}", method = RequestMethod.PUT)
    public Callable<String> updateOrder(@PathVariable long accountId, @PathVariable String accountInfo) {
        return () -> {
            orderService.updateAccountInfo(accountId, accountInfo);
            return "Update successfully..";
        };
    }

    // update
    @RequestMapping(value = "/orders/{accountId}", method = RequestMethod.DELETE)
    public Callable<String> deleteOrder(@PathVariable long accountId) {
        return () -> {
            orderService.deleteOrder(accountId);
            return "Delete successfully..";
        };
    }

}
