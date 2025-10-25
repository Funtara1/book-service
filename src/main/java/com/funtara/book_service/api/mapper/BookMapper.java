package com.funtara.book_service.api.mapper;

import com.funtara.book_service.api.dto.*;
import com.funtara.book_service.model.Book;
import org.mapstruct.*;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    Book toEntity(CreateBookRequest req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Book book, UpdateBookRequest req);

    BookResponse toResponse(Book book);

    List<BookResponse> toResponseList(List<Book> books);
}
