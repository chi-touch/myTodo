package africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Data
@Document
public class TodoUser {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private boolean isLocked;
    private LocalDateTime dateRegistered = LocalDateTime.now();

    private List<TodoTaskList> tasks = new ArrayList<>();





}
