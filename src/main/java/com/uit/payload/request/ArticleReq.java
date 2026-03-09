package com.uit.payload.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleReq {
    @NotBlank
    private String id;
    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotBlank
    private String summary;

    @NotBlank
    private String groupValue;

    @NotBlank
    private String urlAvatar;

    private Float price;

    public ArticleReq(){
        this.price = Float.valueOf(0);
    }
}
