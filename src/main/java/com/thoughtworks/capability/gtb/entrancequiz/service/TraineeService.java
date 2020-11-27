package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.entity.GroupEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.TraineeEntity;
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
    traineeRepository.deleteById(trainee_id);
  }
}
