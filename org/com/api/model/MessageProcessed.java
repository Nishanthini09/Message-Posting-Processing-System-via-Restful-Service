package org.com.api.model;

public class MessageProcessed {
    private int userId;
    private String content;
    private String messageType;
    private long processedTime;

    public MessageProcessed(int userId, String content, String messageType, long processedTime) {
        this.userId = userId;
        this.content = content;
        this.messageType = messageType;
        this.processedTime = processedTime;
    }

    public int getUserId() {
        return userId;
    }
    public String getContent() {
        return content;
    }
    public String getMessageType() {
        return messageType;
    }
    public long getProcessedTime() {
        return processedTime;
    }
}
