package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.WindowEntity;
import com.tms.oknapvh.entity.WindowEntity_;
import com.tms.oknapvh.entity.WindowFilter;
import com.tms.oknapvh.exception.WindowNotFoundException;
import com.tms.oknapvh.mapper.WindowMapper;
import com.tms.oknapvh.repositories.WindowRepository;
import com.tms.oknapvh.service.WindowService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.JoinType;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class WindowServiceImpl implements WindowService {

    private final WindowRepository repository;

    private final WindowMapper mapper;

    @Override
    public WindowDto getById(UUID windowId) {
        return repository.findById(windowId)
                .map(mapper::entityToDto)
                .orElseThrow(() -> new WindowNotFoundException("Окно не найдено"));
    }

    @Override
    public List<WindowDto> getAllWithoutOrder() {
        var specification = createSpecificationWithoutOrder();
        return mapper.windowsEntityToDto(repository.findAll(specification));
    }

    public Specification<WindowEntity> createSpecificationWithoutOrder() {
        return (root, query, builder) -> {
            var predicate = builder.conjunction();
            var orderJoin = root.join("order", JoinType.LEFT);
            return builder.and(
                    predicate,
                    builder.isNull(orderJoin.get("id"))
            );
        };
    }

    @Override
    public void saveWindow(WindowDto windowDto) {
        var windowEntity = mapper.dtoToEntity(windowDto);
        repository.save(windowEntity);
    }

    @Override
    public void deleteWindow(UUID windowId) {
        repository.deleteById(windowId);
    }

    @Override
    public List<WindowDto> getByType(String windowType) {
        return mapper.windowsEntityToDto(repository.findByType(windowType));
    }

    @Override
    public List<WindowDto> getByWindowFilter(WindowFilter windowFilter) {
        var specification = createSpecificationForFilter(windowFilter);
        return mapper.windowsEntityToDto(repository.findAll(specification));
    }

    public Specification<WindowEntity> createSpecificationForFilter(WindowFilter windowFilter) {
        return (root, query, builder) -> {

            var predicate = builder.conjunction();

            var minPrice = windowFilter.getMinPrice() != null ? windowFilter.getMinPrice() : Integer.MIN_VALUE;
            var maxPrice = windowFilter.getMaxPrice() != null ? windowFilter.getMaxPrice() : Integer.MAX_VALUE;
            predicate = builder.and(predicate, builder.between(root.get(WindowEntity_.PRICE), minPrice, maxPrice));

            if (windowFilter.getWidth() != null) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.WIDTH), windowFilter.getWidth()));
            }

            if (windowFilter.getHeight() != null) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.HEIGHT), windowFilter.getHeight()));
            }

            if (StringUtils.isNotBlank(windowFilter.getType())) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.TYPE), windowFilter.getType()));
            }

            if (StringUtils.isNotBlank(windowFilter.getLamination())) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.LAMINATION), windowFilter.getLamination()));
            }

            if (windowFilter.getMountingWidth() != null) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.MOUNTING_WIDTH), windowFilter.getMountingWidth()));
            }

            if (windowFilter.getCameras() != null) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.CAMERAS), windowFilter.getCameras()));
            }

            if (StringUtils.isNotBlank(windowFilter.getManufacturer())) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.MANUFACTURER), windowFilter.getManufacturer()));
            }

            if (StringUtils.isNotBlank(windowFilter.getAvailability())) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.AVAILABILITY), windowFilter.getAvailability()));
            }

            var orderJoin = root.join("order", JoinType.LEFT);
            return builder.and(
                    predicate,
                    builder.isNull(orderJoin.get("id"))
            );
        };
    }
}
