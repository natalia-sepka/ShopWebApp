package com.example.demo.controller;

import com.example.demo.entity.OrderDTO;
import com.example.demo.entity.Response;
import com.example.demo.entity.notify.Notify;
import com.example.demo.exception.*;
import com.example.demo.mediator.OrderMediator;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderMediator orderMediator;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createOrder(@RequestBody OrderDTO order, HttpServletRequest request, HttpServletResponse response){
        return orderMediator.createOrder(order,request,response);
    }

    @RequestMapping(method = RequestMethod.POST,value = "/notification")
    public ResponseEntity<Response> notifyOrder(@RequestBody Notify notify, HttpServletRequest request){
        return orderMediator.handleNotify(notify,request);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> get(@RequestParam(required = false) String uuid,HttpServletRequest request){
        return orderMediator.getOrder(uuid,request);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyBasketException.class)
    public Response handleValidationExceptions(EmptyBasketException ex){
        return new Response("Basket is empty");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BasketDoesntExistException.class)
    public Response handleValidationExceptions(BasketDoesntExistException ex){
        return new Response("Basket doesn't exist for this session");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(PayUException.class)
    public Response handleValidationExceptions(PayUException ex){
        return new Response("Server error contact with administrator");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UnknownDeliverTypeException.class)
    public Response handleValidationExceptions(UnknownDeliverTypeException ex){
        return new Response("Deliver doesn't exist with this uuid");
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserDoesntLogInException.class)
    public Response handleValidationExceptions(UserDoesntLogInException ex){
        return new Response("User is not logged in");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrderDoesntExistException.class)
    public Response handleValidationExceptions(OrderDoesntExistException ex){
        return new Response("Order doesn't exist");
    }

}

