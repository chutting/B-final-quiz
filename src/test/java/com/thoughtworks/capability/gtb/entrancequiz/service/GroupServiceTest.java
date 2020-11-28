package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.entity.GroupEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.TraineeEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.TrainerEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.TrainerNotEnoughException;
import com.thoughtworks.capability.gtb.entrancequiz.repo.GroupRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repo.TraineeRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repo.TrainerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class GroupServiceTest {
  GroupService groupService;
  @Mock
  TraineeRepository traineeRepository;

  @Mock
  GroupRepository groupRepository;

  @Mock
  TrainerRepository trainerRepository;

  private TraineeEntity initTrainee;
  private List<TraineeEntity> allTrainees = new ArrayList<>();
  private GroupEntity initGroup;
  private List<GroupEntity> groupList = new ArrayList<>();
  private TrainerEntity initFirstTrainer;

  @BeforeEach
  void setUp() {
    groupService = new GroupService(groupRepository, traineeRepository, trainerRepository);
    initTrainee = TraineeEntity.builder()
        .id(1L)
        .name("ctt")
        .build();

    allTrainees.add(initTrainee);

    initGroup = GroupEntity.builder()
        .id(1L)
        .name("1 组")
        .trainees(allTrainees)
        .build();
    initFirstTrainer = TrainerEntity.builder()
        .id(1L)
        .name("chu")
        .build();

    groupList.add(initGroup);
  }

  @Test
  void shouldFindAllGroups() {
    when(groupRepository.findAll()).thenReturn(groupList);
    assertEquals(groupService.findAllGroups(), groupList);
  }

  @Test
  void shouldThrowExceptionWhenTrainersCountLessThan2() {
    when(trainerRepository.findAll()).thenReturn(Arrays.asList(initFirstTrainer));
    assertThrows(TrainerNotEnoughException.class, () -> groupService.grouping());
  }
}