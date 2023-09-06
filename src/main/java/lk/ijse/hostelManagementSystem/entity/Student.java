package lk.ijse.hostelManagementSystem.entity;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Student {
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
