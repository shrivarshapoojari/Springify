package com.shri.springify.Springify.response;

import lombok.Data;

@Data
public class AddItemRequest {
    private  String size;
    private int quantity;
    private  Long productId;
}
