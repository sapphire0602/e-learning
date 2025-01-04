package com.ahmad.e_learning.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class Enrollment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate enrollmentDate = LocalDate.now();

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "student_id")
    private User student;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Enrollment(Long id, LocalDate enrollmentDate, User student, Course course) {
        this.id = id;
        this.enrollmentDate = enrollmentDate;
        this.student = student;
        this.course = course;
    }

    public Enrollment() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getEnrollmentDate() {
        return enrollmentDate;
    }

    public void setEnrollmentDate(LocalDate enrollmentDate) {
        this.enrollmentDate = enrollmentDate;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
