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

    private String author;
    private String body;
    private String title;
    private  String localDate = finishDate();

    private String finishDate(){
        LocalDateTime finished = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
        return finished.format(dateTimeFormatter);
    }

    private String localDateTime = createdAt();
    private boolean isCompleted = false;

    private String createdAt() {
        LocalDateTime datedTime = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
        return datedTime.format(dateTimeFormatter);
    }
}
