package africa.semicolon.dto.request;

import lombok.Data;

import java.time.LocalDate;
@Data
public class CreateTaskRequest {

    private String title;
    private String body;
    private LocalDate localDate;
}
