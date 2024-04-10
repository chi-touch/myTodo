package africa.semicolon.dto.request;

import africa.semicolon.data.model.Status;
import africa.semicolon.data.model.TaskPriority;
import africa.semicolon.data.model.Tasks;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class UpdateTaskRequest {

    private String title;
    private String body;
    private String author;
    List<Tasks> taskList = new ArrayList<>();
    private String localDate = createdAt();
    private TaskPriority priority;
    private Status status;

    private String createdAt() {
        LocalDateTime datedTime = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
        return datedTime.format(dateTimeFormatter);
    }
}
