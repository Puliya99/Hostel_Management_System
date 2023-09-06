package lk.ijse.hostelManagementSystem.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
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
    private LocalDate dob;
    private String gender;
    @ToString.Exclude
    private final List<ReservationDTO> reservationList = new ArrayList<>();
}
