package com.thoughtworks.capability.gtb.entrancequiz.repo;

import com.thoughtworks.capability.gtb.entrancequiz.entity.GroupEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.TraineeEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class GroupRepositoryTest {
  @Autowired
  private GroupRepository groupRepository;

  @Autowired
  private TestEntityManager entityManager;

  private TraineeEntity initTrainee;
  private List<TraineeEntity> traineeList = new ArrayList<>();
  private GroupEntity initGroup;
  private List<GroupEntity> groupList = new ArrayList<>();

  @BeforeEach
  void setUp() {
    initTrainee = TraineeEntity.builder()
        .name("ctt")
        .build();

    traineeList.add(initTrainee);

    initGroup = GroupEntity.builder()
        .name("1 组")
        .trainees(traineeList)
        .build();

    groupList.add(initGroup);
    entityManager.persistAndFlush(initTrainee);
    entityManager.persistAndFlush(initGroup);
  }

  @Test
  @DirtiesContext
  void shouldFindAll() {
    assertEquals(groupRepository.findAll(), groupList);
  }

  @Test
  @DirtiesContext
  void shouldSaveGroup() {
    TraineeEntity newTrainee = TraineeEntity.builder()
        .name("chu")
        .build();
    entityManager.persistAndFlush(newTrainee);

    assertEquals(groupRepository.findAll().size(), 1);

    GroupEntity newGroup = GroupEntity.builder()
        .name("2 组")
        .trainees(Arrays.asList(newTrainee))
        .build();
    groupRepository.save(newGroup);
    assertEquals(groupRepository.findAll().size(), 2);
  }

  @Test
  @DirtiesContext
  void shouldFindById() {
    assertTrue(groupRepository.findById(1L).isPresent());
    assertEquals(groupRepository.findById(1L).get().getName(), "1 组");
  }

  @Test
  @DirtiesContext
  void shouldDeleteById() {
    assertTrue(groupRepository.findById(1L).isPresent());
    groupRepository.deleteById(1L);
    assertFalse(groupRepository.findById(1L).isPresent());
  }
}