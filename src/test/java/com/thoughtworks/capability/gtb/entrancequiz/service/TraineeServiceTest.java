package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.entity.GroupEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.TraineeEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.NotFoundException;
import com.thoughtworks.capability.gtb.entrancequiz.repo.GroupRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repo.TraineeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TraineeServiceTest {
  TraineeService traineeService;
  @Mock
  TraineeRepository traineeRepository;

  @Mock
  GroupRepository groupRepository;

  private TraineeEntity initFirstTrainee;
  private TraineeEntity initSecondTrainee;
  private List<TraineeEntity> traineeListInGroup = new ArrayList<>();
  private List<TraineeEntity> allTrainees = new ArrayList<>();
  private GroupEntity initGroup;
  private List<GroupEntity> groupList = new ArrayList<>();

  @BeforeEach
  void setUp() {
    traineeService = new TraineeService(traineeRepository, groupRepository);
    initFirstTrainee = TraineeEntity.builder()
        .id(1L)
        .name("ctt")
        .build();

    initSecondTrainee = TraineeEntity.builder()
        .id(2L)
        .name("chuttin")
        .build();

    traineeListInGroup.add(initFirstTrainee);

    allTrainees.add(initFirstTrainee);
    allTrainees.add(initSecondTrainee);

    initGroup = GroupEntity.builder()
        .id(1L)
        .name("1 组")
        .trainees(traineeListInGroup)
        .build();

    groupList.add(initGroup);
  }

  @Test
  void shouldFindAllTrainees() {
    when(traineeRepository.findAll()).thenReturn(allTrainees);
    when(groupRepository.findAll()).thenReturn(groupList);

    assertEquals(traineeService.findAllUnGroupedTrainees(), Arrays.asList(initSecondTrainee));
  }

  @Test
  void couldSaveTrainee() {
    TraineeEntity newTrainee = TraineeEntity.builder()
        .id(3L)
        .name("chu")
        .build();
    traineeService.saveTrainee(newTrainee);
    verify(traineeRepository).save(newTrainee);
  }

  @Test
  void couldDeleteTrainee() {
    when(traineeRepository.findById(1L)).thenReturn(Optional.of(initFirstTrainee));
    traineeService.deleteTrainee(1L);
    verify(groupRepository).deleteTraineeInGroup(1L);
    verify(traineeRepository).deleteById(1L);
  }

  @Test
  void shouldReturnErrorWhenTraineeNotExist() {
    when(traineeRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> traineeService.deleteTrainee(1L));
  }

}