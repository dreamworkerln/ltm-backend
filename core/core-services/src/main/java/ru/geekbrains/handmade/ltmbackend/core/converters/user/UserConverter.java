package ru.geekbrains.handmade.ltmbackend.core.converters.user;

import ru.geekbrains.handmade.ltmbackend.core.converters._base.AbstractConverter;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.specifications.user.UserSpecBuilder;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserSpecDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter extends AbstractConverter<User, UserDto, UserSpecDto> {

    @Autowired
    public UserConverter(UserMapper userMapper, UserSpecBuilder userSpecBuilder) {
        this.entityMapper = userMapper;
        this.specBuilder = userSpecBuilder;

        this.entityClass = User.class;
        this.dtoClass = UserDto.class;
        this.specClass = UserSpecDto.class;
    }


    @Override
    protected void validate(User user) {
        super.validate(user);



        // ... custom validation
    }
}
