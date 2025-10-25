package com.funtara.book_service.api.mapper;

import com.funtara.book_service.api.dto.BookResponse;
import com.funtara.book_service.api.dto.CreateBookRequest;
import com.funtara.book_service.api.dto.UpdateBookRequest;
import com.funtara.book_service.model.Book;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    Book toEntity(CreateBookRequest req);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    void updateEntity(@MappingTarget Book book, UpdateBookRequest req);

    BookResponse toResponse(Book book);

    List<BookResponse> toResponseList(List<Book> books);
}
