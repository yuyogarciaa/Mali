package com.example.mali;

public class Task {

    private String id, title, Desciption, status_id, subject, start_task, end_task, responsible, username, project;

    public Task() {
    }

    public Task(String id, String title, String desciption, String status_id, String subject, String start_task, String end_task, String responsible, String username, String project) {
        this.id = id;
        this.title = title;
        this.Desciption = desciption;
        this.status_id = status_id;
        this.subject = subject;
        this.start_task = start_task;
        this.end_task = end_task;
        this.responsible = responsible;
        this.username = username;
        this.project = project;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDesciption() {
        return Desciption;
    }

    public void setDesciption(String desciption) {
        Desciption = desciption;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getStart_task() {
        return start_task;
    }

    public void setStart_task(String start_task) {
        this.start_task = start_task;
    }

    public String getEnd_task() {
        return end_task;
    }

    public void setEnd_task(String end_task) {
        this.end_task = end_task;
    }

    public String getResponsible() {
        return responsible;
    }

    public void setResponsible(String responsible) {
        this.responsible = responsible;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }
}
