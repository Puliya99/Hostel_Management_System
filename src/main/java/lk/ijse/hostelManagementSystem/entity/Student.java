package lk.ijse.hostelManagementSystem.entity;

import javax.persistence.*;
import lombok.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Student implements SuperEntity{
    @Id
    private  String student_id;
    private String name;
    private String address;
    private String contact_no;
    private Date date;
    private String gender;
    @ToString.Exclude
    @OneToMany(targetEntity = Reservation.class, mappedBy = "student", cascade = CascadeType.ALL)
    private List<Reservation> reservationList = new ArrayList<>();
}
