package ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.address;


import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractDto;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AddressDto extends AbstractDto {

    private String city;
    private String street;
    private Integer house;
    private Integer building;
    private Integer flat;
    private Double longitude;
    private Double latitude;


    public AddressDto(String city, String street, Integer house, Integer building, Integer flat,
                      Double longitude, Double latitude) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.building = building;
        this.flat = flat;
        this.longitude = longitude;
        this.latitude = latitude;
    }
}
