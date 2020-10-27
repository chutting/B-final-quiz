package com.thoughtworks.capability.gtb.entrancequiz.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import static org.hamcrest.CoreMatchers.not;

import static jdk.internal.dynalink.support.Guards.isNotNull;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
class StudentControllerTest {
  @Autowired
  MockMvc mockMvc;

  @Test
  void couldGetAllStudents() throws Exception{
    mockMvc.perform(get("/students"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(15)));
  }

  @Test
  void couldGetAllGroups() throws Exception{
    mockMvc.perform(get("/groups"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$", hasSize(6)));
  }

  @Test
  void couldGroupingRandomly() throws Exception {
    mockMvc.perform(get("/grouping"))
        .andExpect(status().isOk());
  }
}