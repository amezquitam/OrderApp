package ford.group.orderapp.exception;

import java.time.LocalDateTime;

public class ErrorDetails {
    LocalDateTime threwAt;
    String description;
    String path;
    String errorCode;

    public ErrorDetails(LocalDateTime threwAt, String description, String path, String errorCode) {
        this.threwAt = threwAt;
        this.description = description;
        this.path = path;
        this.errorCode = errorCode;
    }

    public LocalDateTime getThrewAt() {
        return threwAt;
    }

    public void setThrewAt(LocalDateTime threwAt) {
        this.threwAt = threwAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }


}
