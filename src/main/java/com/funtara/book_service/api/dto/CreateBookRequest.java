package com.funtara.book_service.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateBookRequest(
        @NotBlank @Size(max = 200) String bookName,
        @NotBlank @Size(max = 120) String authorName
) {}
