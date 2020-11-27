package com.thoughtworks.capability.gtb.entrancequiz.repo;

import com.thoughtworks.capability.gtb.entrancequiz.entity.TraineeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TraineeRepository extends JpaRepository<TraineeEntity, Long> {
}
