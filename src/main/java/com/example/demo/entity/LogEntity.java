package com.example.demo.entity;


import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class LogEntity {
    private String date;
    private String time;
    private String type;
    private String level;
    private String _class;
    private String message;
}

