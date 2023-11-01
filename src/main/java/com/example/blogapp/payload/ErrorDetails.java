package com.example.blogapp.payload;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ErrorDetails {
    private Date timesDate;
    private String message;
    private String details;
}
