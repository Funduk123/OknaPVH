package com.tms.oknapvh.service.impl;

import com.tms.oknapvh.dto.WindowDto;
import com.tms.oknapvh.entity.OrderEntity;
import com.tms.oknapvh.entity.WindowEntity;
import com.tms.oknapvh.entity.WindowEntity_;
import com.tms.oknapvh.exception.WindowNotFoundException;
import com.tms.oknapvh.mapper.WindowMapper;
import com.tms.oknapvh.repositories.WindowRepository;
import com.tms.oknapvh.service.WindowService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.Join;
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
    public List<WindowDto> getMatches(WindowDto windowDto) {
        var specification = createSpecification(windowDto);
        return mapper.windowsEntityToDto(repository.findAll(specification));
    }

    public Specification<WindowEntity> createSpecification(WindowDto windowDto) {
        var windowEntity = mapper.dtoToEntity(windowDto);
        return (root, query, builder) -> {

            var predicate = builder.conjunction();

            if (windowEntity.getWidth() != null) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.WIDTH), windowEntity.getWidth()));
            }

            if (windowEntity.getHeight() != null) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.HEIGHT), windowEntity.getHeight()));
            }

            if (StringUtils.isNotBlank(windowEntity.getType())) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.TYPE), windowEntity.getType()));
            }

            if (StringUtils.isNotBlank(windowEntity.getLamination())) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.LAMINATION), windowEntity.getLamination()));
            }

            if (windowEntity.getMountingWidth() != null) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.MOUNTING_WIDTH), windowEntity.getMountingWidth()));
            }

            if (windowEntity.getCameras() != null) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.CAMERAS), windowEntity.getCameras()));
            }

            if (windowEntity.getPrice() != null) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.PRICE), windowEntity.getPrice()));
            }

            if (StringUtils.isNotBlank(windowEntity.getManufacturer())) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.MANUFACTURER), windowEntity.getManufacturer()));
            }

            if (StringUtils.isNotBlank(windowEntity.getAvailability())) {
                predicate = builder.and(predicate, builder.equal(root.get(WindowEntity_.AVAILABILITY), windowEntity.getAvailability()));
            }

            Join<WindowEntity, OrderEntity> orderJoin = root.join("order", JoinType.LEFT);
            return builder.and(
                    predicate,
                    builder.isNull(orderJoin.get("id"))
            );
        };
    }
}
