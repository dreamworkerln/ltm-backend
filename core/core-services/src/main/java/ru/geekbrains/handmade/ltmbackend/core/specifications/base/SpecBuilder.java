package ru.geekbrains.handmade.ltmbackend.core.specifications.base;

import ru.geekbrains.handmade.ltmbackend.core.entities.base.AbstractEntity;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto._base.AbstractSpecDto;
import org.springframework.data.jpa.domain.Specification;

public interface SpecBuilder<E extends AbstractEntity, S extends AbstractSpecDto> {
    Specification<E> build(S specDto);
}
