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
    public void setUp() {
        List<Student> list = Lists.newArrayList();
        Random random = new Random(System.currentTimeMillis());
        for (int i = 0; i < 100; ++i) {
            //builder构造po
            Student student = Student.builder().name("haha" + i).age(5 + Math.abs(random.nextInt() % 80))
                    .isDeleted(i % 2 == 0).rank(Math.abs(random.nextInt() % 5000))
                    .uuid(UUID.randomUUID().toString().substring(0, 20)).height(110 + Math.abs(random.nextInt() % 100))
                    .build();
            list.add(student);
        }
        studentMapper.batchInsertSelective(list, Student.Column.name, Student.Column.age, Student.Column.isDeleted,
                Student.Column.uuid, Student.Column.height, Student.Column.rank);
        Assert.assertTrue(studentMapper.countByExample(new StudentExample()) == 100);
    }

    /**
     * 通过criteria可以得到example，方便链式调用
     */
    @Test
    public void prettyExampleTest() {
        //选取所有未删除、15岁以下身高大于165
        List<Student> students = studentMapper.selectByExample(new StudentExample().createCriteria().andDeleted(false)
                .andAgeLessThanOrEqualTo(15).andHeightGreaterThan(165)
                .example());

        StudentExample studentExample = new StudentExample();
        studentExample.createCriteria().andDeleted(false).andAgeLessThanOrEqualTo(15).andHeightGreaterThan(165);
        List<Student> students1 = studentMapper.selectByExample(studentExample);
        Assert.assertEquals(students, students1);
    }

    @Test
    public void conditionSelect() {
        boolean condition = new Random(System.currentTimeMillis()).nextBoolean();
        //new
        List<Student> students = studentMapper.selectByExample(new StudentExample().createCriteria().andDeleted(false)
                .andAgeLessThanOrEqualTo(15).andIf(condition, add -> add.andHeightGreaterThan(160))
                .example());

        //old
        StudentExample studentExample = new StudentExample();
        StudentExample.Criteria criteria = studentExample.createCriteria();
        criteria.andDeleted(false).andAgeLessThanOrEqualTo(15);
        if (condition) {
            criteria.andHeightGreaterThan(160);
        }
        List<Student> students1 = studentMapper.selectByExample(studentExample);
        Assert.assertEquals(students, students1);
    }

    @Test
    public void logicalDeleteTest() {
        List<Student> list = allNotDeletedStudent();
        int i = studentMapper.logicalDeleteByExample(new StudentExample().createCriteria().andDeleted(false).example());
        Assert.assertTrue(list.size() == i && i > 0);
        list = allNotDeletedStudent();
        Assert.assertTrue(list.isEmpty());
        int number = studentMapper.updateByExampleSelective(Student.builder().isDeleted(false).build(), new StudentExample().createCriteria().andDeleted(true).example());
        Assert.assertTrue(number == 100);
        list = allNotDeletedStudent();
        Assert.assertTrue(list.size() == 100);
    }

    @Test
    public void incrementsColumnsTest() {
        int delta = -10;
        List<Student> list = allNotDeletedStudent();
        Student student = list.get(0);
        Integer rank = student.getRank();
        Student student1 = Student.builder().id(student.getId()).rank(delta, Student.Builder.Inc.INC).build();
        studentMapper.updateByPrimaryKeySelective(student1);
        Integer rank1 = studentMapper.selectByPrimaryKey(student.getId()).getRank();
        Assert.assertTrue(rank + delta == rank1);
    }

    private List<Student> allNotDeletedStudent() {
        List<Student> list = studentMapper.selectByExample(new StudentExample().createCriteria().andDeleted(false).example());
        return list;
    }
}