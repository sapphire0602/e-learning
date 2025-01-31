package com.ahmad.e_learning.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Set;


@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;
    private int duration;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FileData> fileData;

    @ManyToOne
    @JoinColumn(name = "instructor_id")
    private User instructor;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Enrollment> enrollments;

    public Course(Long id, String title, String description, int duration, User instructor, Set<Enrollment> enrollments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.instructor = instructor;
        this.enrollments = enrollments;
    }


    public Course() {
    }

    public Course(Long id, String title, String description, int duration, List<FileData> fileData, User instructor, Set<Enrollment> enrollments) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.fileData = fileData;
        this.instructor = instructor;
        this.enrollments = enrollments;
    }

    public Course(String title, String description, int duration, User instructor) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.instructor = instructor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public User getInstructor() {
        return instructor;
    }

    public void setInstructor(User instructor) {
        this.instructor = instructor;
    }

    public Set<Enrollment> getEnrollments() {
        return enrollments;
    }

    public void setEnrollments(Set<Enrollment> enrollments) {
        this.enrollments = enrollments;
    }

    public List<FileData> getFileData() {
        return fileData;
    }

    public void setFileData(List<FileData> fileData) {
        this.fileData = fileData;
    }
}
