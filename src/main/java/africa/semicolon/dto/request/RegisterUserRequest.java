package africa.semicolon.dto.request;

import africa.semicolon.data.model.Tasks;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.http.HttpStatusCode;

import java.util.ArrayList;
import java.util.List;
@Data
public class RegisterUserRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String userName;
    private boolean isLocked;
    //@DBRef
   // private List<Tasks> taskList = new ArrayList<>();
}
