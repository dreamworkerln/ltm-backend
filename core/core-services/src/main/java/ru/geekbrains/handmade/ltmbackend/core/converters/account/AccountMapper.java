package ru.geekbrains.handmade.ltmbackend.core.converters.account;

import ru.geekbrains.handmade.ltmbackend.core.converters._base.AbstractMapper;
import ru.geekbrains.handmade.ltmbackend.core.entities.Account;
import ru.geekbrains.handmade.ltmbackend.core.services.AccountService;
import ru.geekbrains.handmade.ltmbackend.jrpc_protocol.dto.Account.AccountDto;

import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;

@Mapper(config = AbstractMapper.class)
public abstract class AccountMapper extends AbstractMapper<Account, AccountDto> {

    @Autowired
    private AccountService accountService;


    @PostConstruct
    private void postConstruct() {
        this.baseRepoAccessService = accountService;
        //constructor = new AccountMapper.EntityConstructor();
    }

    @Mapping(target = "user", ignore = true)
    public abstract AccountDto toDto(Account account);

    @Mapping(target = "user", ignore = true)
    public abstract Account toEntity(AccountDto accountDto);

    @AfterMapping
    Account afterMapping(AccountDto source, @MappingTarget Account target) {
        return merge(source, target);
    }

//    protected class EntityConstructor extends Constructor<Account, AccountDto> {
//
//        //private UserRoleService userRoleService;
//
//        @Override
//        public Account create(AccountDto dto, Account entity) {
//            return new Account();
//        }
//
//    }


}
