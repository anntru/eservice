package com.electricalequipment.eservice.service.mapper;

import com.electricalequipment.eservice.domain.Description;
import com.electricalequipment.eservice.domain.Item;
import com.electricalequipment.eservice.service.dto.ItemDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.stream.Collectors;

/**
 * Mapper for the entity Item and its DTO ItemDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface ItemMapper extends EntityMapper<ItemDTO, Item> {


    @Mapping(target = "descriptions", ignore = true)
    Item toEntity(ItemDTO itemDTO);

    default Item fromId(Long id) {
        if (id == null) {
            return null;
        }
        Item item = new Item();
        item.setId(id);
        return item;
    }

    @Override
    @Mapping(target = "params", expression = "java(mapDesc(entity))")
    ItemDTO toDto(Item entity);

    default String mapDesc(Item entity) {
        return String.join(",", entity.getDescriptions().stream().map(Description::getParameter).collect(Collectors.toList()));
    }

}
