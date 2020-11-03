package com.thoughtworks.capability.gtb.entrancequiz.repo;

import com.thoughtworks.capability.gtb.entrancequiz.entity.StudentEntity;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Repository
public class StudentRepository{

  private List<StudentEntity> allStudents = new ArrayList(Arrays.asList(new StudentEntity(1, "成吉思汗"), new StudentEntity(2, "鲁班七号"), new StudentEntity(3, "太乙真人"),
      new StudentEntity(4, "钟无艳"), new StudentEntity(5, "花木兰"), new StudentEntity(6, "雅典娜"),
      new StudentEntity(7, "芈月"), new StudentEntity(8, "白起"), new StudentEntity(9, "刘禅"),
      new StudentEntity(10, "庄周"), new StudentEntity(11, "马超"), new StudentEntity(12, "刘备"),
      new StudentEntity(13, "哪吒"), new StudentEntity(14, "大乔"), new StudentEntity(15, "蔡文姬")));

  // TODO GTB-工程实践: - Magic Number
  // TODO GTB-工程实践: - 代码不优雅
  // TODO GTB-完成度: - 应该在点击分组的时候才生成组
  private List<String> allGroups = Arrays.asList("1", "2", "3", "4", "5", "6");

  private List<StudentEntity> getAllStudents() {
    return allStudents;
  }

  private List<String> getAllGroups() {
    return allGroups;
  }

  public List<StudentEntity> findAllStudents() {
    return this.getAllStudents();
  }

  public List<String> findAllGroups() {
    return this.getAllGroups();
  }

  public void updateGroupIndexOfStudent(String groupName, int studentId) {
    this.allStudents.stream().filter(student -> student.getId() == studentId).forEach(student -> student.setGroup(groupName));
  }

  public void save(StudentEntity student) {
    this.allStudents.add(student);
  }
}
