package com.trenchkid.demo.repository;

import com.trenchkid.demo.common.datatype.Money;
import com.trenchkid.demo.model.Housing;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface HousingRepository extends JpaRepository<Housing, Long>, JpaSpecificationExecutor {
    Optional<List<Housing>> findHousingByOwnerId(Long ownerId, Pageable paging);

    Optional<List<Housing>> findHousingByOwnerIdAndNumberOfFloorsAndNumberOfMasterRoomAndNumberOfSingleRoomAndAmountAndHousingNameLikeAndCreatedDateBetween(
            Long ownerId, Integer numberOfFloors, Integer numberOfMasterRoom, Integer numberOfSingleRoom, Money amount, String housingName, Date startDate, Date endDate
    );

    @Query("SELECT h FROM Housing h WHERE " +
            "h.ownerId = :ownerId " +
            "AND h.numberOfFloors = :numberOfFloors " +
            "AND h.numberOfMasterRoom = :numberOfMasterRoom " +
            "AND h.numberOfSingleRoom = :numberOfSingleRoom " +
            "AND h.amount = :amount " +
            "AND h.housingName LIKE %:housingName% " +
            "AND h.createdDate BETWEEN :startDate AND :endDate")
    Optional<List<Housing>> inquireHousing(
            Long ownerId, Integer numberOfFloors, Integer numberOfMasterRoom, Integer numberOfSingleRoom,
            Money amount, String housingName, Date startDate, Date endDate
    );
}
