package ru.rosbank.javaschool.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.rosbank.javaschool.entity.PostEntity;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
  List<PostEntity> findAllByContentLike(String q);
}