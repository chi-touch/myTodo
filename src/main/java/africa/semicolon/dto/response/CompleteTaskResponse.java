package africa.semicolon.dto.response;

import africa.semicolon.data.model.Status;
import africa.semicolon.data.model.TodoTaskList;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.List;

@Data
public class CompleteTaskResponse {
    @DBRef
   private List<TodoTaskList> actual;
    private String message;
    private Status status;
    private String author;


}
