package com.uit.model;

import lombok.Data;

@Data
public class Failure implements Type {
    private final String msg;
}