package com.uexcel.roomservice.command.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NonNull;


@Entity
@Data
public class Room {
    @Id
    private String number;
    private String roomTypeId;
}
