package ru.rosbank.javaschool.rest;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import ru.rosbank.javaschool.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.dto.PostResponseDto;
import ru.rosbank.javaschool.service.PostService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class RestPostController {
    private final PostService service;
    private final Logger logger = LoggerFactory.getLogger(RestPostController.class);

    @GetMapping(params = "q")
    public List<PostResponseDto> searchByContent(@RequestParam String q) {
        return service.searchByContent(q);
    }

    @PostMapping
    public PostResponseDto save(@RequestBody PostSaveRequestDto dto) {
        return service.save(dto);
    }

    @DeleteMapping("/{id}")
    public void removeById(@PathVariable int id) {
        service.removeById(id);
    }

    @PostMapping("/{id}/likes")
    public PostResponseDto likeById(@PathVariable int id) {
        return service.likeById(id);
    }

    @DeleteMapping("/{id}/likes")
    public PostResponseDto dislikeById(@PathVariable int id) {
        return service.dislikeById(id);
    }

    @GetMapping(params = {"lastPost", "count"})
    public List<PostResponseDto> getSomePosts(@RequestParam int lastPost, @RequestParam int count) {
        logger.info(Thread.currentThread().getName());
        return service.getSomePosts(lastPost, count);
    }

    @GetMapping(params = {"firstPostId"})
    public int getCountOfNewPosts(@RequestParam int firstPostId) {
        logger.info(Thread.currentThread().getName());
        return service.getCountOfNewPosts(firstPostId);
    }

    @GetMapping
    public int getFirstPostId() {
        logger.info(Thread.currentThread().getName());
        return service.getFirstId();
    }
}