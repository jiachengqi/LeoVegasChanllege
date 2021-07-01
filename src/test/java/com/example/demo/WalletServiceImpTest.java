package com.example.demo;

import com.example.demo.dataObject.Account;
import com.example.demo.dataObject.Wallet;
import com.example.demo.exceptions.InsufficientBalanceException;
import com.example.demo.exceptions.WalletException;
import com.example.demo.service.AccountService;
import com.example.demo.service.WalletService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.Date;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class WalletServiceImpTest {

    @Autowired
    private AccountService accountService;
    @Autowired
    private WalletService walletService;

    private Account account;

    @Before
    public void setupPlayerAccount() throws WalletException {
        account = accountService.save(new Account("jiacheng qi", "M", new Date()));
    }

    @Test(expected = InsufficientBalanceException.class)
    public void registerDebitTransactionof5000ForEricCartman() throws WalletException, InsufficientBalanceException {
        Wallet wallet = new Wallet(account, new BigDecimal(-5000), "debit", new Date(), 150L);
        walletService.createTransaction(wallet);
    }

    @Test
    public void registerCreditTransactionof10000ForEricCartman() throws WalletException, InsufficientBalanceException {
        Wallet saved1 = new Wallet(account, new BigDecimal(10000), "credit", new Date(), 150L);
        Wallet savedTransaction1 = walletService.createTransaction(saved1);
        assertNotNull(savedTransaction1);
        BigDecimal balance = walletService.balanceByAccountID(account.getId());
        assertTrue(balance.doubleValue() == 10000);
    }

}