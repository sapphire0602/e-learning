package com.ahmad.e_learning.service.course;

import com.ahmad.e_learning.dto.CourseDto;
import com.ahmad.e_learning.model.Course;
import com.ahmad.e_learning.model.User;
import com.ahmad.e_learning.request.AddCourseRequest;
import com.ahmad.e_learning.request.CourseUpdateRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ICourseService {
    Course addCourse(AddCourseRequest request);

    void uploadFileForCourse(MultipartFile file, Long courseId);

    List<Course> getCourseByInstructor(Long instructorId);
    List<Course> getAllCourses();
    Course getCourseById(Long courseId);
    Course updateCourse(CourseUpdateRequest updatedCourse , Long courseId);
    void deleteCourse(Long courseId);

    List<CourseDto> getConvertedCourses(List<Course> courses);

    CourseDto convertToDto(Course course);
}
