package com.tms.oknapvh.web;

import com.tms.oknapvh.model.WindowEntity;
import com.tms.oknapvh.service.WindowService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/window")
@RequiredArgsConstructor
public class WindowResource {

    private final WindowService service;

    @GetMapping
    public List<WindowEntity> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public WindowEntity getById(@PathVariable(name = "id") Integer id) {
        return service.getById(id);
    }

    @PostMapping
    public WindowEntity save(@RequestBody WindowEntity window) {
        return service.save(window);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable(name = "id") Integer id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public WindowEntity update(
            @PathVariable(name = "id") String id,
            @RequestBody WindowEntity window) {
        window.setId(Integer.parseInt(id));
        return service.update(window);
    }

}
