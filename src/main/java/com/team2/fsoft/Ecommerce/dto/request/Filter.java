package com.team2.fsoft.Ecommerce.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Filter {
    public String text;

    public Date created;

    public  String author;


    public  String categoryCode;

    public  boolean ascending = false;

    public String orderBy;

}
