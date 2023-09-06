package lk.ijse.hostelManagementSystem.entity;

import lombok.*;

import javax.persistence.Id;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class User implements SuperEntity {
    @Id
    private String userName;
    private String password;

}
