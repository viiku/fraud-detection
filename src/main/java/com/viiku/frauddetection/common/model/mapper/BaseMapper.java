package com.viiku.frauddetection.common.model.mapper;

/**
 * Mapping between DTOs and Entities
 * @param <D> Dto class
 * @param <E> Entity class
 */
public interface BaseMapper<D, E> {

    E mapToEntity(D dto);

    D mapToDto(E entity);
}
