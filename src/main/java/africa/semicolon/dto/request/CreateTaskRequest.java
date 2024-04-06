package africa.semicolon.dto.request;

import africa.semicolon.data.model.Tasks;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class CreateTaskRequest {

    private String title;
    private String body;
    private String author;
    List<Tasks> taskList = new ArrayList<>();
    private String localDate = createdAt();

    private String createdAt() {
        LocalDateTime datedTime = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
        return datedTime.format(dateTimeFormatter);
    }
}