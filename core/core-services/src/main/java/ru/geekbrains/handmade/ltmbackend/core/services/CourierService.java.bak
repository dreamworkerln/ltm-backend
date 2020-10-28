package ru.geekbrains.handmade.ltmbackend.core.services;

import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.repositories.CourierRepository;
import ru.geekbrains.handmade.ltmbackend.core.services.base.BaseRepoAccessService;
import ru.geekbrains.handmade.ltmbackend.core.entities.Courier;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Service
@Transactional
public class CourierService extends BaseRepoAccessService<Courier> {

    private final CourierRepository courierRepository;
    private final UserService userService;

    public CourierService(CourierRepository courierRepository, UserService userService) {
        super(courierRepository);
        this.courierRepository = courierRepository;
        this.userService = userService;
    }

    public Optional<Courier> findOneByUser(User user) {
        return courierRepository.findOneByUser(user);
    }

    public Optional<Courier> findByUsername(String username) {
        return courierRepository.findOneByUserUsername(username);
    }


    public Courier getCurrent() {

        AtomicReference<Courier> result = new AtomicReference<>();
        findOneByUser(userService.getCurrent()).ifPresent(result::set);
        return result.get();
    }

}