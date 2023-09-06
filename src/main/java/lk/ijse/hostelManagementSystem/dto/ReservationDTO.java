package lk.ijse.hostelManagementSystem.dto;

import lombok.*;
import java.sql.Date;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class ReservationDTO {
    private String res_id;
    private Date date;
    private String status;
    @ToString.Exclude
    private StudentDTO studentDto;
    @ToString.Exclude
    private RoomDTO roomDto;
}
