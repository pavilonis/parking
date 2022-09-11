package com.example.parking.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.Objects;

@MappedSuperclass
@Setter
@Getter
public class BaseEntity {

   @Id
   private int id;
   private String name;

   @Override
   public boolean equals(Object o) {
      if (this == o) return true;
      if (o == null || getClass() != o.getClass()) return false;
      BaseEntity that = (BaseEntity) o;
      return id == that.id;
   }

   @Override
   public int hashCode() {
      return Objects.hash(id);
   }
}
