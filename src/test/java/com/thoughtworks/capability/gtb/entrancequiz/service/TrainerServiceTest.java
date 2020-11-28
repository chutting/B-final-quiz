package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.entity.GroupEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.TrainerEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.NotFoundException;
import com.thoughtworks.capability.gtb.entrancequiz.repo.GroupRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repo.TrainerRepository;
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
class TrainerServiceTest {
  TrainerService trainerService;
  @Mock
  TrainerRepository trainerRepository;

  @Mock
  GroupRepository groupRepository;

  private TrainerEntity initFirstTrainer;
  private TrainerEntity initSecondTrainer;
  private List<TrainerEntity> trainerListInGroup = new ArrayList<>();
  private List<TrainerEntity> allTrainers = new ArrayList<>();
  private GroupEntity initGroup;
  private List<GroupEntity> groupList = new ArrayList<>();

  @BeforeEach
  void setUp() {
    trainerService = new TrainerService(trainerRepository, groupRepository);
    initFirstTrainer = TrainerEntity.builder()
        .id(1L)
        .name("ctt")
        .build();

    initSecondTrainer = TrainerEntity.builder()
        .id(2L)
        .name("chuttin")
        .build();

    trainerListInGroup.add(initFirstTrainer);

    allTrainers.add(initFirstTrainer);
    allTrainers.add(initSecondTrainer);

    initGroup = GroupEntity.builder()
        .id(1L)
        .name("1 组")
        .trainers(trainerListInGroup)
        .build();

    groupList.add(initGroup);
  }

  @Test
  void shouldFindAllTrainers() {
    when(trainerRepository.findAll()).thenReturn(allTrainers);
    when(groupRepository.findAll()).thenReturn(groupList);

    assertEquals(trainerService.findAllUnGroupedTrainers(), Arrays.asList(initSecondTrainer));
  }

  @Test
  void couldSaveTrainer() {
    TrainerEntity newTrainer = TrainerEntity.builder()
        .id(3L)
        .name("chu")
        .build();
    trainerService.saveTrainer(newTrainer);
    verify(trainerRepository).save(newTrainer);
  }

  @Test
  void couldDeleteTrainer() {
    when(trainerRepository.findById(1L)).thenReturn(Optional.of(initFirstTrainer));
    trainerService.deleteTrainer(1L);
    verify(groupRepository).deleteTrainerInGroup(1L);
    verify(trainerRepository).deleteById(1L);
  }

  @Test
  void shouldReturnErrorWhenTraineeNotExist() {
    when(trainerRepository.findById(1L)).thenReturn(Optional.empty());
    assertThrows(NotFoundException.class, () -> trainerService.deleteTrainer(1L));
  }

}