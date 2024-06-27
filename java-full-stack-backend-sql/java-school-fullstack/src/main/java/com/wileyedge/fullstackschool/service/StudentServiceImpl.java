package com.wileyedge.fullstackschool.service;

import com.wileyedge.fullstackschool.dao.StudentDao;
import com.wileyedge.fullstackschool.model.Course;
import com.wileyedge.fullstackschool.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentServiceInterface {

    //YOUR CODE STARTS HERE
    @Autowired
    StudentDao studentDao;

    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Autowired
    CourseServiceImpl courseService;



    //YOUR CODE ENDS HERE

    public List<Student> getAllStudents() {
        //YOUR CODE STARTS HERE

        return studentDao.getAllStudents();

        //YOUR CODE ENDS HERE
    }

    public Student getStudentById(int id) {
        //YOUR CODE STARTS HERE

        try {
            return studentDao.findStudentById(id);
        } catch (DataAccessException e) {
            Student student = new Student();
            student.setStudentFirstName("Student Not Found");
            student.setStudentLastName("Student Not Found");
            return student;
        }

        //YOUR CODE ENDS HERE
    }

    public Student addNewStudent(Student student) {
        //YOUR CODE STARTS HERE

        boolean firstNameBlank = false;
        boolean lastNameBlank = false;

        if (student.getStudentFirstName() == null || student.getStudentFirstName().trim().isEmpty()) {
            student.setStudentFirstName("First Name blank, student NOT added");
            firstNameBlank = true;
        }
        if (student.getStudentLastName() == null || student.getStudentLastName().trim().isEmpty()) {
            student.setStudentLastName("Last Name blank, student NOT added");
            lastNameBlank = true;
        }

        if (firstNameBlank || lastNameBlank) {
            return student;
        } else {
            return studentDao.createNewStudent(student);
        }

        //YOUR CODE ENDS HERE
    }

    public Student updateStudentData(int id, Student student) {
        //YOUR CODE STARTS HERE

        if (id != student.getStudentId()) {
            student.setStudentFirstName("IDs do not match, student not updated");
            student.setStudentLastName("IDs do not match, student not updated");
        } else {
            studentDao.updateStudent(student);
        }
        return student;

        //YOUR CODE ENDS HERE
    }

    public void deleteStudentById(int id) {
        //YOUR CODE STARTS HERE

        studentDao.deleteStudent(id);

        //YOUR CODE ENDS HERE
    }

    public void deleteStudentFromCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE

        Student student = getStudentById(studentId);
        Course course = courseService.getCourseById(courseId);

        if ("Student Not Found".equals(student.getStudentFirstName()) || "Course Not Found".equals(course.getCourseName())) {
            if ("Student Not Found".equals(student.getStudentFirstName())) {
                System.out.println("Student not found");
            }
            if ("Course Not Found".equals(course.getCourseName())) {
                System.out.println("Course not found");
            }
        } else {
            studentDao.deleteStudentFromCourse(studentId, courseId);
            System.out.println("Student: " + studentId + " deleted from course: " + courseId);
        }

        //YOUR CODE ENDS HERE
    }

    public void addStudentToCourse(int studentId, int courseId) {
        //YOUR CODE STARTS HERE

        try {
            studentDao.addStudentToCourse(studentId, courseId);
            System.out.println("Student: " + studentId + " added to course: " + courseId);
        } catch (DataIntegrityViolationException e) {
            System.out.println("Student: " + studentId + " already enrolled in course: " + courseId);
        }


        //YOUR CODE ENDS HERE
    }
}
