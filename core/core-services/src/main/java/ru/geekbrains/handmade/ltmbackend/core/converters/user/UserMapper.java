package ru.geekbrains.handmade.ltmbackend.core.converters.user;

import ru.geekbrains.handmade.ltmbackend.core.converters._base.AbstractMapper;
import ru.geekbrains.handmade.ltmbackend.core.entities.user.User;
import ru.geekbrains.handmade.ltmbackend.core.services.user.UserService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.user.UserDto;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Mapper(config = AbstractMapper.class)
public abstract class UserMapper extends AbstractMapper<User, UserDto> {

    @Autowired
    private UserService userService;

    @PostConstruct
    private void postConstruct() {
        this.baseRepoAccessService = userService;
        //constructor = new EntityConstructor();
    }

    @Mapping(target = "password", ignore = true)
    //@Mapping(target = "taskMembers", ignore = true)
    public abstract UserDto toDto(User user);

    @Mapping(target = "refreshTokenList", expression = "java(null)") // всегда подгружаем из БД
    @Mapping(target = "taskMembers", ignore = true)
    public abstract User toEntity(UserDto userDto);


//    @AfterMapping
//    UserDto afterMapping(User source, @MappingTarget UserDto target) {
//
//        UserDto result = target;
//        result.getAccount().setUser(result);
//        return result;
//    }


//    @AfterMapping
//    public User afterMapping(UserDto source, @MappingTarget User target) {
//
//        return merge(source, target);
//    }

//    protected class EntityConstructor extends Constructor<User, UserDto> {
//
//        //private UserRoleService userRoleService;
//
//        @Override
//        public User create(UserDto dto, User entity) {
//
//        // Mapstruct 1.4 maybe will support constructors with params
//            return null;
//            //return new User();
////            return new User(
////            dto.getUsername(),
////            dto.getPassword(),
////            dto.getFirstName(),
////            dto.getLastName(),
////            dto.getAge(),
////            dto.getEmail(),
////            dto.getPhoneNumber());
//        }
//
//    }
}
