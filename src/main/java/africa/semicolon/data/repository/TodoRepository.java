package africa.semicolon.data.repository;

import africa.semicolon.data.model.Status;
import africa.semicolon.data.model.TodoTask;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface TodoRepository extends MongoRepository<TodoTask, String> {
    TodoTask findByAuthor(String author);

    void deleteByTitle(String title);

    TodoTask findByTitle(String title);

    List<TodoTask> findByStatus(Status status);


}
