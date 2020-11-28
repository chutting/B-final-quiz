package com.thoughtworks.capability.gtb.entrancequiz.repo;

import com.thoughtworks.capability.gtb.entrancequiz.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
  @Modifying
  @Transactional
  @Query(value = "delete from group_entity_trainees where trainees_id = :traineeId",nativeQuery = true)
  void deleteTraineeInGroup(Long traineeId);

  @Modifying
  @Transactional
  @Query(value = "delete from group_entity_trainers where trainers_id = :trainerId",nativeQuery = true)
  void deleteTrainerInGroup(Long trainerId);
}
