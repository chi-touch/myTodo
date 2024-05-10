package africa.semicolon.dto.request;

import africa.semicolon.data.model.Status;
import africa.semicolon.data.model.TaskPriority;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class TodoTaskRequest {
    private String author;
    private String title;
    private String body;
    private String LocalDate = createAt();
    private Status status;
    private TaskPriority priority;

    private String createAt(){
        LocalDateTime dateTime = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
        return dateTime.format(dateTimeFormatter);
    }


}
