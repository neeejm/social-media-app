package com.neeejm.posts.dtos;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostRequestDto {

    private String title;
    private String content;
}
