package com.example.demo;



import com.example.demo.dataObject.Account;
import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.AccountMapper;
import com.example.demo.repo.AccountRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
public class RepoTest {

    @Autowired
    private AccountRepo accountRepo;



    @Test(expected = ObjectOptimisticLockingFailureException.class)
    public void testConcurrentAccountNameWriting() {


        accountRepo.save(new Account(1L, "jiacheng qi", "M", new Date()));

        Account accountForUser1 = accountRepo.findById(1L).get();
        Account accountForUser2 = accountRepo.findById(1L).get();




        AccountDTO accountDTOForUser1 = AccountMapper.DoToDataTransferObject(accountForUser1);
        AccountDTO accountDTOForUser2 = AccountMapper.DoToDataTransferObject(accountForUser2);


        accountDTOForUser1.setPlayerName("kobe byrant");
        accountDTOForUser2.setPlayerName("lebron james");

        Account preparedForUpdate1 = AccountMapper.DtoToDataObject(accountDTOForUser1);
        Account preparedForUpdate2 = AccountMapper.DtoToDataObject(accountDTOForUser2);

        assertEquals(0, accountForUser1.getVersion());
        assertEquals(0, accountForUser2.getVersion());

        accountRepo.save(preparedForUpdate1);
        accountRepo.save(preparedForUpdate2);

    }

}
