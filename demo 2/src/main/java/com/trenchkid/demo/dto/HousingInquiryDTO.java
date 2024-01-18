package com.trenchkid.demo.dto;

import com.trenchkid.demo.common.datatype.Money;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HousingInquiryDTO {

    private Long id;
    private Long ownerId;
    private String housingName;
    private Integer numberOfFloors;
    private Integer numberOfMasterRooms;
    private Integer numberOfSingleRooms;
    private Money amount;
    private LocalDateTime searchStartDate;
    private LocalDateTime searchEndDate;
    private Pageable paging;
}
