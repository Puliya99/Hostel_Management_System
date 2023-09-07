package lk.ijse.hostelManagementSystem.entity;

import javax.persistence.*;
import lombok.*;

@Entity
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
