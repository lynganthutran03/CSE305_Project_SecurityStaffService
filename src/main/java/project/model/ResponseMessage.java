package project.model;

public class ResponseMessage {
    private String message;
    private int leaveId;

    public ResponseMessage(String message, int leaveId) {
        this.message = message;
        this.leaveId = leaveId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getLeaveId() {
        return leaveId;
    }

    public void setLeaveId(int leaveId) {
        this.leaveId = leaveId;
    }
}