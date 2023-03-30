package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.OrderDto;
import com.tms.oknapvh.entity.OrderEntity;
import com.tms.oknapvh.entity.WindowEntity;
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
    public OrderEntity createOrder(String type) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String dateAndTime = LocalDateTime.now().format(formatter);

        List<WindowEntity> windowsByType = windowRepository.findByType(type); // с типом и без заказа

        WindowEntity window = windowsByType.get(0); // первое свободное окно в списке

        UUID window_id = window.getId(); // id первого свободного окна в списке

        OrderDto orderDto = new OrderDto();

        orderDto.setUserId(UUID.randomUUID()); // ДОБАВИТЬ ID РЕАЛЬНОГО ЮЗЕРА

        orderDto.setWindow_id(window_id);
        orderDto.setPrice(window.getPrice());
        orderDto.setDateAndTime(dateAndTime);
        orderDto.setStatus("Обработка");

        var orderEntity = mapper.dtoToEntity(orderDto);
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
        repository.deleteById(orderId);
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
