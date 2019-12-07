package ru.rosbank.javaschool.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.rosbank.javaschool.dto.PostSaveRequestDto;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostModel {
  private int id;
  private String content;
  private String media;
  private boolean removed;
  private int likes;

  public static PostModel from(PostSaveRequestDto dto) {
    return new PostModel(dto.getId(), dto.getContent(), dto.getMedia(), false, 0);
  }
}
