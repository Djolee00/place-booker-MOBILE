package com.placebooker.dto.fs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@Setter
public class FileServiceResponse {
    private String message;
    private boolean isSuccessful;
    private int statusCode;
    private Object data;
}
