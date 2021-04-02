package com.hzx.tools.util;

import lombok.Getter;

/**
 * @author Hzx
 */

@Getter
public enum StatusData {

    SUCCESS(200, "success","成功"),
    SYS_ERROR(401, "system exception","系统异常"),
    SERVICE_ERROR(404, "Service exceptions","服务异常");
    private int status;
    private String message;
    private String pit;

    StatusData(int status, String message, String pit) {
        this.status = status;
        this.message = message;
        this.pit = pit;
    }

}
