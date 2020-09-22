package ru.geekbrains.handmade.ltmbackend.ltmapplication.repositories;

import ru.geekbrains.handmade.ltmbackend.ltmapplication.entities.TestEntity;
import ru.geekbrains.handmade.ltmbackend.core.repositories.CustomRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.handmade.ltmbackend.core.repositories.CustomRepository;

@Repository
public interface TestRepository extends CustomRepository<TestEntity, Long> {
}
