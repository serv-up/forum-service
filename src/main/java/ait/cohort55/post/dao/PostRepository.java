package ait.cohort55.post.dao;

import ait.cohort55.post.model.Post;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Stream;

public interface PostRepository extends MongoRepository<Post, String> {
    Stream<Post> findPostByAuthorIgnoreCase(String author);

    Stream<Post> findPostByTagsInIgnoreCase(List<String> tag);

    Stream<Post> findPostByDateCreatedBetween(LocalDate from, LocalDate to);
}
