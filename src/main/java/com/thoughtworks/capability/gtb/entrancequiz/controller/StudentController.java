package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.entity.StudentEntity;
import com.thoughtworks.capability.gtb.entrancequiz.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class StudentController {
  private final StudentService studentService;

  public StudentController(StudentService studentService) {
    this.studentService = studentService;
  }

  @GetMapping("/students")
  @CrossOrigin
  public ResponseEntity<List<StudentEntity>> getStudents() {
    return ResponseEntity.ok(studentService.findAllStudents());
  }

  @GetMapping("/groups")
  @CrossOrigin
  public ResponseEntity<List<String>> getGroups() {
    return ResponseEntity.ok(studentService.findAllGroups());
  }

  @GetMapping("/grouping")
  @CrossOrigin
  public ResponseEntity<List<StudentEntity>> grouping() {
    return ResponseEntity.ok((studentService.grouping()));
  }
}
