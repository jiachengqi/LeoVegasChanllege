package com.example.demo.serviceTest;

import com.example.demo.dataObject.Account;
import com.example.demo.exceptions.WalletException;
import com.example.demo.service.AccountService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertNotNull;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AccountServiceImpTest {

    @Autowired
    private AccountService accountService;


    @Before
    public  void setupAccount() throws WalletException {
        accountService.save(new Account(1L, "jiacheng qi", "M", new Date()));
    }

    @Test
    public void findPlayerByValidPrimaryKey() throws WalletException {
        Account account = accountService.accountByPK(1L);
        assertNotNull(account);
        Assert.assertEquals("jiacheng qi", account.getPlayerName());
    }

    @Test(expected = WalletException.class)
    public void createPlayerWithEmptyPlayerName() throws WalletException {
        accountService.save(new Account(1L, "", "M", new Date()));
    }

    @Test(expected = WalletException.class)
    public void createPlayerWithPlayerNameLessThan5Characters() throws WalletException {
        accountService.save(new Account(1L, "", "M", new Date()));
    }

    @Test(expected = WalletException.class)
    public void createPlayerWithNullPlayerName() throws WalletException {
        accountService.save(new Account(1L, null, "M", new Date()));
    }

    @Test
    public void createPlayerWithValidDetails() throws WalletException {
        accountService.save(new Account(2L, "gumball darwin", "M", new Date()));
    }

}
