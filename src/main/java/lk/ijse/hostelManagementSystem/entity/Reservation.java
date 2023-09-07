package lk.ijse.hostelManagementSystem.entity;

import javax.persistence.*;
import lombok.*;
import java.sql.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Reservation implements SuperEntity {
    @Id
    private String res_id;
    private Date date;
    private String status;
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Student student;
    @ToString.Exclude
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Room room;

}
