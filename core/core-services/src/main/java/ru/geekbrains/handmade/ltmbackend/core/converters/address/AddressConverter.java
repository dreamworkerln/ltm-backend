package ru.geekbrains.handmade.ltmbackend.core.converters.address;


import org.springframework.stereotype.Component;
import ru.geekbrains.handmade.ltmbackend.core.converters._base.AbstractConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.Address;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractSpecDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.address.AddressDto;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class AddressConverter extends AbstractConverter<Address, AddressDto, AbstractSpecDto> {

    @Autowired
    public AddressConverter(AddressMapper addressMapper) {
        this.entityMapper = addressMapper;

        this.entityClass = Address.class;
        this.dtoClass = AddressDto.class;
        this.specClass = AbstractSpecDto.class;
    }


    @Override
    protected void validate(Address address) {
        super.validate(address);

        // ... custom validation
    }
}
