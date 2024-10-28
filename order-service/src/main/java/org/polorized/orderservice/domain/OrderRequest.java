package org.polorized.orderservice.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderRequest {
    @NotBlank(message = "The book ISBN must be defined")
    private String isbn;

   @NotNull(message = "The book must have a quantity")
   @Min(value = 1, message = "You must order at least 1 item")
   @Max(value = 5, message = "You cannot order more than 5 items")
    private int quantity;
}
