package com.springboot.demo.bookstore.mapper;

import com.springboot.demo.bookstore.components.responses.AuthorResponse;
import com.springboot.demo.bookstore.entity.Author;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

/**
 * This class contain the mapper for the Author entity
 */
@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AuthorMapper {
    AuthorMapper Instance = Mappers.getMapper(AuthorMapper.class);
}
