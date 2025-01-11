package com.ahmad.e_learning.controller;

import com.ahmad.e_learning.dto.CourseDto;
import com.ahmad.e_learning.exception.ResourceNotFoundException;
import com.ahmad.e_learning.model.Course;
import com.ahmad.e_learning.request.AddCourseRequest;
import com.ahmad.e_learning.request.CourseUpdateRequest;
import com.ahmad.e_learning.response.ApiResponse;
import com.ahmad.e_learning.service.course.CourseService;
import com.ahmad.e_learning.service.course.ICourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("/api/v1")
public class CourseController {
    private final ICourseService courseService;

    public CourseController(ICourseService courseService) {
        this.courseService = courseService;
    }

//    @PreAuthorize("hasAuthority('INSTRUCTOR' , 'ADMIN')")
    @PostMapping("/course/add")
    public ResponseEntity<ApiResponse> addCourse(@RequestBody AddCourseRequest request){
        try {
            Course course = courseService.addCourse(request);
            CourseDto courseDto = courseService.convertToDto(course);
            return ResponseEntity.ok(new ApiResponse("COURSE CREATED SUCCESSFULLY ! " , courseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_IMPLEMENTED).body(new ApiResponse(e.getMessage() + "UNABLE TO CREATE COURSE !" , null));
        }
    }

    @GetMapping("/instructor/{instructorId}/course")
    public ResponseEntity<ApiResponse>  getCourseByInstructor(@PathVariable Long instructorId){
        try {
            List<Course> course = courseService.getCourseByInstructor(instructorId);
            List<CourseDto> courseDto = courseService.getConvertedCourses(course);
            return ResponseEntity.ok(new ApiResponse("COURSES BY INSTRUCTOR : " + instructorId + " RETRIEVED SUCCESSFULLY ! " , courseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse("ERROR RETRIEVING COURSE BY INSTRUCTOR : " + instructorId  , null));
        }
    }

    @GetMapping("/course/all")
    public ResponseEntity<ApiResponse> getAllCourses() {
        try {
            List<Course> course = courseService.getAllCourses();
            List<CourseDto> courseDto = courseService.getConvertedCourses(course);
            return ResponseEntity.ok(new ApiResponse("COURSES RETRIEVED SUCCESSFULLY !" , courseDto));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR).body(new ApiResponse("ERROR RETRIEVING COURSES !" , null));
        }
    }

    @GetMapping("/course/{courseId}/courseId")
    public ResponseEntity<ApiResponse> getCourseById(@PathVariable Long courseId) {
        try {
            Course course = courseService.getCourseById(courseId);
            CourseDto courseDto = courseService.convertToDto(course);
            return ResponseEntity.ok(new ApiResponse("COURSE WITH ID : " + courseId + "  , RETRIEVED SUCCESSFULLY !" , courseDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(" UNABLE TO RETRIEVE COURSE " + e.getMessage()  , null));
        }
    }

    //@PreAuthorize("hasRole('INSTRUCTOR')")
    @PutMapping("/course/{courseId}/update")
    public ResponseEntity<ApiResponse> updateCourseById(@RequestBody CourseUpdateRequest request , @PathVariable Long courseId){
        try {
            Course course = courseService.updateCourse(request, courseId );
            CourseDto courseDto = courseService.convertToDto(course);
            return ResponseEntity.ok(new ApiResponse("COURSE UPDATED SUCCESSFULLY !" , courseDto));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
        }
    }

    //@PreAuthorize("hasAnyRole('ADMIN' , 'INSTRUCTOR')")
    @DeleteMapping("/course/{courseId}/delete")
    public ResponseEntity<ApiResponse> deleteCourseById(@PathVariable Long courseId){
        try {
            courseService.deleteCourse(courseId);
            return ResponseEntity.ok(new ApiResponse("COURSE SUCCESSFULLY DELETED !" , null));
        } catch (Exception e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage() , null));
        }
    }


}
