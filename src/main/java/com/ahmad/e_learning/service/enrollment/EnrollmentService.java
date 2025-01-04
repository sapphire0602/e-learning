package com.ahmad.e_learning.service.enrollment;

import com.ahmad.e_learning.dto.CourseDto;
import com.ahmad.e_learning.enums.UserRoles;
import com.ahmad.e_learning.exception.ResourceNotFoundException;
import com.ahmad.e_learning.exception.RoleNotFoundException;
import com.ahmad.e_learning.model.Course;
import com.ahmad.e_learning.model.Enrollment;
import com.ahmad.e_learning.model.Role;
import com.ahmad.e_learning.model.User;
import com.ahmad.e_learning.repository.CourseRepository;
import com.ahmad.e_learning.repository.EnrollmentRepository;
import com.ahmad.e_learning.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EnrollmentService implements IEnrollmentService {
    private final EnrollmentRepository enrollmentRepository;
    private final CourseRepository courseRepository;
    private final UserRepository userRepository;

    public EnrollmentService(EnrollmentRepository enrollmentRepository, CourseRepository courseRepository, UserRepository userRepository) {
        this.enrollmentRepository = enrollmentRepository;
        this.courseRepository = courseRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<Enrollment> getAllEnrollments() {
        return enrollmentRepository.findAll();
    }

    @Override
    public Enrollment getEnrollmentsByCourseId(Long courseId) {
        return enrollmentRepository.findByCourseId(courseId);
    }

    @Override
    public Enrollment getEnrollmentByStudentId(Long studentId) {
        return enrollmentRepository.findByStudentId(studentId);
    }

    @Override
    public boolean isStudentEnrolled(Long courseId, Long studentId) {
        return enrollmentRepository.existsByCourseIdAndStudentId(courseId , studentId);
    }

    @Override
    public Enrollment enrollStudent(Long courseId, Long studentId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course doesn't exist!"));
        User student = userRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student doesn't exist"));

        boolean isStudent = student.getRoles().stream().anyMatch(role -> role.getName().equals(UserRoles.STUDENT));
        if (!isStudent ){
            throw  new RoleNotFoundException("User is not a student , please , sign in as a student to get access ro the course");
        }

        if (enrollmentRepository.existsByCourseIdAndStudentId(courseId , studentId)){
            throw new RoleNotFoundException("USER ALREADY ENROLLED IN THE COURSE");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setCourse(course);
        enrollment.setStudent(student);

        return enrollmentRepository.save(enrollment);
    }

    @Override
    public void unEnrollStudent(Long courseId, Long studentId){
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course doesn't exist!"));
        User student = userRepository.findById(studentId).orElseThrow(() -> new ResourceNotFoundException("Student doesn't exist"));

        Enrollment enrollment = enrollmentRepository.findByCourseAndStudent(course , student).orElseThrow(() -> new ResourceNotFoundException("Course enrollment not found"));
        enrollmentRepository.delete(enrollment);
    }


}
