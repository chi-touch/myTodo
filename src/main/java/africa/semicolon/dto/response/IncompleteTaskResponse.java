package africa.semicolon.dto.response;

import africa.semicolon.data.model.Status;
import lombok.Data;

@Data
public class IncompleteTaskResponse {

    private String message;
    private String author;
    private Status status;

}
