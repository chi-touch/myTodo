package africa.semicolon.dto.request;

import africa.semicolon.data.model.Task;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String userName;
    private boolean isLocked;
    @DBRef
    private List<Task> taskList = new ArrayList<>();

}
