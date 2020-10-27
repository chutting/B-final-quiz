package com.thoughtworks.capability.gtb.entrancequiz.service;

import com.thoughtworks.capability.gtb.entrancequiz.entity.StudentEntity;
import com.thoughtworks.capability.gtb.entrancequiz.repo.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
  private final StudentRepository studentRepository;

  public StudentService(StudentRepository studentRepository) {
    this.studentRepository = studentRepository;
  }

  public List<StudentEntity> findAllStudents() {
    return studentRepository.findAllStudents().stream()
        .sorted((stu1, stu2) -> stu1.getId() - stu2.getId())
        .collect(Collectors.toList());
  }

  public List<String> findAllGroups() {
    return studentRepository.findAllGroups();
  }

  public List<StudentEntity> grouping() {
    List<StudentEntity> allStudents = studentRepository.findAllStudents();
    List<String> allGroups = studentRepository.findAllGroups();

    int studentNum = allStudents.size();
    int groupNum = allGroups.size();

    Collections.shuffle(allStudents);

    int remainingStudentNum = studentNum % groupNum;

    int studentNumPerGroup = studentNum / groupNum;

    for(int i = 0; i < groupNum; i++) {
      List<StudentEntity> studentInTheGroup = new ArrayList(allStudents.subList(i * studentNumPerGroup, (i + 1) * studentNumPerGroup));
      if (i < remainingStudentNum) {
        studentInTheGroup.add(allStudents.get(allStudents.size() - 1 - i));
      }
      String groupName = allGroups.get(i);
      studentInTheGroup.forEach(student -> {
        studentRepository.updateGroupIndexOfStudent(groupName, student.getId());
      });
    }

    return studentRepository.findAllStudents();
  }

  public void saveStudent(StudentEntity newStudent) {
    List<StudentEntity> allStudents = findAllStudents();
    int lastId = allStudents.get(allStudents.size() - 1).getId();
    newStudent.setId(lastId + 1);
    studentRepository.save(newStudent);
  }
}
