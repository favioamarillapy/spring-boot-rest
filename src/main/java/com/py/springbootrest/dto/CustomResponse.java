package com.py.springbootrest.dto;

import com.py.springbootrest.model.UserApp;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomResponse<T> {
    private String message;
    private boolean error;
    private T data;

}