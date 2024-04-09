package africa.semicolon.dto.request;

import lombok.Data;

@Data
public class RegisterUserRequest {
    private String firstName;
    private String lastName;
    private String password;
    private String username;
    private boolean isLocked;
    //@DBRef
   // private List<Tasks> taskList = new ArrayList<>();
}
