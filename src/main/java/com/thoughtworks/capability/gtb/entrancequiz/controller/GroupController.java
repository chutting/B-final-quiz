package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.entity.GroupEntity;
import com.thoughtworks.capability.gtb.entrancequiz.service.GroupService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/groups")
public class GroupController {
  private final GroupService groupService;

  public GroupController(GroupService groupService) {
    this.groupService = groupService;
  }

  @GetMapping
  public List<GroupEntity> getGroups() {
    return groupService.findAllGroups();
  }

  @PostMapping("/auto-grouping")
  public List<GroupEntity> grouping() {
    return groupService.grouping();
  }
}
