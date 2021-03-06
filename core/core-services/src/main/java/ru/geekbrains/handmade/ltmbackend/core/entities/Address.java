package ru.geekbrains.handmade.ltmbackend.core.entities;

import ru.geekbrains.handmade.ltmbackend.core.entities.base.AbstractEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Data
@EqualsAndHashCode(callSuper = true)
@Table(name = "address")
public class Address extends AbstractEntity {

    @Column
    private String city;

    @Column

    private String street;

    @Column
    private int house;

    @Column
    private int building;

//    @Column
//    private int frontDoor;

    @Column
    private int flat;

    @Column
    private String longitude;

    @Column
    private String latitude;

    public Address(){}
    public Address(String city, String street, int house, int building, int flat) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.building = building;
        this.flat = flat;
    }

    public String addressFormatToRequest(){

        // TODO: geodata
//        if(building == 0){
//            return  "+" + city + "+" + street + "+" + house;
//        }
//        return "+" + city + "+" + street + "+" + house + "+" + building;
        String result = "+" + city + "+" + street + "+" + house;
        if(building != 0) {
            result +=  "+" + house;
        }
        return result;
    }

    @Override
    public String toString() {
        return "Address{city= " + city
                + ", street= " + street
                + ", house= " + house
                + ", building= " + building
                //+ ", front_door= " + frontDoor
                + ", flat= " + flat
                + ", longitude= " + longitude
                + ", latitude= " + latitude + "}";
    }
}
