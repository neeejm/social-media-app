package com.neeejm.posts.models;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Post {

  @Id
  private String id;

  private String title;

  private String content;

  @Builder.Default
  private Integer views = 0;

  @Version
  private Integer version;

  @CreatedDate
  @Builder.Default
  private LocalDateTime createdAt = LocalDateTime.now();

  @LastModifiedDate
  @Builder.Default
  private LocalDateTime modifiedAt = LocalDateTime.now();
}
