package lk.ijse.hostelManagementSystem.entity;

import lombok.*;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Room {
    @Id
    private String room_type_id;
    private String type;
    private Double key_money;
    private Integer qty;
    @ToString.Exclude
    @OneToMany(mappedBy = "room", targetEntity = Reservation.class)
    List<Reservation> reservationList = new ArrayList<>();
}
