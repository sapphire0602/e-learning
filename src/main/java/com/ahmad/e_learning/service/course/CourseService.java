package com.ahmad.e_learning.service.course;

import com.ahmad.e_learning.dto.CourseDto;
import com.ahmad.e_learning.exception.ResourceNotFoundException;
import com.ahmad.e_learning.model.Course;
import com.ahmad.e_learning.model.User;
import com.ahmad.e_learning.repository.CourseRepository;
import com.ahmad.e_learning.repository.UserRepository;
import com.ahmad.e_learning.request.AddCourseRequest;
import com.ahmad.e_learning.request.CourseUpdateRequest;
import com.ahmad.e_learning.service.file.FileService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class CourseService implements ICourseService {
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private FileService fileService;

    public CourseService(CourseRepository courseRepository, ModelMapper modelMapper, UserRepository userRepository) {
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
        this.userRepository = userRepository;
    }

    @Override
    public Course addCourse(AddCourseRequest request) {
        User instructor = userRepository.findById(request.getInstructorId())
                .orElseThrow(() -> new ResourceNotFoundException("INSTRUCTOR NOT FOUND !"));
        return courseRepository.save(createCourse(request, instructor));
    }

    private Course createCourse(AddCourseRequest request, User instructor) {
        return new Course(
                request.getTitle(),
                request.getDescription(),
                request.getDuration(),
                instructor
        );
    }

    @Override
    public void uploadFileForCourse(MultipartFile file , Long courseId) {
//        Course course = courseRepository.findById(courseId)
//                .orElseThrow(() -> new ResourceNotFoundException("Course Not Found!"));
        fileService.saveFile(file , courseId);
    }

    @Override
    public List<Course> getCourseByInstructor(Long instructorId) {
        return courseRepository.findByInstructorId(instructorId);
    }

    @Override
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @Override
    public Course getCourseById(Long courseId) {
        return courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Unable to get course with Id " + courseId));
    }

    @Override
    public Course updateCourse(CourseUpdateRequest updatedCourse, Long courseId) {
        return courseRepository.findById(courseId).map(existingCourse ->
        {
            existingCourse.setTitle(updatedCourse.getTitle());
            existingCourse.setDescription(updatedCourse.getDescription());
            existingCourse.setDuration(updatedCourse.getDuration());

            return courseRepository.save(existingCourse);
        }).orElseThrow(() -> new ResourceNotFoundException("UNABLE TO UPDATE COURSE " + courseId + " , COURSE DOESN'T EXIST"));
    }

    @Override
    public void deleteCourse(Long courseId) {
        courseRepository.findById(courseId).ifPresentOrElse(courseRepository::delete,
                () -> new ResourceNotFoundException("Unable to delete course , course ," + courseId + " doesn't exist"));
    }

    @Override
    public List<CourseDto> getConvertedCourses(List<Course> courses) {
        return courses.stream().map(this::convertToDto).toList();
    }

    @Override
    public CourseDto convertToDto(Course course) {
        CourseDto courseDto = modelMapper.map(course, CourseDto.class);
        return courseDto;
    }
}
