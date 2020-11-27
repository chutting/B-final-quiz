package com.thoughtworks.capability.gtb.entrancequiz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String name;

  @OneToMany(cascade = CascadeType.REFRESH, fetch=FetchType.LAZY, orphanRemoval = true)
  private List<TraineeEntity> trainees;

  @OneToMany(cascade = CascadeType.REFRESH, fetch=FetchType.LAZY, orphanRemoval = true)
  private List<TrainerEntity> trainers;

  public GroupEntity(String name, List<TraineeEntity> trainees, List<TrainerEntity> trainers) {
    this.name = name;
    this.trainees = trainees;
    this.trainers = trainers;
  }
}
