package ait.cohort55.post.service;

import ait.cohort55.post.dao.PostRepository;
import ait.cohort55.post.dto.NewCommentDto;
import ait.cohort55.post.dto.NewPostDto;
import ait.cohort55.post.dto.PostDto;
import ait.cohort55.post.model.Post;
import ait.cohort55.post.dto.exception.PostNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Override
    public PostDto addNewPost(String author, NewPostDto newPostDto) {
        Post post = modelMapper.map(newPostDto, Post.class);
        post.setAuthor(author);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto findPostById(String id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public void addLike(String id) {

    }

    @Override
    public PostDto updatePost(String id, NewPostDto newPostDto) {
        return null;
    }

    @Override
    public PostDto deletePost(String id) {
        return null;
    }

    @Override
    public PostDto addComment(String id, String author, NewCommentDto newCommentDto) {
        return null;
    }

    @Override
    public Iterable<PostDto> findPostsByAuthor(String author) {
        return null;
    }

    @Override
    public Iterable<PostDto> findPostsByTags(List<String> tags) {
        return null;
    }

    @Override
    public Iterable<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        return null;
    }
}