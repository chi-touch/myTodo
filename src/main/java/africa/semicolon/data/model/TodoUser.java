package africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
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
    private TaskPriority priority;
    private Status status;

    private boolean isCompleted;

    private LocalDateTime dateRegistered = LocalDateTime.now();
    @DBRef
    private List<Tasks> tasks = new ArrayList<>();

}
