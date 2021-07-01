package com.example.demo.dto;

import com.example.demo.dataObject.Account;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class AccountMapper {

    public static Account DtoToDataObject(AccountDTO accountDTO) {
        return new Account.AccountBuilder().setDateCreated(new Date()).setId(accountDTO.getId()).setPlayerName(accountDTO.getPlayerName()).setSex(accountDTO.getSex()).build();
    }

    public static AccountDTO DoToDataTransferObject(Account account) {
        double balance = account.getTransactions().stream().mapToDouble(o -> o.getAmount().doubleValue()).sum();
        return new AccountDTO.AccountDTOBuilder().setId(account.getId()).setDateCreated(account.getDateCreated()).setPlayerName(account.getPlayerName()).setId(account.getId()).setSex(account.getSex()).setBalance(new BigDecimal(balance)).build();
    }

    public static List<AccountDTO> DataObjectToDTOList(List<Account> accounts) {
        return accounts.stream().map(AccountMapper::DoToDataTransferObject).collect(Collectors.toList());
    }

}
