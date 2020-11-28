package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.entity.TrainerEntity;
import com.thoughtworks.capability.gtb.entrancequiz.service.TrainerService;
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

@WebMvcTest(TrainerController.class)
@AutoConfigureJsonTesters
class TrainerControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private TrainerService trainerService;

  @Autowired
  private JacksonTester<TrainerEntity> trainerJson;

  private TrainerEntity initTrainer;
  private List<TrainerEntity> trainerList = new ArrayList<>();

  @BeforeEach
  public void init() {
    initTrainer = TrainerEntity.builder()
        .id(1L)
        .name("ctt")
        .build();

    trainerList.add(initTrainer);
  }

  @Test
  void couldGetAllUnGroupedTrainers() throws Exception{
    when(trainerService.findAllUnGroupedTrainers()).thenReturn(trainerList);
    mockMvc.perform(get("/trainers?grouped=false"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("ctt")));
  }

  @Test
  void couldSaveNewTrainer() throws Exception{
    TrainerEntity newTrainer = TrainerEntity.builder()
        .id(2L)
        .name("chuttin")
        .build();

    mockMvc.perform(post("/trainers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(trainerJson.write(newTrainer).getJson()))
        .andExpect(status().isCreated());
    verify(trainerService).saveTrainer(newTrainer);
  }

  @Test
  void couldDeleteTrainer() throws Exception {
    mockMvc.perform(delete("/trainers/1"))
        .andExpect(status().isNoContent());
    verify(trainerService).deleteTrainer(1L);
  }

  @Test
  void shouldReturnErrorWhenNameBlank() throws Exception {
    TrainerEntity newTrainer = TrainerEntity.builder()
        .id(2L)
        .name("")
        .build();

    when(trainerService.saveTrainer(newTrainer))
        .thenThrow(new RuntimeException("请求出错"));

    mockMvc.perform(post("/trainers")
        .contentType(MediaType.APPLICATION_JSON)
        .content(trainerJson.write(newTrainer).getJson()))
        .andExpect(status().isBadRequest())
        .andExpect(jsonPath("$.message", is("请求出错")));
  }
}