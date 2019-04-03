package com.electricalequipment.eservice.service.mapper;

import com.electricalequipment.eservice.domain.*;
import com.electricalequipment.eservice.service.dto.DescriptionDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Description and its DTO DescriptionDTO.
 */
@Mapper(componentModel = "spring", uses = {ItemMapper.class})
public interface DescriptionMapper extends EntityMapper<DescriptionDTO, Description> {

    @Mapping(source = "item.id", target = "itemId")
    DescriptionDTO toDto(Description description);

    @Mapping(source = "itemId", target = "item")
    Description toEntity(DescriptionDTO descriptionDTO);

    default Description fromId(Long id) {
        if (id == null) {
            return null;
        }
        Description description = new Description();
        description.setId(id);
        return description;
    }
}
