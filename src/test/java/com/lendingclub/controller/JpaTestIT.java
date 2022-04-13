package com.lendingclub.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@DataJpaTest
//@WebMvcTest
public class JpaTestIT {

    // TODO remove it rodrigo

    /*
    Test repositorires here
     */

//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void foo() throws Exception {
//        mockMvc.perform(
//                MockMvcRequestBuilders
//                    .get("/foo/test")
//                    .contentType(MediaType.APPLICATION_JSON)
//                    .accept(MediaType.APPLICATION_JSON)
//            )
//            .andExpect(status().isOk())
//            .andExpect(content().string("ok"))
//            .andReturn();
//    }

}
