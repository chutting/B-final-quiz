package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.entity.TraineeEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.NotFoundException;
import com.thoughtworks.capability.gtb.entrancequiz.service.TraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
// TODO GTB-工程实践: - 未使用的import语句

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/trainees")
public class TraineeController {
  private final TraineeService traineeService;

  public TraineeController(TraineeService traineeService) {
    this.traineeService = traineeService;
  }

  @GetMapping
  public List<TraineeEntity> getUnGroupedTrainees(@RequestParam boolean grouped) {
    if (!grouped) {
      return traineeService.findAllUnGroupedTrainees();
    }
    throw new NotFoundException("this interface not found");
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Valid
  public TraineeEntity saveTrainees(@RequestBody TraineeEntity trainee) {
    return traineeService.saveTrainee(trainee);
  }

  @DeleteMapping("/{trainee_id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTrainees(@PathVariable Long trainee_id) {
    traineeService.deleteTrainee(trainee_id);
  }
}
