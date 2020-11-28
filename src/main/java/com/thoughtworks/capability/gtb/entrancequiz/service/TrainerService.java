package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.entity.GroupEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.TraineeEntity;
import com.thoughtworks.capability.gtb.entrancequiz.entity.TrainerEntity;
import com.thoughtworks.capability.gtb.entrancequiz.repo.GroupRepository;
import com.thoughtworks.capability.gtb.entrancequiz.repo.TrainerRepository;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrainerService {
  private final TrainerRepository trainerRepository;
  private final GroupRepository groupRepository;

  public TrainerService(TrainerRepository trainerRepository, GroupRepository groupRepository) {
    this.trainerRepository = trainerRepository;
    this.groupRepository = groupRepository;
  }


  public List<TrainerEntity> findAllUnGroupedTrainers() {
    LinkedList<TrainerEntity> allGroupedTrainers = new LinkedList<>();
    List<GroupEntity> allGroups = groupRepository.findAll();
    allGroups.forEach(group -> {
      allGroupedTrainers.addAll(group.getTrainers());
    });
    List<TrainerEntity> allTrainers = findAllTrainers();
    return allTrainers.stream().filter(trainer ->
        allGroupedTrainers.stream().filter(groupedTrainer -> groupedTrainer.getId() == trainer.getId()).count() == 0
    ).collect(Collectors.toList());
  }

  private List<TrainerEntity> findAllTrainers() {
    return trainerRepository.findAll();
  }

  public TrainerEntity saveTrainer(TrainerEntity newTrainer) {
    return trainerRepository.save(newTrainer);
  }

  public void deleteTrainee(Long trainer_id) {
    groupRepository.deleteTrainerInGroup(trainer_id);
    trainerRepository.deleteById(trainer_id);
  }
}
