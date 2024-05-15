package africa.semicolon.dto.request;

import africa.semicolon.data.model.Status;
import africa.semicolon.data.model.MyStatus;
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
    private String username;
    private String LocalDate = createAt();
    private MyStatus priority;



    private String createAt(){
        LocalDateTime dateTime = LocalDateTime.now();

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss");
        return dateTime.format(dateTimeFormatter);
    }


}
