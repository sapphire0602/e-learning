package com.ahmad.e_learning.repository;

import com.ahmad.e_learning.model.Course;
import com.ahmad.e_learning.model.Enrollment;
import com.ahmad.e_learning.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository extends JpaRepository<Enrollment , Long> {
    boolean existsByCourseIdAndStudentId(Long courseId , Long studentId);
    Enrollment findByStudentId(Long studentId);
    Enrollment findByCourseId(Long courseId);
    Optional<Enrollment> findByCourseAndStudent(Course course , User student);
}
