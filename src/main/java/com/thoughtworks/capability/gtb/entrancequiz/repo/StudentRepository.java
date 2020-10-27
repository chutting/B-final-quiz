package com.thoughtworks.capability.gtb.entrancequiz.repo;

import com.thoughtworks.capability.gtb.entrancequiz.entity.StudentEntity;
import org.springframework.stereotype.Repository;

import java.util.Arrays;
import java.util.List;

@Repository
public class StudentRepository{
  public List<StudentEntity> findAll() {
    return Arrays.asList(new StudentEntity(1, "成吉思汗"), new StudentEntity(2, "鲁班七号"), new StudentEntity(3, "太乙真人"),
        new StudentEntity(4, "钟无艳"), new StudentEntity(5, "花木兰"), new StudentEntity(6, "雅典娜"),
        new StudentEntity(7, "芈月"), new StudentEntity(8, "白起"), new StudentEntity(9, "刘禅"),
        new StudentEntity(10, "庄周"), new StudentEntity(11, "马超"), new StudentEntity(12, "刘备"),
        new StudentEntity(13, "哪吒"), new StudentEntity(14, "大乔"), new StudentEntity(15, "蔡文姬"));
  }
}
