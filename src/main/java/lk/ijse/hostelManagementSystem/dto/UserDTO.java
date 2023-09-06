package lk.ijse.hostelManagementSystem.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class UserDTO implements SuperDTO{
    private String userName;
    private String password;

}
