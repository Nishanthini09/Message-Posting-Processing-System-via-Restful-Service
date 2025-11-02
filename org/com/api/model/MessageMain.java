package org.com.api.model;

public class MessageMain {
    private int messageId;
    private int userId;
    private int typeId;
    private String assignedSubTable;
    private long postedTime;

    public MessageMain() {}

    public MessageMain(int messageId, int userId, int typeId, String assignedSubTable, long postedTime) {
        this.messageId = messageId;
        this.userId = userId;
        this.typeId = typeId;
        this.assignedSubTable = assignedSubTable;
        this.postedTime = postedTime;
    }

    public int getMessageId() {
        return messageId;
    }
    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTypeId() {
        return typeId;
    }
    public void setTypeId(int typeId) {
        this.typeId = typeId;
    }

    public String getAssignedSubTable() {
        return assignedSubTable;
    }
    public void setAssignedSubTable(String assignedSubTable) {
        this.assignedSubTable = assignedSubTable;
    }

    public long getPostedTime() {
        return postedTime;
    }
    public void setPostedTime(long postedTime) {
        this.postedTime = postedTime;
    }
}
