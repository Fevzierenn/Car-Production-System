package com.security.demo.demo.entities;

import com.security.demo.demo.enums.CarType;
import com.security.demo.demo.enums.FuelType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "Car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Enumerated(EnumType.STRING)
    @Column(name = "carType")
    CarType carType;
    String model;
    String perform;
    Double price;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuelTpye")
    FuelType fuelType;
    
    
    public String toString()
    {
        return "Car [ID=" + id + ", Type=" + carType + ", Model=" + model + ", Perform=" + perform + ", Price=" + price + ", FuelType=" + fuelType + "]";
    }


}
