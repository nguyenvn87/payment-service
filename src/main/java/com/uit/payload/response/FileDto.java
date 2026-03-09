package com.uit.payload.response;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FileDto {
    @NotBlank
    private String id;
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String summary;

}
