package lk.ijse.hostelManagementSystem.dto;

import lombok.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class StudentDTO implements SuperDTO{
    private String student_id;
    private String name;
    private String address;
    private String contact_no;
    private Date dob;
    private String gender;
    @ToString.Exclude
    private final List<ReservationDTO> reservationList = new ArrayList<>();
}
