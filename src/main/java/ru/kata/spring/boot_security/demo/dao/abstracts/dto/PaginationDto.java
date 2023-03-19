package ru.kata.spring.boot_security.demo.dao.abstracts.dto;

import ru.kata.spring.boot_security.demo.model.dto.PageDto;

import java.util.List;

public interface PaginationDto<T, P> {
    List<T> getItems(P param);

    int getTotalResultCount(P param);

}
