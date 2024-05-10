package africa.semicolon.data.repository;

import africa.semicolon.data.model.Status;
import africa.semicolon.data.model.TodoTaskList;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;


public interface TodoRepository extends MongoRepository<TodoTaskList, String> {
    TodoTaskList findByAuthor(String author);

    void deleteByTitle(String title);

    TodoTaskList findByTitle(String title);

    List<TodoTaskList> findByStatus(Status status);


    TodoTaskList findTaskById(String taskId);
}
