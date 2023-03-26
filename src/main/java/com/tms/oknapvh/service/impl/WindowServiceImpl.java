package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.WindowEntity;
import com.tms.oknapvh.entity.WindowEntity_;
import com.tms.oknapvh.mapper.MyMapper;
import com.tms.oknapvh.repository.WindowRepository;
import com.tms.oknapvh.service.WindowService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WindowServiceImpl implements WindowService {

    private final WindowRepository repository;

    private final MyMapper mapper;

    @Override
    public List<WindowDto> getAll() {
        return repository.findAll()
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    @Override
    public WindowDto saveWindow(WindowDto windowDto) {
        var windowEntity = mapper.dtoToEntity(windowDto);
        repository.save(windowEntity);
        return mapper.entityToDto(windowEntity);
    }

    @Override
    public WindowDto getById(Integer id) {
        return repository.findById(id)
                .map(mapper::entityToDto)
                .orElse(null);
    }

    @Override
    public void deleteWindow(Integer windowId) {
        repository.deleteById(windowId);
    }

    public List<WindowDto> getBySomething(WindowDto windowDto) {
        Specification<WindowEntity> specification = createSpecification(windowDto);
        return repository.findAll(specification)
                .stream()
                .map(mapper::entityToDto)
                .collect(Collectors.toList());
    }

    private Specification<WindowEntity> createSpecification(WindowDto windowDto) {
        var windowEntity = mapper.dtoToEntity(windowDto);
        return (root, query, builder) -> {

            List<Predicate> listCond = new ArrayList<>();

            if (StringUtils.isNotBlank(windowEntity.getModel())) {
                Predicate equalModel = builder.equal(root.get(WindowEntity_.MODEL), windowEntity.getModel());
                listCond.add(equalModel);
            }

            if (windowEntity.getWidth() != null) {
                Predicate equalWidth = builder.equal(root.get(WindowEntity_.WIDTH), windowEntity.getWidth());
                listCond.add(equalWidth);
            }

            if (windowEntity.getHeight() != null) {
                Predicate equalHeight = builder.equal(root.get(WindowEntity_.HEIGHT), windowEntity.getHeight());
                listCond.add(equalHeight);
            }

            if (windowEntity.getPrice() != null) {
                Predicate equalPrice = builder.equal(root.get(WindowEntity_.PRICE), windowEntity.getPrice());
                listCond.add(equalPrice);
            }

            if (StringUtils.isNotBlank(windowEntity.getManufacturer())) {
                Predicate equalManufacturer = builder.equal(root.get(WindowEntity_.MANUFACTURER), windowEntity.getManufacturer());
                listCond.add(equalManufacturer);
            }

                Predicate equalAvailability = builder.equal(root.get(WindowEntity_.AVAILABILITY), windowEntity.isAvailability());
                listCond.add(equalAvailability);

            return builder.and(listCond.toArray(new Predicate[]{}));
        };
    }

}
