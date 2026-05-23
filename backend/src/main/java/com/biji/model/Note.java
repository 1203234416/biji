package com.biji.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notes")
public class Note {
    @Id
    private String id;

    private String userId;

    private String title;

    @Column(columnDefinition = "CLOB")
    private String content;

    private LocalDateTime createTime;
    private LocalDateTime updateTime;

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

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public LocalDateTime getCreateTime() { return createTime; }
    public void setCreateTime(LocalDateTime createTime) { this.createTime = createTime; }

    public LocalDateTime getUpdateTime() { return updateTime; }
    public void setUpdateTime(LocalDateTime updateTime) { this.updateTime = updateTime; }

    public String getCreateTimeStr() {
        return createTime != null ? createTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "";
    }

    public String getUpdateTimeStr() {
        return updateTime != null ? updateTime.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")) : "";
    }
}
