package com.example.parking.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Entity
public class ParkingLot {

   @Id
   private Integer id;
   private String name;
   private int size;

}
