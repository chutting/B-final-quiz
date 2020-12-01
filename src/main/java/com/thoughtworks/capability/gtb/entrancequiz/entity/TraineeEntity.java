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
  @NotBlank(message = "trainee's name could not be blank")
  private String name;
  // TODO GTB-工程实践: - Comment, 一段代码如果不需要，应该删除而非将其注释
//  @ManyToOne
//  @JoinColumn(name = "group_id")
//  private GroupEntity group;
}
