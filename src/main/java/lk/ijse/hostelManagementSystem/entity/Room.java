package lk.ijse.hostelManagementSystem.entity;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Room {
    private String room_type_id;
    private String type;
    private String key_money;
    private String qty;
}
