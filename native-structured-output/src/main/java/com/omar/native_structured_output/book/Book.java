package com.omar.native_structured_output.book;

import java.util.List;

public record Book(
        String title,
        String author,
        String publisher,
        int yearPublished,
        List<String> topics
) {}