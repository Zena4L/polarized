package org.polorized.orderservice.domain;

import lombok.Builder;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.Instant;

@Builder
@Table("orders")
public record Order(@Id int id, String bookIsbn, String bookName, int quantity, BigDecimal bookPrice,
                    OrderStatus orderStatus,

                    @CreatedDate Instant createdDate,

                    @LastModifiedDate Instant modifiedDate,

                    @Version int version) {

}
