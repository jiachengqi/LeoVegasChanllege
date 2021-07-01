package com.example.demo;

import com.example.demo.dataObject.Account;
import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.AccountMapper;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

public class AccountMapperTest {

    private static Account account;
    private static AccountDTO accountDTO;

    @Before
    public void setupAccountObject() {
        account = new Account(1L, "jiacheng qi", "F", new Date());
    }

    @Test
    public void testDataObjectToDataTransferObject() {
        accountDTO = AccountMapper.DoToDataTransferObject(account);
        Assert.assertEquals(accountDTO.getId(), account.getId());
        Assert.assertEquals(accountDTO.getPlayerName(), account.getPlayerName());
        Assert.assertEquals(accountDTO.getSex(), account.getSex());

    }

    @Test
    public void testDataTransferObjectToDataObject() {
        account = AccountMapper.DtoToDataObject(accountDTO);
        Assert.assertEquals(account.getId(), accountDTO.getId());
        Assert.assertEquals(account.getPlayerName(), accountDTO.getPlayerName());
        Assert.assertEquals(account.getSex(), accountDTO.getSex());

    }
}
