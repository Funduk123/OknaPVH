package com.tms.oknapvh.web;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.OrderEntity;
import com.tms.oknapvh.entity.OrderStatus;
import com.tms.oknapvh.entity.UserEntity;
import com.tms.oknapvh.entity.WindowEntity;
import com.tms.oknapvh.exception.InvalidOrderStatusException;
import com.tms.oknapvh.exception.OrderNotFoundException;
import com.tms.oknapvh.exception.WindowNotFoundException;
import com.tms.oknapvh.repositories.OrderRepository;
import com.tms.oknapvh.repositories.UserRepository;
import com.tms.oknapvh.repositories.WindowRepository;
import com.tms.oknapvh.service.OrderService;
import com.tms.oknapvh.service.WindowService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private WindowRepository windowRepository;

    @Autowired
    private WindowService windowService;

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    public void testGetAllOrders() throws Exception {
        var orders = orderService.getAll();
        mockMvc.perform(get("/store/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("orders.html"))
                .andExpect(model().attribute("allOrders", orders));
    }

    @Test
    @Transactional
    public void testGetUserOrders() throws Exception {

        // Создаем тестового пользователя и сохраняем его в базе данных
        var user = new UserEntity();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setAuth("ROLE_USER");
        userRepository.save(user);

        // Создаем два окна и сохраняем их в базе данных
        var window1 = new WindowEntity();
        var window2 = new WindowEntity();
        windowRepository.save(window1);
        windowRepository.save(window2);

        // Создаем два заказа для тестирования и сохраняем их в базе данных
        var order1 = new OrderEntity();
        order1.setUser(user);
        order1.setWindow(window1);
        orderRepository.save(order1);

        var order2 = new OrderEntity();
        order2.setUser(user);
        order2.setWindow(window2);
        orderRepository.save(order2);

        var orders = orderService.getByUserId(user.getId());

        // Выполняем GET-запрос к /store/my-orders с аутентификацией тестового пользователя и проверяем результаты
        mockMvc.perform(get("/store/my-orders").with(user(user)))
                .andExpect(status().isOk())
                .andExpect(view().name("orders.html"))
                .andExpect(model().attribute("userOrders", hasSize(2)))
                .andExpect(model().attribute("userOrders", equalTo(orders)));

    }

    @Test
    @Transactional
    public void testCreateOrder_Success() throws Exception {

        var user = new UserEntity();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setAuth("ROLE_USER");
        userRepository.save(user);

        var windowEntity = new WindowEntity();
        windowRepository.save(windowEntity);

        var window = windowService.getById(windowEntity.getId());

        var sizeBefore = orderService.getByUserId(user.getId()).size();

        mockMvc.perform(post("/store/orders").with(user(user)).flashAttr("windowForOrder", window))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/store"));

        var sizeAfter = orderService.getByUserId(user.getId()).size();

        assertEquals(sizeBefore + 1, sizeAfter);

    }

    @Test
    @WithMockUser(username = "test")
    public void testCreateOrder_ThrowUsernameNotFoundException() throws Exception {
        mockMvc.perform(post("/store/orders"))
                .andExpect(status().isOk())
                .andExpect(view().name("error.html"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof UsernameNotFoundException))
                .andExpect(result -> assertEquals("Пользователь не найден", result.getResolvedException().getMessage()));
    }

    @Test
    @Transactional
    public void testCreateOrder_ThrowWindowNotFoundException() throws Exception {

        var user = new UserEntity();
        user.setUsername("testUsername");
        user.setPassword("testPassword");
        user.setAuth("ROLE_USER");
        userRepository.save(user);

        var window = new WindowDto();
        window.setId(UUID.randomUUID());

        mockMvc.perform(post("/store/orders").with(user(user)).flashAttr("windowForOrder", window))
                .andExpect(status().isOk())
                .andExpect(view().name("error.html"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof WindowNotFoundException))
                .andExpect(result -> assertEquals("Окно не найдено", result.getResolvedException().getMessage()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    @Transactional
    public void testUpdateStatus_Success() throws Exception {
        var order = new OrderEntity();
        order.setStatus(OrderStatus.NEW);

        orderRepository.save(order);

        mockMvc.perform(post("/store/orders/status/{id}", order.getId()).param("status", "COMPLETED"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/store/orders"));

        var status = order.getStatus();
        assertEquals(OrderStatus.COMPLETED, status);

    }

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    public void testUpdateStatus_ThrowOrderNotFoundException() throws Exception {
        mockMvc.perform(post("/store/orders/status/{id}", UUID.randomUUID()).param("status", "COMPLETED"))
                .andExpect(status().isOk())
                .andExpect(view().name("error.html"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof OrderNotFoundException))
                .andExpect(result -> assertEquals("Заказ не найден", result.getResolvedException().getMessage()));
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    @Transactional
    public void testDeleteOrder_IfCompleted() throws Exception {

        var window = new WindowEntity();
        windowRepository.save(window);

        var order = new OrderEntity();
        order.setStatus(OrderStatus.COMPLETED);
        order.setWindow(window);

        orderRepository.save(order);

        mockMvc.perform(post("/store/orders/delete/{id}", order.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/store/orders"));

        assertThat(windowRepository.findById(window.getId())).isEmpty();
        assertThat(orderRepository.findById(order.getId())).isEmpty();
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    @Transactional
    public void testDeleteOrder_IfCancelled() throws Exception {

        var window = new WindowEntity();
        windowRepository.save(window);

        var order = new OrderEntity();
        order.setStatus(OrderStatus.CANCELLED);
        order.setWindow(window);

        orderRepository.save(order);

        mockMvc.perform(post("/store/orders/delete/{id}", order.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/store/orders"));

        assertThat(windowRepository.findById(window.getId())).isPresent();
        assertThat(orderRepository.findById(order.getId())).isEmpty();
    }

    @Test
    @WithMockUser(roles = {"ADMIN", "SUPER_ADMIN"})
    @Transactional
    public void testDeleteOrder_ThrowInvalidOrderStatusException() throws Exception {

        var testStatus = OrderStatus.NEW;

        var window = new WindowEntity();
        windowRepository.save(window);

        var order = new OrderEntity();
        order.setStatus(testStatus);
        order.setWindow(window);

        orderRepository.save(order);

        mockMvc.perform(post("/store/orders/delete/{id}", order.getId()))
                .andExpect(status().isOk())
                .andExpect(view().name("error.html"))
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidOrderStatusException))
                .andExpect(result -> assertEquals("Нельзя удалить заказ со статусом: " + testStatus, result.getResolvedException().getMessage()));
    }

    @Test
    @WithMockUser(roles = {"USER", "ADMIN", "SUPER_ADMIN"})
    @Transactional
    public void testCancellationOrder_Success() throws Exception {
        var order = new OrderEntity();
        order.setStatus(OrderStatus.CANCELLED);

        orderRepository.save(order);

        mockMvc.perform(post("/store/orders/cancellation/{id}", order.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/store/my-orders"));
        assertEquals(OrderStatus.CANCELLED, order.getStatus());
    }
}