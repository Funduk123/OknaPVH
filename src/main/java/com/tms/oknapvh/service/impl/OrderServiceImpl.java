package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.OrderDto;
import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.OrderEntity;
import com.tms.oknapvh.entity.OrderStatus;
import com.tms.oknapvh.exception.InvalidOrderStatusException;
import com.tms.oknapvh.exception.OrderNotFoundException;
import com.tms.oknapvh.exception.WindowNotFoundException;
import com.tms.oknapvh.mapper.OrderMapper;
import com.tms.oknapvh.repositories.OrderRepository;
import com.tms.oknapvh.repositories.UserRepository;
import com.tms.oknapvh.repositories.WindowRepository;
import com.tms.oknapvh.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
        return orderMapper.ordersEntityToDto(orderRepository.findAll());
    }

    @Override
    @Transactional
    public void createOrder(WindowDto windowDto) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var username = authentication.getName();
        var user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Пользователь не найден"));

        var windowEntity = windowRepository.findById(window.getId())
                .orElseThrow(RuntimeException::new);

        var orderEntity = OrderEntity.builder()
                .user(user)
                .dateAndTime(LocalDateTime.now())
                .price(windowDto.getPrice())
                .status(OrderStatus.NEW.name())
                .window(windowEntity)
                .build();

        orderRepository.save(orderEntity);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        var order = orderRepository.findById(orderId).orElseThrow(RuntimeException::new);
        var status = order.getStatus();
        var window = order.getWindow();
        switch (status) {
            case "COMPLETED" -> {
                orderRepository.delete(orderEntity);
                windowRepository.delete(window);
            }
            case "CANCELLED" -> orderRepository.delete(order);
            case "NEW", "ACCEPTED" -> throw new RuntimeException();
        }
    }

    @Override
    public void updateStatusById(UUID id, String status) {
        var order = orderRepository.findById(id).orElseThrow(RuntimeException::new);
        order.setStatus(status);
        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getByUserId(UUID userId) {
        return orderMapper.ordersEntityToDto(orderRepository.findAllByUserId(userId));
    }

    @Override
    @Transactional
    public void cancellationOrder(UUID id) {
        var order = orderRepository.findById(id).orElseThrow(RuntimeException::new);
        order.setStatus(OrderStatus.CANCELLED.name());
    }

}