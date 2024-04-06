package africa.semicolon.data.repository;

import africa.semicolon.data.model.Tasks;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TasksRepository extends MongoRepository<Tasks,String> {
}
