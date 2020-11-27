package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.entity.TrainerEntity;
import com.thoughtworks.capability.gtb.entrancequiz.exception.NotFoundException;
import com.thoughtworks.capability.gtb.entrancequiz.service.TrainerService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/trainers")
public class TrainerController {
  private final TrainerService trainerService;

  public TrainerController(TrainerService trainerService) {
    this.trainerService = trainerService;
  }

  @GetMapping
  public List<TrainerEntity> getTrainers(@RequestParam boolean grouped) {
    if (!grouped) {
      return trainerService.findAllUnGroupedTrainers();
    }
    throw new NotFoundException("this interface not found");
  }

  @PostMapping
  @ResponseStatus(HttpStatus.CREATED)
  @Valid
  public TrainerEntity saveTrainers(@RequestBody TrainerEntity trainer) {
    return trainerService.saveTrainer(trainer);
  }

  @DeleteMapping("/{trainer_id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void deleteTrainers(@PathVariable Long trainer_id) {
    trainerService.deleteTrainee(trainer_id);
  }
}
