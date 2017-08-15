package com.swellwu.mapper;

import com.google.common.collect.Lists;
import com.swellwu.po.Student;
import com.swellwu.po.StudentExample;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * ${DESCRIPTION}
 *
 * @author swellwu
 * @create 2017-08-15-22:42
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StudentMapperTest {

    @Autowired
    StudentMapper studentMapper;

    @Before
    public void setUp(){
        List<Student> list = Lists.newArrayList();
        for(int i=0;i<100;++i){
            //builder构造po
            Student student = Student.builder().name("haha" + i).age(5 + Math.abs(new Random().nextInt() % 80))
                    .isDeleted(i % 2 == 0)
                    .uuid(UUID.randomUUID().toString().substring(0,20)).height(110 + Math.abs(new Random().nextInt() % 100))
                    .build();
            list.add(student);
        }
        studentMapper.batchInsertSelective(list, Student.Column.name, Student.Column.age, Student.Column.isDeleted,
                Student.Column.uuid, Student.Column.height);
        Assert.assertTrue(studentMapper.countByExample(new StudentExample())==100);
    }

    @Test
    public void prettyExampleTest(){
        //选取所有未删除、15岁以下身高大于165
        List<Student> students = studentMapper.selectByExample(new StudentExample().createCriteria().andDeleted(false)
                .andAgeLessThanOrEqualTo(15).andHeightGreaterThan(165)
                .example());

        StudentExample studentExample = new StudentExample();
        studentExample.createCriteria().andDeleted(false).andAgeLessThanOrEqualTo(15).andHeightGreaterThan(165);
        List<Student> students1 = studentMapper.selectByExample(studentExample);
        Assert.assertEquals(students,students1);
    }
}