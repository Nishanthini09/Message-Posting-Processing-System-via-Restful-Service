package org.com.api.model;

public class ScheduledJob {
    private int jobId;
    private String type;
    private String scheduledTime;

    public ScheduledJob() {}

    public ScheduledJob(int jobId, String type, String scheduledTime) {
        this.jobId = jobId;
        this.type = type;
        this.scheduledTime = scheduledTime;
    }

    public int getJobId() {
        return jobId;
    }
    public void setJobId(int jobId) {
        this.jobId = jobId;
    }

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getScheduledTime() {
        return scheduledTime;
    }
    public void setScheduledTime(String scheduledTime) {
        this.scheduledTime = scheduledTime;
    }
}
