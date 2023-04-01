package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.OrderDto;
import com.tms.oknapvh.entity.OrderEntity;
import com.tms.oknapvh.mapper.OrderMapper;
import com.tms.oknapvh.repositories.OrderRepository;
import com.tms.oknapvh.repositories.WindowRepository;
import com.tms.oknapvh.service.OrderService;
import lombok.RequiredArgsConstructor;
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

    private final OrderRepository repository;

    private final WindowRepository windowRepository;

    private final OrderMapper mapper;


    @Override
    @Transactional
    public List<OrderDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public OrderEntity createOrder(UUID id) {

        var formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        var dateAndTime = LocalDateTime.now().format(formatter);

        var window = windowRepository.findById(id).orElseThrow(RuntimeException::new);

        var orderEntity = new OrderEntity();

        orderEntity.setUserId(UUID.randomUUID()); // ДОБАВИТЬ ID РЕАЛЬНОГО ЮЗЕРА

        orderEntity.setPrice(window.getPrice());
        orderEntity.setDateAndTime(dateAndTime);
        orderEntity.setWindow_id(window);

        return repository.save(orderEntity);
    }

    @Override
    public OrderDto getById(UUID orderId) {
        return repository.findById(orderId)
                .map(mapper::entityToDto)
                .orElseThrow(RuntimeException::new);
    }

    @Override
    public void deleteOrder(UUID orderId) {
        var windowId = repository.findById(orderId)
                .orElseThrow(RuntimeException::new)
                .getWindow_id().getId();

        repository.deleteById(orderId);
        windowRepository.deleteById(windowId);
    }

    public OrderEntity updateStatusById(UUID id, String status) {
        OrderEntity order = repository.findById(id).orElseThrow(RuntimeException::new);
        order.setStatus(status);
        return repository.save(order);
    }

    @Override
    public List<OrderDto> getByUserId(UUID id) {
        return repository.findAllByUserId(id)
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<OrderDto> getByWindowId(UUID id) {
        return repository.findAllByUserId(id)
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }
}
