package africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Document
@Data
public class TodoTask {
    @Id
    private String id;
    private String title;
    private String body;
    private String author;
    private String localDate = createdAt();
    private Status status;
    private TaskPriority priority;
    private boolean isCompleted;

    private String createdAt() {
        LocalDateTime date = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
        return date.format(dateTimeFormatter);
    }

    List<Tasks> taskList = new ArrayList<>();
}
