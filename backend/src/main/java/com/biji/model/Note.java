package com.biji.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Note {
    private String id;
    private String title;
    private String content;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public Note() {}

    public Note(String title, String content) {
        this.title = title;
        this.content = content;
        this.createTime = LocalDateTime.now();
        this.updateTime = LocalDateTime.now();
    }

    public Note(String id, String title, String content, LocalDateTime createTime, LocalDateTime updateTime) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }

    public String getCreateTimeStr() {
        return createTime != null ? createTime.format(FORMATTER) : "";
    }

    public String getUpdateTimeStr() {
        return updateTime != null ? updateTime.format(FORMATTER) : "";
    }
}
