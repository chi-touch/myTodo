package africa.semicolon.data.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

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
    private String userName;
    private boolean isLocked;
    @DBRef
    private List<Tasks> tasks = new ArrayList<>();

}
