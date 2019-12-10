package ru.rosbank.javaschool.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.rosbank.javaschool.dto.PostResponseDto;
import ru.rosbank.javaschool.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.exception.BadRequestException;
import ru.rosbank.javaschool.entity.PostEntity;
import ru.rosbank.javaschool.repository.PostRepository;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostService {
    private final PostRepository repository;
    private final Logger logger = LoggerFactory.getLogger(PostService.class);

    public List<PostResponseDto> getAll() {
        return repository.findAll().stream()
                .map(PostResponseDto::from)
                .collect(Collectors.toList());
    }

    public PostResponseDto save(PostSaveRequestDto dto) {
        logger.info(dto.toString());
        return PostResponseDto.from(
                repository.save(PostEntity.from(
                        dto)));
    }

    public List<PostResponseDto> searchByContent(String q) {
        return repository.findAllByContentLike(q).stream()
                .map(PostResponseDto::from)
                .collect(Collectors.toList());
    }

    public void removeById(int id) {
        repository.deleteById(id);
    }


    public PostResponseDto likeById(int id) {
        final PostEntity entity = repository.findById(id).orElseThrow(BadRequestException::new);
        entity.setLikes(entity.getLikes() + 1);
        return PostResponseDto.from(entity);
    }

    public PostResponseDto dislikeById(int id) {
        final PostEntity entity = repository.findById(id).orElseThrow(BadRequestException::new);
            entity.setLikes(entity.getLikes() - 1);
        return PostResponseDto.from(entity);
    }

    public List<PostResponseDto> getSomePosts(int lastPost, int step) {
        return repository.findAll().stream()
                .sorted((o1, o2) -> o2.getId() - o1.getId())
                .skip(lastPost)
                .limit(step)
                .map(PostResponseDto::from)
                .collect(Collectors.toList());

    }

    public int getCountOfNewPosts(int firstPostId) {
        Optional<PostEntity> firstPost = repository.findById(firstPostId);
        List<Optional<PostEntity>> collect = repository.findAll().stream()
                .sorted((o1, o2) -> o2.getId() - o1.getId())
                .map(Optional::of)
                .collect(Collectors.toList());
        return collect.indexOf(firstPost);
    }

    public int getFirstId() {
        List<PostResponseDto> collect = repository.findAll().stream()
                .sorted((o1, o2) -> o2.getId() - o1.getId())
                .limit(1)
                .map(PostResponseDto::from)
                .collect(Collectors.toList());
        return collect.get(0).getId();
    }
}