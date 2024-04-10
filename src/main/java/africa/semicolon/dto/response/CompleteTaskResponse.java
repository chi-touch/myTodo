package africa.semicolon.dto.response;

import africa.semicolon.data.model.Status;
import lombok.Data;

@Data
public class CompleteTaskResponse {
    private String message;
    private Status status;
    private String author;


}
