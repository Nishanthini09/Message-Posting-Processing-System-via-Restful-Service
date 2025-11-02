package org.com.api.model;

public class MessageSub {
    private int id;
    private int userId;
    private String content;
    private boolean priority;
    private long postedTime;

    public MessageSub(int id, int userId, String content, boolean priority, long postedTime) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.priority = priority;
        this.postedTime = postedTime;
    }

    public int getId() {
        return id;
    }
    public int getUserId() {
        return userId;
    }
    public String getContent() {
        return content;
    }
    public boolean isPriority() {
        return priority;
    }
    public long getPostedTime() {
        return postedTime;
    }
}
