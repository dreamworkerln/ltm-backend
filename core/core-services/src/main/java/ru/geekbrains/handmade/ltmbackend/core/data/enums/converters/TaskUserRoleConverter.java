package ru.geekbrains.handmade.ltmbackend.core.data.enums.converters;

import ru.geekbrains.handmade.ltmbackend.utils.data.enums.task.TaskUserRole;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class TaskUserRoleConverter implements AttributeConverter<TaskUserRole, String> {

    @Override
    public String convertToDatabaseColumn(TaskUserRole role) {
        if (role == null) {
            return null;
        }
        return role.getCode();
    }

    @Override
    public TaskUserRole convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return TaskUserRole.getByCode(code);
    }
}
