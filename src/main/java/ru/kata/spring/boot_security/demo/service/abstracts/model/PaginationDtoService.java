package ru.kata.spring.boot_security.demo.service.abstracts.model;

import ru.kata.spring.boot_security.demo.dao.abstracts.dto.PaginationDto;
import ru.kata.spring.boot_security.demo.model.dto.PageDto;

public interface PaginationDtoService<T, P> {
    default PageDto<T> getPage(P param, int currentPageNumber, int totalPageCount, PaginationDto<T, P> pageDto) {
        return new PageDto<>(currentPageNumber,
                totalPageCount,
                pageDto.getTotalResultCount(param),
                pageDto.getItems(param),
                (int) Math.ceil((double) pageDto.getTotalResultCount(param) / totalPageCount));
    }
}
