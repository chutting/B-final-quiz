package com.thoughtworks.capability.gtb.entrancequiz.repo;

import com.thoughtworks.capability.gtb.entrancequiz.entity.GroupEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {
}
