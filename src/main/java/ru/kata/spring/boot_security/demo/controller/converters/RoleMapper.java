package ru.kata.spring.boot_security.demo.controller.converters;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.kata.spring.boot_security.demo.model.dto.RoleDTO;
import ru.kata.spring.boot_security.demo.model.entity.Role;

@Mapper
public interface RoleMapper {
    RoleMapper INSTANCE = Mappers.getMapper(RoleMapper.class);

    Role roleDtoToRole(RoleDTO roleDTO);
}
