package com.thoughtworks.capability.gtb.entrancequiz.repo;

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
class TrainerRepositoryTest {
  @Autowired
  private TrainerRepository trainerRepository;

  @Autowired
  private TestEntityManager entityManager;

  private TrainerEntity initTrainer;
  private List<TrainerEntity> trainerList = new ArrayList<>();

  @BeforeEach
  public void init() {
    initTrainer = TrainerEntity.builder()
        .name("ctt")
        .build();

    trainerList.add(initTrainer);
    entityManager.persistAndFlush(initTrainer);
  }

  @Test
  @DirtiesContext
  void shouldFindAll() {
    List<TrainerEntity> allTrainers = trainerRepository.findAll();
    assertEquals(allTrainers, trainerList);
  }

  @Test
  @DirtiesContext
  void shouldSaveTrainer() {
    TrainerEntity newTrainer = TrainerEntity.builder()
        .name("chuttin")
        .build();
    trainerRepository.save(newTrainer);
    assertEquals(trainerRepository.findAll().size(), 2);
  }

  @Test
  @DirtiesContext
  void shouldFindById() {
    Optional<TrainerEntity> trainer = trainerRepository.findById(1L);
    assertTrue(trainer.isPresent());
    assertEquals(trainer.get().getName(), "ctt");
  }

  @Test
  @DirtiesContext
  void shouldDeleteById() {
    Optional<TrainerEntity> trainerBefore = trainerRepository.findById(1L);
    assertTrue(trainerBefore.isPresent());
    trainerRepository.deleteById(1L);
    Optional<TrainerEntity> trainerAfter = trainerRepository.findById(1L);
    assertFalse(trainerAfter.isPresent());
  }

}