package com.trenchkid.demo.service;

import com.trenchkid.demo.common.exceptions.HousingNotFoundException;
import com.trenchkid.demo.common.exceptions.OwnerMisMatchException;
import com.trenchkid.demo.dto.HousingDTO;
import com.trenchkid.demo.dto.HousingInquiryDTO;
import com.trenchkid.demo.model.Housing;
import com.trenchkid.demo.model.Owner;
import com.trenchkid.demo.repository.HousingRepository;
import com.trenchkid.demo.specifications.HousingSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HousingService {

    private final HousingRepository housingRepository;

    public Housing createHousing(HousingDTO housingDTO) {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Owner currentUser = (Owner) authentication.getPrincipal();
        housingDTO.setOwnerId(currentUser.getId());
        return housingRepository.save(
                Housing.builder()
                        .housingName(housingDTO.getHousingName())
                        .address(housingDTO.getAddress())
                        .numberOfFloors(housingDTO.getNumberOfFloors())
                        .numberOfMasterRoom(housingDTO.getNumberOfMasterRoom())
                        .numberOfSingleRoom(housingDTO.getNumberOfSingleRoom())
                        .amount(housingDTO.getAmount())
                        .createdDate(LocalDateTime.now())
                        .ownerId(housingDTO.getOwnerId())
                        .build()
        );
    }

    public Housing findHousingById(Long id) {
        var housing = housingRepository.findById(id);
        if (housing.isPresent()) {
            return housing.get();
        } else {
            throw new HousingNotFoundException("Housing not found for ID: " + id);
        }
    }

    public Housing updateHousing(Long id, HousingDTO housingDTO) {

        Long currentUserId = ((Owner) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        var housing = findHousingById(id);
        if (!housing.getOwnerId().equals(currentUserId)) {
            throw new OwnerMisMatchException("Current user does not have permission to update this housing.");
        }
        housing.setAddress(housingDTO.getAddress());
        housing.setHousingName(housingDTO.getHousingName());
        housing.setNumberOfFloors(housingDTO.getNumberOfFloors());
        housing.setNumberOfMasterRoom(housingDTO.getNumberOfMasterRoom());
        housing.setNumberOfSingleRoom(housingDTO.getNumberOfSingleRoom());
        housing.setAmount(housingDTO.getAmount());
        housing.setUpdatedDate(LocalDateTime.now());

        return housingRepository.save(housing);
    }

    public List<Housing> findHousingByOwnerId(Long ownerId, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        var optionalHousingList = housingRepository.findHousingByOwnerId(ownerId, paging);
        return optionalHousingList.get();
    }

    public List<Housing> inquireHousing(HousingInquiryDTO housingInquiryDTO) {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        Owner currentUser = null;
        if (authentication.getPrincipal() instanceof Owner) {
            currentUser = (Owner) authentication.getPrincipal();
        }
        housingInquiryDTO.setOwnerId(currentUser == null ? null : currentUser.getId());

        var startDate = housingInquiryDTO.getSearchStartDate() == null ? LocalDateTime.now() : housingInquiryDTO.getSearchStartDate();
        var endDate = housingInquiryDTO.getSearchEndDate() == null ? LocalDateTime.now().minusDays(30) : housingInquiryDTO.getSearchEndDate();

        Specification<Housing> spec = Specification.where(null);

        if (housingInquiryDTO.getOwnerId() != null) {
            spec = spec.and(HousingSpec.hasOwnerId(housingInquiryDTO.getOwnerId()));
        }
        if (startDate != null && endDate != null) {
            spec = spec.and(HousingSpec.createdDateBetween(startDate, endDate));
        }
        if (housingInquiryDTO.getHousingName() != null && !housingInquiryDTO.getHousingName().isEmpty()) {
            spec = spec.and(HousingSpec.hasHousingName(housingInquiryDTO.getHousingName()));
        }
        if (housingInquiryDTO.getNumberOfFloors() != null) {
            spec = spec.and(HousingSpec.hasFloors(housingInquiryDTO.getNumberOfFloors()));
        }
        if (housingInquiryDTO.getNumberOfSingleRooms() != null) {
            spec = spec.and(HousingSpec.hasSingleRoom(housingInquiryDTO.getNumberOfSingleRooms()));
        }
        if (housingInquiryDTO.getNumberOfMasterRooms() != null) {
            spec = spec.and(HousingSpec.hasMasterRoom(housingInquiryDTO.getNumberOfMasterRooms()));
        }
        if (housingInquiryDTO.getAmount() != null) {
            spec = spec.and(HousingSpec.hasAmount(housingInquiryDTO.getAmount()));
        }

        Page<Housing> result = housingRepository.findAll(spec, housingInquiryDTO.getPaging());
        return result.getContent();
    }

}
