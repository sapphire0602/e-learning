package com.ahmad.e_learning.service.enrollment;

import com.ahmad.e_learning.model.Enrollment;

import java.util.List;

public interface IEnrollmentService {
    List<Enrollment> getAllEnrollments();
    Enrollment  getEnrollmentsByCourseId(Long courseId);
    Enrollment getEnrollmentByStudentId(Long studentId);
    boolean isStudentEnrolled(Long courseId , Long studentId);
    Enrollment enrollStudent (Long courseId , Long studentId);
    void unEnrollStudent (Long courseId , Long studentId);
}
