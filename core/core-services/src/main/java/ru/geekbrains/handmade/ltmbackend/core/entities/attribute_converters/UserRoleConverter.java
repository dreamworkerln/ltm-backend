package ru.geekbrains.handmade.ltmbackend.core.entities.attribute_converters;

import ru.geekbrains.handmade.ltmbackend.utils.data.enums.UserRole;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class UserRoleConverter implements AttributeConverter<UserRole, String> {

    @Override
    public String convertToDatabaseColumn(UserRole role) {
        if (role == null) {
            return null;
        }
        return role.getCode();
    }

    @Override
    public UserRole convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return UserRole.getByCode(code);
    }
}