package ru.geekbrains.handmade.ltmbackend.core.services;

import ru.geekbrains.handmade.ltmbackend.core.repositories.AddressRepository;
import ru.geekbrains.handmade.ltmbackend.core.services.base.BaseRepoAccessService;
import ru.geekbrains.handmade.ltmbackend.core.entities.Address;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AddressService extends BaseRepoAccessService<Address> {

    private final AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        super(addressRepository);
        this.addressRepository = addressRepository;
    }
}
