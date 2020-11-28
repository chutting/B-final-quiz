package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.entity.TraineeEntity;
import com.thoughtworks.capability.gtb.entrancequiz.service.TraineeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TraineeController.class)
@AutoConfigureJsonTesters
class TraineeControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TraineeService traineeService;

  @Autowired
  private JacksonTester<TraineeEntity> traineeJson;

  private TraineeEntity initTrainee;
  private List<TraineeEntity> traineeList = new ArrayList<>();

  @BeforeEach
  public void init() {
    initTrainee = TraineeEntity.builder()
        .id(1L)
        .name("ctt")
        .build();

    traineeList.add(initTrainee);
  }

  @Test
  void couldGetAllUnGroupedTrainees() throws Exception{
    when(traineeService.findAllUnGroupedTrainees()).thenReturn(traineeList);
    mockMvc.perform(get("/trainees?grouped=false"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("ctt")));
  }

  @Test
  void couldSaveNewTrainee() throws Exception{
    TraineeEntity newTrainee = TraineeEntity.builder()
        .id(2L)
        .name("chuttin")
        .build();

    mockMvc.perform(post("/trainees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(traineeJson.write(newTrainee).getJson()))
        .andExpect(status().isCreated());
    verify(traineeService).saveTrainee(newTrainee);
  }

  @Test
  void couldDeleteTrainee() throws Exception {
    mockMvc.perform(delete("/trainees/1"))
        .andExpect(status().isNoContent());
    verify(traineeService).deleteTrainee(1L);
  }

  @Test
  void shouldReturnErrorWhenNameBlank() throws Exception {
    TraineeEntity newTrainee = TraineeEntity.builder()
        .id(2L)
        .name("")
        .build();

    when(traineeService.saveTrainee(newTrainee))
        .thenThrow(new RuntimeException("请求出错"));

    mockMvc.perform(post("/trainees")
        .contentType(MediaType.APPLICATION_JSON)
        .content(traineeJson.write(newTrainee).getJson()))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("请求出错")));
  }
}