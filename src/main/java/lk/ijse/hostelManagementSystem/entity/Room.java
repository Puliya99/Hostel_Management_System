package lk.ijse.hostelManagementSystem.entity;

import javax.persistence.*;
import lombok.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class Room implements SuperEntity {
    @ToString.Exclude
    @OneToMany(mappedBy = "room", targetEntity = Reservation.class)
    List<Reservation> reservationList = new ArrayList<>();
    @Id
    private String room_type_id;
    private String type;
    private Double key_money;
    private Integer qty;
}
