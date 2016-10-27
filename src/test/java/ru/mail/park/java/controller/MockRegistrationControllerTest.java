package ru.mail.park.java.controller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import ru.mail.park.dataSets.UserDataSet;
import ru.mail.park.services.SessionService;
import ru.mail.park.services.impl.AccountServiceImpl;
import ru.mail.park.services.impl.SessionServiceImpl;

import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by kirrok on 04.10.16.
 */
@WebMvcTest
@RunWith(SpringRunner.class)
public class MockRegistrationControllerTest {
    @MockBean
    private AccountServiceImpl accountService;
    @MockBean
    private SessionServiceImpl sessionService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testRegistration() throws Exception {
        when(accountService.getUser(anyString())).thenReturn(null);

        mockMvc.perform(post("/registration")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"login\":\"kirrok\", \"password\":\"1234\", \"email\":\"some@email.ru\"}")
        ).andExpect(status().isOk()).andExpect(jsonPath("login").value("kirrok"));
    }

//    @Test
//    public void testAuth() throws Exception {
//        when(accountService.getUser(anyString())).thenReturn(new UserDataSet("kirrok", "some@meil.ru", "1234"));
//        when(new UserDataSet(anyString(),anyString(),anyString()).getPassword().equals(anyString())).thenReturn(true);
//
//        mockMvc.perform(post("/auth")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"login\":\"kirrok\", \"password\":\"1234\"}")
//        ).andExpect(status().isOk()).andExpect(jsonPath("login").value("kirrok"));
//    }
}
