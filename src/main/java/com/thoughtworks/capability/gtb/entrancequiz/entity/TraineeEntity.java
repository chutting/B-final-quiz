package com.thoughtworks.capability.gtb.entrancequiz.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TraineeEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @NotBlank
  private String name;
//  @ManyToOne
//  @JoinColumn(name = "group_id")
//  private GroupEntity group;
}
