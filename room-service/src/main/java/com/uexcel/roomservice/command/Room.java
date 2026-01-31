package com.uexcel.roomservice.command;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;


@Entity
@Data
public class Room {
    @Id
    private String number;
    private String type;
    private double price;
}
