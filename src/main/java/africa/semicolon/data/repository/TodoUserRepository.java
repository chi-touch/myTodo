package africa.semicolon.data.repository;

import africa.semicolon.data.model.TodoUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoUserRepository extends MongoRepository<TodoUser, String> {
    TodoUser findByUserName(String username);

}
