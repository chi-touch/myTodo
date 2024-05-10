package africa.semicolon.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateTaskResponse {
    private String taskId;
    private String message;
    private String author;
}
