package africa.semicolon.data.repository;

import africa.semicolon.data.model.TodoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoUserRepository extends MongoRepository<TodoUser, String> {
    TodoUser findByUsername(String username);
    boolean existsByUsername(String username);
}
