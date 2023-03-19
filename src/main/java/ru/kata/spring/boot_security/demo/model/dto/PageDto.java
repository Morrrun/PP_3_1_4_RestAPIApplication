package ru.kata.spring.boot_security.demo.model.dto;

import java.util.List;

public record PageDto<T>(int currentPageNumber, int totalPageCount,
                         int totalResultCount, List<T> items, int itemsOnPage) {
}
