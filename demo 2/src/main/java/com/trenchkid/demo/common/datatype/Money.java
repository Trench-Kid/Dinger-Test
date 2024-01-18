package com.trenchkid.demo.common.datatype;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Embeddable
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Money {

    @Column(name = "amount")
    @NotNull
    @Positive(message = "Amount must be a positive value")
    private BigDecimal amount;
    @Column(name = "currency")
    @NotBlank
    private String currency;

}
