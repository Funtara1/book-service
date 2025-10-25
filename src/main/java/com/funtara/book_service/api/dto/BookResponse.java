package com.funtara.book_service.api.dto;

public record BookResponse(
        Long id,
        String bookName,
        String authorName
) {}
