package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.OrderDto;
import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.OrderEntity;
import com.tms.oknapvh.entity.OrderStatus;
import com.tms.oknapvh.mapper.OrderMapper;
import com.tms.oknapvh.repositories.OrderRepository;
import com.tms.oknapvh.repositories.UserRepository;
import com.tms.oknapvh.repositories.WindowRepository;
import com.tms.oknapvh.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    private final WindowRepository windowRepository;

    private final OrderMapper orderMapper;

    private final UserRepository userRepository;

    @Override
    @Transactional
    public List<OrderDto> getAll() {
        return orderRepository.findAll()
                .stream()
                .map(orderMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderEntity createOrder(WindowDto window) {

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        var dateAndTime = LocalDateTime.now().format(formatter);

        var orderEntity = new OrderEntity();

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        var username = authentication.getName();

        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        orderEntity.setUser(user);

        orderEntity.setPrice(window.getPrice());
        orderEntity.setDateAndTime(dateAndTime);

        var windowEntity = windowRepository.findById(window.getId()).orElseThrow(RuntimeException::new);
        orderEntity.setWindow(windowEntity);

        return orderRepository.save(orderEntity);
    }

    @Override
    public OrderEntity getById(UUID orderId) {
        return orderRepository.findById(orderId).orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        var order = orderRepository.findById(orderId).orElseThrow(RuntimeException::new);
        var status = order.getStatus();
        var window = order.getWindow();
        switch (status) {
            case COMPLETED -> {
                orderRepository.delete(order);
                windowRepository.delete(window);
            }
            case CANCELLED -> orderRepository.delete(order);
            case NEW, ACCEPTED -> throw new RuntimeException();

        }
    }

    public OrderEntity updateStatusById(UUID id, OrderStatus status) {
        var order = orderRepository.findById(id).orElseThrow(RuntimeException::new);
        order.setStatus(status);
        return orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getByUserId(UUID id) {
        return orderRepository.findAllByUserId(id)
                .stream()
                .map(orderMapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public void cancellationOrder(UUID id) {
        var order = orderRepository.findById(id).orElseThrow(RuntimeException::new);
        order.setStatus(OrderStatus.CANCELLED);
        orderRepository.save(order);
    }


}
