package com.ahmad.e_learning.request;

public class CourseUpdateRequest {
    private String title;
    private String description;
    private int duration;
    private Long instructorId;

    public CourseUpdateRequest(String title, String description, int duration , Long instructorId) {
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.instructorId = instructorId;
    }

    public Long getInstructorId() {
        return instructorId;
    }

    public void setInstructorId(Long instructorId) {
        this.instructorId = instructorId;
    }

    public CourseUpdateRequest() {
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
