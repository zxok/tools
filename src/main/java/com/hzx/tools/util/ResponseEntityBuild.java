package com.hzx.tools.util;

import lombok.Builder;
import lombok.Data;

/**
 * @program: day5-ssm
 * @author: hzx
 * @since: JDK 1.8
 * @create: 2020-10-31 11:36
 **/
@Data
@Builder
public class ResponseEntityBuild<T> {

    private Integer status;
    private String message;
    private String pit;
    private T data;

    public static <T> ResponseEntityBuild<T> success(T data) {
        return (ResponseEntityBuild<T>) ResponseEntityBuild.builder()
                .data(data)
                .status(StatusData.SUCCESS.getStatus())
                .message(StatusData.SUCCESS.getMessage())
                .pit(StatusData.SUCCESS.getPit())
                .build();
    }

    public static <T> ResponseEntityBuild<T> error() {
        return (ResponseEntityBuild<T>) ResponseEntityBuild.builder()
                .status(StatusData.SERVICE_ERROR.getStatus())
                .message(StatusData.SERVICE_ERROR.getMessage())
                .pit(StatusData.SERVICE_ERROR.getPit())
                .build();
    }

    public static <T> ResponseEntityBuild<T> error(Integer status, String message, String pit) {
        return (ResponseEntityBuild<T>) ResponseEntityBuild.builder()
                .status(status)
                .message(message)
                .pit(pit)
                .build();
    }

}
