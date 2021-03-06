package com.thoughtworks.capability.gtb.entrancequiz.repo;

import com.thoughtworks.capability.gtb.entrancequiz.entity.TraineeEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.TrainerEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.DirtiesContext;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
class TraineeRepositoryTest {
  @Autowired
  private TraineeRepository traineeRepository;

  @Autowired
  private TestEntityManager entityManager;

  private TraineeEntity initTrainee;
  private List<TraineeEntity> traineeList = new ArrayList<>();

  @BeforeEach
  public void init() {
    initTrainee = TraineeEntity.builder()
        .name("ctt")
        .build();

    traineeList.add(initTrainee);
    entityManager.persistAndFlush(initTrainee);
  }

  @Test
  @DirtiesContext
  void shouldFindAll() {
    List<TraineeEntity> allTrainees = traineeRepository.findAll();
    assertEquals(allTrainees, traineeList);
  }

  @Test
  @DirtiesContext
  void shouldSaveTrainee() {
    TraineeEntity newTrainee = TraineeEntity.builder()
        .name("chuttin")
        .build();
    traineeRepository.save(newTrainee);
    assertEquals(traineeRepository.findAll().size(), 2);
  }

  @Test
  @DirtiesContext
  void shouldFindById() {
    Optional<TraineeEntity> trainee = traineeRepository.findById(1L);
    assertTrue(trainee.isPresent());
    assertEquals(trainee.get().getName(), "ctt");
  }

  @Test
  @DirtiesContext
  void shouldDeleteById() {
    Optional<TraineeEntity> traineeBefore = traineeRepository.findById(1L);
    assertTrue(traineeBefore.isPresent());
    traineeRepository.deleteById(1L);
    Optional<TraineeEntity> traineeAfter = traineeRepository.findById(1L);
    assertFalse(traineeAfter.isPresent());
  }

}