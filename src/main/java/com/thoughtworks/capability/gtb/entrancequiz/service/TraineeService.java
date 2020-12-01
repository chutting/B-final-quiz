package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.entity.GroupEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.TraineeEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.NotFoundException;
import com.thoughtworks.capability.gtb.entrancequiz.repo.GroupRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repo.TraineeRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TraineeService {
  private final TraineeRepository traineeRepository;
  private final GroupRepository groupRepository;

  public TraineeService(TraineeRepository traineeRepository, GroupRepository groupRepository) {
    this.traineeRepository = traineeRepository;
    this.groupRepository = groupRepository;
  }

  private List<TraineeEntity> findAllTrainees() {
    return traineeRepository.findAll();
  }

  public TraineeEntity saveTrainee(TraineeEntity newStudent) {
    return traineeRepository.save(newStudent);
  }

  public List<TraineeEntity> findAllUnGroupedTrainees() {
    LinkedList<TraineeEntity> allGroupedTrainees = new LinkedList<>();
    // TODO GTB-工程实践: - 建议用SQL来进行筛选，内存筛选不但逻辑写复杂了，而且在Trainee很多的情况下也有问题
    List<GroupEntity> allGroups = groupRepository.findAll();
    allGroups.forEach(group -> {
      allGroupedTrainees.addAll(group.getTrainees());
    });
    List<TraineeEntity> allTrainees = findAllTrainees();
    return allTrainees.stream().filter(trainee ->
        allGroupedTrainees.stream().filter(groupedTrainee -> groupedTrainee.getId() == trainee.getId()).count() == 0
    ).collect(Collectors.toList());
  }

  public void deleteTrainee(Long trainee_id) {
    // TODO GTB-工程实践: if 即使只有一条语句也建议加{}
    if (!traineeRepository.findById(trainee_id).isPresent()) throw new NotFoundException("trainee_id not found");
    groupRepository.deleteTraineeInGroup(trainee_id);
    traineeRepository.deleteById(trainee_id);
  }
}
