package com.trenchkid.demo.dto;

import com.trenchkid.demo.common.datatype.Money;
import com.trenchkid.demo.model.Housing;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HousingDTO {
    private Long id;
    @NotBlank
    private String housingName;
    @NotBlank
    private String address;
    @NotNull
    @Min(value = 1, message = "Number of floors must be at least 1")
    private int numberOfFloors;
    @NotNull
    @Min(value = 1, message = "Number of master rooms must be at least 1")
    private int numberOfMasterRoom;
    @NotNull
    @Min(value = 1, message = "Number of single rooms must be at least 1")
    private int numberOfSingleRoom;

    @Valid
    private Money amount;
    private Long ownerId;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;

    public Housing toHousing() {
        return Housing.builder()
                .id(id)
                .housingName(housingName)
                .address(address)
                .numberOfFloors(numberOfFloors)
                .numberOfMasterRoom(numberOfMasterRoom)
                .numberOfSingleRoom(numberOfSingleRoom)
                .ownerId(ownerId)
                .amount(amount)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();
    }
}
