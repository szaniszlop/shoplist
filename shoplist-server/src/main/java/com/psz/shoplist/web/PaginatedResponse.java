package com.psz.shoplist.web;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class PaginatedResponse<T> {
    List<T> response;
    String offset;
    String limit;
    
}
