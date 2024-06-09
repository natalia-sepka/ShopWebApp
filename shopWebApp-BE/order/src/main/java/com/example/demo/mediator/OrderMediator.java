package com.example.demo.mediator;

import com.example.demo.entity.Order;
import com.example.demo.entity.OrderDTO;
import com.example.demo.entity.Response;
import com.example.demo.exception.BadSignatureException;
import com.example.demo.exception.OrderDoesntExistException;
import com.example.demo.service.OrderService;
import com.example.demo.service.SignatureValidator;
import com.example.demo.translators.OrderDTOToOrder;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.security.NoSuchAlgorithmException;

@Component
@RequiredArgsConstructor
public class OrderMediator {

    private final OrderDTOToOrder orderDTOToOrder;
    private final OrderService orderService;
    private final SignatureValidator signatureValidator;

    public ResponseEntity<?> createOrder(OrderDTO orderDTO, HttpServletRequest request, HttpServletResponse response) {
        Order order = orderDTOToOrder.toOrder(orderDTO);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add(HttpHeaders.CONTENT_TYPE, "application/json");
        return ResponseEntity.status(200).headers(httpHeaders).body(orderService.createOrder(order, request, response));
    }

    public ResponseEntity<Response> handleNotify(com.example.demo.entity.notify.Notify notify, HttpServletRequest request) {
        String header = request.getHeader("OpenPayu-Signature");
        try {
            signatureValidator.validate(header,notify);
            orderService.completeOrder(notify);
        } catch (NoSuchAlgorithmException | JsonProcessingException | BadSignatureException e) {
            return ResponseEntity.badRequest().body(new Response("Bad signature"));
        }catch (OrderDoesntExistException e1){
            return ResponseEntity.badRequest().body(new Response("Order doesn't exist"));
        };
        return ResponseEntity.ok(new Response("Notification handle success"));
    }

}

