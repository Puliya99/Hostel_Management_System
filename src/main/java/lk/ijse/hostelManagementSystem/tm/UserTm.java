package lk.ijse.hostelManagementSystem.tm;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@ToString
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserTm {
    private String userName;
    private String password;
    private String passwordHint;
}
