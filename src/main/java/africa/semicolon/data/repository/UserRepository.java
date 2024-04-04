package africa.semicolon.data.repository;

import africa.semicolon.data.model.TodoUser;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface UserRepository extends MongoRepository<TodoUser, String> {
    TodoUser findByUserName(String username);
}
