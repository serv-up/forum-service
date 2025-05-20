package ait.cohort55.post.service;

import ait.cohort55.post.dao.PostRepository;
import ait.cohort55.post.dto.NewCommentDto;
import ait.cohort55.post.dto.NewPostDto;
import ait.cohort55.post.dto.PostDto;
import ait.cohort55.post.model.Comment;
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
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        post.addLike();
        postRepository.save(post);
    }

    @Override
    public PostDto updatePost(String id, NewPostDto newPostDto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        post.setTitle(newPostDto.getTitle());
        post.setContent(newPostDto.getContent());
        if (newPostDto.getTags() != null) {
            post.getTags().clear();
            post.getTags().addAll(newPostDto.getTags());
        }
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public PostDto deletePost(String id) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        PostDto postDto = modelMapper.map(post, PostDto.class);
        postRepository.delete(post);
        return postDto;
    }

    @Override
    public PostDto addComment(String id, String author, NewCommentDto newCommentDto) {
        Post post = postRepository.findById(id).orElseThrow(PostNotFoundException::new);
        Comment comment = new Comment(author, newCommentDto.getMessage());
        post.addComment(comment);
        post = postRepository.save(post);
        return modelMapper.map(post, PostDto.class);
    }

    @Override
    public Iterable<PostDto> findPostsByAuthor(String author) {
        List<Post> posts = postRepository.findAll().stream()
                .filter(post -> post.getAuthor().equals(author))
                .toList();
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    @Override
    public Iterable<PostDto> findPostsByTags(List<String> tags) {
        List<Post> posts = postRepository.findAll().stream()
                .filter(post -> post.getTags().stream()
                        .anyMatch(tag -> tags.contains(tag)))
                .toList();
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }

    @Override
    public Iterable<PostDto> findPostsByPeriod(LocalDate dateFrom, LocalDate dateTo) {
        List<Post> posts = postRepository.findAll().stream()
                .filter(post -> {
                    LocalDate postDate = post.getDateCreated().toLocalDate();
                    return (postDate.isEqual(dateFrom) || postDate.isAfter(dateFrom)) &&
                           (postDate.isEqual(dateTo) || postDate.isBefore(dateTo));
                })
                .toList();
        return posts.stream()
                .map(post -> modelMapper.map(post, PostDto.class))
                .toList();
    }
}
