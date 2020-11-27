package com.thoughtworks.capability.gtb.entrancequiz.repo;

import com.thoughtworks.capability.gtb.entrancequiz.entity.TrainerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainerRepository extends JpaRepository<TrainerEntity, Long> {
}
