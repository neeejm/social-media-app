package com.neeejm.posts.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Document
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
public class Post extends BaseEntity {

    // @Id
    // private String id;

    private String title;

    private String content;

    private Integer views;
}
