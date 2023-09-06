package lk.ijse.hostelManagementSystem.dto;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class RoomDTO {
    private String room_type_id;
    private String type;
    private Double key_money;
    private Integer qty;
    @ToString.Exclude
    private List<ReservationDTO> reservationList = new ArrayList<>();

}
