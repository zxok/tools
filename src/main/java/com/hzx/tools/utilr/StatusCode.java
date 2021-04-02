package com.hzx.tools.utilr;

import lombok.Getter;

/**
 * @program: exception
 * @author: hzx
 * @since: JDK 1.8
 * @create: 2020-11-05 19:38
 **/
@Getter
public enum StatusCode {

    SUCCESS(200,"success","成功"),
    ERROR(404,"error","失败"),
    UNKNOWN_ERROR(409,"An unknown error","未知错误");

    private Integer status;
    private String message;
    private String pit;

    StatusCode(int status, String message, String pit) {
        this.status = status;
        this.message = message;
        this.pit = pit;
    }
}
