package com.thoughtworks.capability.gtb.entrancequiz.controller;

import com.thoughtworks.capability.gtb.entrancequiz.entity.StudentEntity;
import com.thoughtworks.capability.gtb.entrancequiz.service.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
// TODO GTB-工程实践: - 未使用的import语句
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  // TODO GTB-知识点: - @CrossOrigin可以放在类上
  public ResponseEntity<List<StudentEntity>> getStudents() {
    return ResponseEntity.ok(studentService.findAllStudents());
  }

  // TODO GTB-完成度: - 获取分组的接口应该返回完整的分组
  @GetMapping("/groups")
  @CrossOrigin
  public ResponseEntity<List<String>> getGroups() {
    return ResponseEntity.ok(studentService.findAllGroups());
  }

  // TODO GTB-知识点: - 违反Restful实践, 创建资源的请求应该使用POST
  @GetMapping("/grouping")
  @CrossOrigin
  public ResponseEntity<List<StudentEntity>> grouping() {
    return ResponseEntity.ok((studentService.grouping()));
  }

  @PostMapping("/student")
  @CrossOrigin
  // TODO GTB-知识点: - 没有使用泛型
  public ResponseEntity saveStudent(@RequestBody StudentEntity student) {
    studentService.saveStudent(student);
    return ResponseEntity.created(null).build();
  }
}
