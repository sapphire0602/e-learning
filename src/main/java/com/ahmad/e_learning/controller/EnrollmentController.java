package com.ahmad.e_learning.controller;

import com.ahmad.e_learning.exception.ResourceNotFoundException;
import com.ahmad.e_learning.model.Enrollment;
import com.ahmad.e_learning.response.ApiResponse;
import com.ahmad.e_learning.service.enrollment.EnrollmentService;
import com.ahmad.e_learning.service.enrollment.IEnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;
@RestController
@RequestMapping("/api/v1")
public class EnrollmentController {

    private final IEnrollmentService enrollmentService;

    public EnrollmentController(IEnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @Secured({"ADMIN" , "INSTRUCTOR"})
    @GetMapping("/enroll/all")
    public ResponseEntity<ApiResponse> getAllEnrollments() {
        try {
            List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
            return ResponseEntity.ok(new ApiResponse("ENROLLMENTS RETRIEVED SUCCESSFULLY !", enrollments));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("UNABLE TO RETRIEVE USERS !", null));
        }
    }

    @Secured({"ADMIN" , "INSTRUCTOR"})
    @GetMapping("/enroll/{courseId}/courseId")
    public ResponseEntity<ApiResponse> getEnrollmentsByCourseId(@PathVariable Long courseId) {
        try {
            Enrollment enrollment = enrollmentService.getEnrollmentsByCourseId(courseId);
            return ResponseEntity.ok(new ApiResponse("ENROLLMENT RETRIEVED SUCCESSFULLY !", enrollment));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("UNABLE TO RETRIEVE ENROLLMENTS!", null));
        }
    }

//    @PreAuthorize("hasRole('STUDENT')")
    @Secured({"ADMIN" , "INSTRUCTOR"})
    @GetMapping("/enroll/{studentId}/studentId")
    public ResponseEntity<ApiResponse> getEnrollmentByStudentId(@PathVariable Long studentId){
        try {
            Enrollment enrollment = enrollmentService.getEnrollmentByStudentId(studentId);
            return ResponseEntity.ok(new ApiResponse("ENROLLMENT RETRIEVED SUCCESSFULLY !" , enrollment));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("UNABLE TO RETRIEVE USER!", null));
        }
    }

    @GetMapping("/course/{courseId}/enroll/{studentId}")
    public ResponseEntity<ApiResponse> isStudentEnrolled(@PathVariable Long courseId , @PathVariable Long studentId) {
        try {
            boolean enrollment = enrollmentService.isStudentEnrolled(courseId, studentId);
            if (enrollment)
                return ResponseEntity.ok(new ApiResponse("STUDENT IS  ENROLLED IN THE COURSE !", enrollment));
            else
                return ResponseEntity.ok(new ApiResponse("STUDENT IS NOT ENROLLED FOR THE COURSE !" ,null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("USER IS NOT ENROLLED IN THE COURSE !" ,null));
        }
    }

    @PostMapping("/course/{courseId}/student/{studentId}/enroll")
    public ResponseEntity<ApiResponse> enrollStudent(@PathVariable Long courseId , @PathVariable Long studentId){
        try {
            Enrollment enrollment = enrollmentService.enrollStudent(courseId, studentId);
                return ResponseEntity.ok(new ApiResponse("USER ENROLLED SUCCESSFULLY !" , enrollment));

        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage() , null));
        }
    }

    @Secured({"ADMIN" , "INSTRUCTOR"})
    @DeleteMapping("/un-enroll/course/{courseId}/student/{studentId}/delete")
    public ResponseEntity<ApiResponse> unEnrollStudent(@PathVariable Long courseId , @PathVariable Long studentId){
        try {
            enrollmentService.unEnrollStudent(courseId, studentId);
            return ResponseEntity.ok(new ApiResponse("USER UN-ENROLLED SUCCESSFULLY !" , null ));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage() , null));
        }

    }
}

