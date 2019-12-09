package ru.rosbank.javaschool.service;

import org.junit.jupiter.api.Test;
import ru.rosbank.javaschool.dto.PostResponseDto;
import ru.rosbank.javaschool.dto.PostSaveRequestDto;
import ru.rosbank.javaschool.entity.PostEntity;
import ru.rosbank.javaschool.exception.BadRequestException;
import ru.rosbank.javaschool.repository.PostRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostServiceTest {

    @Test
    void getAllCorrect() {
        PostRepository repoMock = mock(PostRepository.class);
        PostService service = new PostService(repoMock);
        PostEntity post = new PostEntity(1,"test",null,false,0);
        List<PostEntity> list = new ArrayList<>();
        list.add(post);
        when(repoMock.findAll()).thenReturn(list);

        PostResponseDto dto = new PostResponseDto(1,"test",null,0);
        List<PostResponseDto> listDto = new ArrayList<>();
        listDto.add(dto);

        List<PostResponseDto> result=service.getAll();
        assertIterableEquals(result, listDto);
    }

    @Test
    void getSomePostsCorrect() {
        PostRepository repoMock = mock(PostRepository.class);
        PostService service = new PostService(repoMock);
        PostEntity post = new PostEntity(1,"test",null,false,0);
        List<PostEntity> list = new ArrayList<>();
        list.add(post);
        when(repoMock.findAll()).thenReturn(list);

        PostResponseDto dto = new PostResponseDto(1,"test",null,0);
        List<PostResponseDto> listDto = new ArrayList<>();
        listDto.add(dto);

        List<PostResponseDto> result = service.getSomePosts(0,1);
        assertIterableEquals(result, listDto);
    }

    @Test
    void getSomePostsReturnsEmpty() {
        PostRepository repoMock = mock(PostRepository.class);
        PostEntity post = new PostEntity(1,"test",null,false,0);
        PostService service = new PostService(repoMock);
        List<PostEntity> list = new ArrayList<>();
        list.add(post);
        when(repoMock.findAll()).thenReturn(list);

        List<PostResponseDto> result = service.getSomePosts(5,10);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void saveCorrect() {
        PostRepository repoMock = mock(PostRepository.class);
        PostSaveRequestDto dto = new PostSaveRequestDto(0,"test","");
        PostService service = new PostService(repoMock);
        PostEntity post = PostEntity.from(dto);
        when(repoMock.save(post)).thenReturn(post);

        PostResponseDto expected = PostResponseDto.from(post);

        PostResponseDto result = service.save(dto);
        assertEquals(result, expected);
    }

    @Test
    void searchByContentCorrect() {
        PostRepository repoMock = mock(PostRepository.class);
        PostService service = new PostService(repoMock);
        PostEntity post = new PostEntity(1,"test",null,false,0);
        List<PostEntity> list=new ArrayList<>();
        list.add(post);
        when(repoMock.findAllByContentLike("test")).thenReturn(list);

        PostResponseDto dto = new PostResponseDto(1,"test",null,0);
        List<PostResponseDto> listDto = new ArrayList<>();
        listDto.add(dto);

        List<PostResponseDto> result = service.searchByContent("test");
        assertIterableEquals(result, listDto);
    }

    @Test
    void removeByIdCorrect() {
        PostRepository repoMock = mock(PostRepository.class);
        PostService service = new PostService(repoMock);
        PostEntity post = new PostEntity(1,"test",null,false,0);
        List<PostEntity> list = new ArrayList<>();
        list.add(post);
        when(repoMock.save(post)).thenReturn(post);
        when(repoMock.findAll()).thenReturn(Collections.emptyList());

        PostSaveRequestDto dto = new PostSaveRequestDto(1, "test", null);
        PostResponseDto dtoReturn = service.save(dto);
        PostResponseDto dtoMustReturn = new PostResponseDto(1, "test", null, 0);

        service.removeById(1);
        List<PostResponseDto> result=service.getAll();
        assertEquals(dtoMustReturn, dtoReturn);
        assertEquals(Collections.emptyList(), result);
    }

    @Test
    void likeByIdCorrect() {
        PostRepository repoMock = mock(PostRepository.class);
        PostService service = new PostService(repoMock);
        PostEntity post = new PostEntity(1,"test",null,false,0);
        PostResponseDto expected = new PostResponseDto(1,"test",null,1);
        when(repoMock.findById(1)).thenReturn(Optional.of(post));

        PostResponseDto result = service.likeById(1);
        assertEquals(result, expected);
    }

    @Test
    void likeByIdThrowsException() {
        PostRepository repoMock = mock(PostRepository.class);
        PostService service = new PostService(repoMock);
        when(repoMock.findById(1)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, ()->service.likeById(1));
    }

    @Test
    void dislikeByIdCorrect() {
        PostRepository repoMock = mock(PostRepository.class);
        PostService service = new PostService(repoMock);
        PostEntity post = new PostEntity(1,"test",null,false,1);
        when(repoMock.findById(1)).thenReturn(Optional.of(post));
        PostResponseDto expected = new PostResponseDto(1,"test",null,0);

        PostResponseDto result = service.dislikeById(1);
        assertEquals(result, expected);
    }

    @Test
    void dislikeByIdThrowsException() {
        PostRepository repoMock=mock(PostRepository.class);
        PostService service=new PostService(repoMock);

        when(repoMock.findById(1)).thenReturn(Optional.empty());

        assertThrows(BadRequestException.class, ()->service.dislikeById(1));
    }

    @Test
    void getCountOfNewPostsCorrect() {
        PostRepository repoMock = mock(PostRepository.class);
        PostService service = new PostService(repoMock);
        PostEntity post1 = new PostEntity(1,"test1",null,false,0);
        PostEntity post2 = new PostEntity(2,"test2",null,false,0);
        List<PostEntity> list = new ArrayList<>();
        list.add(post1);
        list.add(post2);
        when(repoMock.findById(1)).thenReturn(Optional.of(post1));
        when(repoMock.findAll()).thenReturn(list);

        int result = service.getCountOfNewPosts(1);
        assertEquals(1, result);
    }

    @Test
    void getFirstIdCorrect() {
        PostRepository repoMock = mock(PostRepository.class);
        PostService service = new PostService(repoMock);
        PostEntity post = new PostEntity(1,"test",null,false,0);
        List<PostEntity> list = new ArrayList<>();
        list.add(post);

        when(repoMock.findAll()).thenReturn(list);

        int result = service.getFirstId();
        assertEquals(1, result);
    }
}