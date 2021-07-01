package com.example.demo;

import com.example.demo.dataObject.Account;
import com.example.demo.dataObject.Wallet;
import com.example.demo.dto.AccountDTO;
import com.example.demo.dto.AccountMapper;
import com.example.demo.dto.WalletDTO;
import com.example.demo.dto.WalletMapper;
import com.example.demo.service.AccountService;
import com.example.demo.service.WalletService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
public class ControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AccountService accountService;
    @MockBean
    private WalletService walletService;
    @Autowired
    WebApplicationContext webApplicationContext;

    @Test
    public void postPlayerTest() throws Exception {
        AccountDTO accountDTO = new AccountDTO(1L, "lebron james", "M", new Date(), new BigDecimal(0));
        ObjectMapper mapper = new ObjectMapper();
        Account account = AccountMapper.DtoToDataObject(accountDTO);

        Mockito.when(accountService.save(ArgumentMatchers.any())).thenReturn(account);
        String json = mapper.writeValueAsString(account);

        MvcResult result = mockMvc.perform(post("/v1/players")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.playerName", Matchers.equalTo("lebron james"))).andReturn();

    }


    @Test
    public void getPlayerTest() throws Exception {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        AccountDTO accountDTO = new AccountDTO(1L, "lebron james", "M", new Date(), new BigDecimal(0));
        Account account = accountService.save(AccountMapper.DtoToDataObject(accountDTO));


        Mockito.when(accountService.accountByPK(accountDTO.getId())).thenReturn(account);

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                .get("/v1/players").accept(MediaType.APPLICATION_JSON).characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(status().isOk())

                .andReturn();

    }


    @Test
    public void putPlayerTest() throws Exception {
        AccountDTO accountDTO = new AccountDTO(1L, "lebron james", "M", new Date(), new BigDecimal(0));
        ObjectMapper mapper = new ObjectMapper();

        accountDTO.setPlayerName("kobe byrant");
        Account accountNew = AccountMapper.DtoToDataObject(accountDTO);

        Mockito.when(accountService.update(ArgumentMatchers.any(), ArgumentMatchers.any())).thenReturn(accountNew);
        String json = mapper.writeValueAsString(accountNew);

        MvcResult result = mockMvc.perform(put("/v1/players/1")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.playerName", Matchers.equalTo("kobe byrant"))).andReturn();

    }


    @Test
    public void postTransactionTest() throws Exception {
        Account account = new Account(1L, "jiacheng qi", "M", new Date());
        WalletDTO walletDTO =
                new WalletDTO.WalletDTOBuilder().setAccountId(1L).setAmount(new BigDecimal(12)).setId(12L).setPurpose("debit").setTransactionDate(new Date()).setTransactionReference(123L).build();
        ObjectMapper mapper = new ObjectMapper();
        Wallet wallet = WalletMapper.DtoToDo(walletDTO);

        Mockito.when(walletService.createTransaction(ArgumentMatchers.any())).thenReturn(wallet);
        String json = mapper.writeValueAsString(wallet);

        MvcResult result = mockMvc.perform(post("/v1/1/transactions")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("utf-8")
                .content(json).accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.transactionReference", Matchers.equalTo(123))).andReturn();

    }


}
