package africa.semicolon.dto.response;

import africa.semicolon.data.model.Status;
import africa.semicolon.data.model.TodoTask;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
public class CompleteTaskResponse {
    @DBRef
   private List<TodoTask> actual;
    private String message;
    private Status status;
    private String author;


}
