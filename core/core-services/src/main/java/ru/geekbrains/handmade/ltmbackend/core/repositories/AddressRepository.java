package ru.geekbrains.handmade.ltmbackend.core.repositories;

import ru.geekbrains.handmade.ltmbackend.core.entities.Address;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CustomRepository<Address, Long>{
}
