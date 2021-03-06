package ru.geekbrains.handmade.ltmbackend.core.entities.base;

import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * Base for simple entities
 */

@MappedSuperclass
public abstract class AbstractEntity extends AbstractEntityNoGen {

    @Id
    @Column(name = "id")
    // https://stackoverflow.com/questions/2951454/should-transient-property-be-used-in-equals-hashcode-tostring
    @EqualsAndHashCode.Exclude
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Override
    public Long getId() { return id; }
}
