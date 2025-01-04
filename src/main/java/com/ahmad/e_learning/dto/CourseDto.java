package com.ahmad.e_learning.dto;

import com.ahmad.e_learning.model.User;

public class CourseDto {
    private String title;
    private String description;
    private int duration;
    private Long instructorId;


    public CourseDto(String title, String description, int duration , Long instructorId) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.instructorId = instructorId;
    }

    public CourseDto() {
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
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
}
