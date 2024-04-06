package africa.semicolon.data.repository;

import africa.semicolon.data.model.Todo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


public interface TodoRepository extends MongoRepository<Todo, String> {
    Todo findByAuthor(String author);

    void deleteByTitle(String title);

    Todo findByTitle(String title);
}
