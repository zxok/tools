package com.hzx.tools.utilr;
import lombok.Data;

/**
 * @author Hzx
 */
@Data
public class ResponseEntity<T> {
    private int status;
    private String message;
    private String pit;
    private T data;

    public static <T> ResponseEntity<T> error(StatusCode statusCode) {
        ResponseEntity<T> entity = new ResponseEntity<>();
        entity.setStatus(statusCode.getStatus());
        entity.setMessage(statusCode.getMessage());
        entity.setPit(statusCode.getPit());
        return entity;
    }

    public static <T> ResponseEntity<T> error(int status, String msg, String pit) {
        ResponseEntity<T> entity = new ResponseEntity<>();
        entity.setStatus(status);
        entity.setMessage(msg);
        entity.setPit(pit);
        return entity;
    }

}
