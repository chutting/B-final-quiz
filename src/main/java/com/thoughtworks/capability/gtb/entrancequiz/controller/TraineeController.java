package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.entity.TraineeEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.NotFoundException;
import com.thoughtworks.capability.gtb.entrancequiz.service.TraineeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
// TODO GTB-工程实践: - import语句不要使用通配符

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
  // TODO GTB-知识点: - @Valid建议放在参数前
  @Valid
  public TraineeEntity saveTrainees(@RequestBody TraineeEntity trainee) {
    return traineeService.saveTrainee(trainee);
  }

  @DeleteMapping("/{trainee_id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  // TODO GTB-工程实践: - Naming, 变量名不符合规范, 应该使用小驼峰
  public void deleteTrainees(@PathVariable Long trainee_id) {
    traineeService.deleteTrainee(trainee_id);
  }
}
