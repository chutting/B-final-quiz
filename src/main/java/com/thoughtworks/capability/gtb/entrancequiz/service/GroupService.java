package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.entity.GroupEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.TraineeEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.TrainerEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.TrainerNotEnoughException;
import com.thoughtworks.capability.gtb.entrancequiz.repo.GroupRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repo.TraineeRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repo.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class GroupService {
  private final GroupRepository groupRepository;
  private final TraineeRepository traineeRepository;
  private final TrainerRepository trainerRepository;

  private final int TRAINER_COUNT_PER_GROUP = 2;

  public GroupService(GroupRepository groupRepository, TraineeRepository traineeRepository, TrainerRepository trainerRepository) {
    this.groupRepository = groupRepository;
    this.traineeRepository = traineeRepository;
    this.trainerRepository = trainerRepository;
  }


  public List<GroupEntity> findAllGroups() {
    return groupRepository.findAll();
  }

  public List<GroupEntity> grouping() {
    List<TrainerEntity> allTrainers = trainerRepository.findAll();
    // TODO GTB-工程实践: - Code Smell, Magic Number
    if (allTrainers.size() < 2) {
      throw new TrainerNotEnoughException("trainer's size less than 2");
    }
    clearGroups();

    int groupSize = allTrainers.size() / TRAINER_COUNT_PER_GROUP;

    List<TraineeEntity> allTrainees = traineeRepository.findAll();

    autoGrouping(allTrainees, allTrainers, groupSize);

    return findAllGroups();
  }

  private void autoGrouping(List<TraineeEntity> allTrainees, List<TrainerEntity> allTrainers, int groupSize) {
    int studentNum = allTrainees.size();
    int remainingStudentNum = studentNum % groupSize;
    int studentNumPerGroup = studentNum / groupSize;

    Collections.shuffle(allTrainees);
    Collections.shuffle(allTrainers);

    for (int i = 1; i <= groupSize; i++) {
      List<TraineeEntity> traineesInTheGroup = new ArrayList<>(allTrainees.subList((i - 1) * studentNumPerGroup, i * studentNumPerGroup));
      List<TrainerEntity> trainersInTheGroup = new ArrayList<>(allTrainers.subList((i - 1) * TRAINER_COUNT_PER_GROUP, i * TRAINER_COUNT_PER_GROUP));
      if (i <= remainingStudentNum) {
        traineesInTheGroup.add(allTrainees.get(allTrainees.size() - i));
      }
      GroupEntity currentGroup = getCurrentGroup(i, traineesInTheGroup, trainersInTheGroup);
      groupRepository.save(currentGroup);
    }

    deleteExtraGroup(groupSize);
  }

  private void clearGroups() {
    List<GroupEntity> allGroups = findAllGroups();
    allGroups.forEach(group -> {
      group.getTrainers().clear();
      group.getTrainees().clear();
      groupRepository.save(group);
    });
  }

  private void deleteExtraGroup(int currentGroupSize) {
    int oldGroupSize = findAllGroups().size();
    if (currentGroupSize < oldGroupSize) {
      for (long i = currentGroupSize; i < oldGroupSize; i++) {
        groupRepository.deleteById(i + 1);
      }
    }
  }

  private GroupEntity getCurrentGroup(long id, List<TraineeEntity> trainees, List<TrainerEntity> trainers) {
    Optional<GroupEntity> currentGroupOptional = getGroupById(id);

    if (currentGroupOptional.isPresent()) {
      GroupEntity currentGroup = currentGroupOptional.get();
      currentGroup.getTrainees().addAll(trainees);
      currentGroup.getTrainers().addAll(trainers);
      return currentGroup;
    } else {
      GroupEntity currentGroup = new GroupEntity(id + " 组", trainees, trainers);
      return currentGroup;
    }
  }

  private Optional<GroupEntity> getGroupById(long id) {
    return groupRepository.findById(id);
  }
}
