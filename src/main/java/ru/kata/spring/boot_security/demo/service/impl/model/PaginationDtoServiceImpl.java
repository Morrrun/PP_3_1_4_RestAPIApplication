package ru.kata.spring.boot_security.demo.service.impl.model;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.service.abstracts.model.PaginationDtoService;

@Service
public class PaginationDtoServiceImpl<T, P> implements PaginationDtoService<T, P> {
}
