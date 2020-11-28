package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.entity.GroupEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.TraineeEntity;
import com.thoughtworks.capability.gtb.entrancequiz.service.GroupService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(GroupController.class)
@AutoConfigureJsonTesters
class GroupControllerTest {
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private GroupService groupService;

  private TraineeEntity initTrainee;
  private List<TraineeEntity> traineeList = new ArrayList<>();
  private GroupEntity initGroup;
  private List<GroupEntity> groupList = new ArrayList<>();

  @BeforeEach
  void setUp() {
    initTrainee = TraineeEntity.builder()
        .id(1L)
        .name("ctt")
        .build();

    traineeList.add(initTrainee);

    initGroup = GroupEntity.builder()
        .id(1L)
        .name("1 组")
        .trainees(traineeList)
        .build();

    groupList.add(initGroup);
  }

  @Test
  void shouldGetGroups() throws Exception {
    when(groupService.findAllGroups()).thenReturn(groupList);
    mockMvc.perform(get("/groups"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].name", is("1 组")));
  }

  @Test
  void shouldAutoGrouping() throws Exception {
    mockMvc.perform(post("/groups/auto-grouping"))
        .andExpect(status().isCreated());
    verify(groupService).grouping();
  }
}