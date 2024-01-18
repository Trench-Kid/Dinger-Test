package com.trenchkid.demo.controller;

import com.trenchkid.demo.common.datatype.Money;
import com.trenchkid.demo.common.util.CommonConstants;
import com.trenchkid.demo.dto.HousingDTO;
import com.trenchkid.demo.dto.HousingInquiryDTO;
import com.trenchkid.demo.model.Housing;
import com.trenchkid.demo.model.Owner;
import com.trenchkid.demo.service.HousingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/owner")
@RequiredArgsConstructor
public class OwnerController {

    private final HousingService housingService;

    @PostMapping("/housing/create")
    public ResponseEntity<Housing> createHousing(
            @Valid @RequestBody HousingDTO housingDTO
    ) {
        Housing housing = housingService.createHousing(housingDTO);
        return ResponseEntity.ok(housing);
    }

    @PutMapping("/housing/edit/{id}")
    public ResponseEntity<Housing> editHousing(
            @Valid @RequestBody HousingDTO housingDTO,
            @PathVariable Long id
    ) {
        var updatedHousing = housingService.updateHousing(id, housingDTO);
        return ResponseEntity.ok(updatedHousing);
    }

    @GetMapping("/housing/{id}")
    public ResponseEntity<Housing> findHousingById(@PathVariable Long id) {
        var housing  = housingService.findHousingById(id);
        return ResponseEntity.ok(housing);
    }

    @GetMapping("/housing/index")
    public ResponseEntity<List<Housing>> getHousingList(
            @RequestParam(name = "page", defaultValue = "0")int page,
            @RequestParam(name = "size", defaultValue = "10")int size,
            @RequestParam(name = "housing_name", required = false) String housingName,
            @RequestParam(name = "floors", required = false) Integer floors,
            @RequestParam(name = "master_rooms", required = false) Integer masterRooms,
            @RequestParam(name = "single_rooms", required = false) Integer singleRooms,
            @RequestParam(value = "start_date", required = false) String startDateStr,
            @RequestParam(value = "end_date", required = false) String endDateStr,
            @RequestParam(name = "price", required = false) Double price
    )  {
        LocalDateTime startDate;
        LocalDateTime endDate;
        if (startDateStr != null && !startDateStr.isEmpty()) {
            startDate = LocalDateTime.parse(startDateStr + "T23:59:59.99999");
        } else {
            startDate = null;
        }
        if (endDateStr != null && !endDateStr.isEmpty()) {
            endDate = LocalDateTime.parse(endDateStr + "T23:59:59.99999");
        } else {
            endDate = null;
        }
        Long currentUserId = ((Owner) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        List<Housing> housingList = housingService.inquireHousing(
                HousingInquiryDTO.builder()
                        .ownerId(currentUserId)
                        .housingName(housingName)
                        .numberOfFloors(floors)
                        .numberOfMasterRooms(masterRooms)
                        .numberOfSingleRooms(singleRooms)
                        .amount(price != null? new Money(BigDecimal.valueOf(price), CommonConstants.MMK_Currency) : null)
                        .searchStartDate(startDate)
                        .searchEndDate(endDate)
                        .paging(PageRequest.of(page, size))
                        .build()
        );
        return ResponseEntity.ok(housingList);
    }
}
