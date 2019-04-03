package com.electricalequipment.eservice.service.impl;

import com.electricalequipment.eservice.domain.Description;
import com.electricalequipment.eservice.domain.Item;
import com.electricalequipment.eservice.repository.DescriptionRepository;
import com.electricalequipment.eservice.repository.ItemRepository;
import com.electricalequipment.eservice.service.ItemService;
import com.electricalequipment.eservice.service.dto.ItemDTO;
import com.electricalequipment.eservice.service.mapper.ItemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

/**
 * Service Implementation for managing Item.
 */
@Service
@Transactional
public class ItemServiceImpl implements ItemService {

    private final Logger log = LoggerFactory.getLogger(ItemServiceImpl.class);

    private final ItemRepository itemRepository;
    private final DescriptionRepository descriptionRepository;

    private final ItemMapper itemMapper;

    public ItemServiceImpl(ItemRepository itemRepository, DescriptionRepository descriptionRepository, ItemMapper itemMapper) {
        this.itemRepository = itemRepository;
        this.descriptionRepository = descriptionRepository;
        this.itemMapper = itemMapper;
    }

    /**
     * Save a item.
     *
     * @param itemDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ItemDTO save(ItemDTO itemDTO) {
        log.debug("Request to save Item : {}", itemDTO);
        String[] params = itemDTO.getParams().split(",");
        Item item = itemMapper.toEntity(itemDTO);
        Set<Description> descriptions = new HashSet<>();
        for (String param : params) {
            descriptions.add(descriptionRepository.save(createDescription(param, item)));
        }
        item.setDescriptions(descriptions);
        item = itemRepository.save(item);
        return itemMapper.toDto(item);
    }

    private Description createDescription(String param, Item item) {
        Description description = new Description();
        description.setItem(item);
        description.setParameter(param);
        return description;
    }

    /**
     * Get all the items.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ItemDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Items");
        return itemRepository.findAll(pageable)
            .map(itemMapper::toDto);
    }


    /**
     * Get one item by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ItemDTO> findOne(Long id) {
        log.debug("Request to get Item : {}", id);
        return itemRepository.findById(id)
            .map(itemMapper::toDto);
    }

    /**
     * Delete the item by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Item : {}", id);
        itemRepository.deleteById(id);
    }
}
