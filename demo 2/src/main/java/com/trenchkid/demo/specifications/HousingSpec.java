package com.trenchkid.demo.specifications;

import com.trenchkid.demo.common.datatype.Money;
import com.trenchkid.demo.model.Housing;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;

public class HousingSpec {

    public static Specification<Housing> hasHousingName(String housingName) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.like(criteriaBuilder.upper(root.get("housingName")), "%"+housingName.toUpperCase()+"%");
    }
    public static Specification<Housing> hasFloors(int floors) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("numberOfFloors"), floors);
    }
    public static Specification<Housing> hasMasterRoom(int masterRooms) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("numberOfMasterRoom"), masterRooms);
    }
    public static Specification<Housing> hasSingleRoom(int singleRooms) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("numberOfSingleRoom"), singleRooms);
    }
    public static Specification<Housing> hasAmount(Money amount) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.lessThanOrEqualTo(root.get("amount").get("amount"), amount.getAmount());
    }

    public static Specification<Housing> hasOwnerId(Long ownerId) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.equal(root.get("ownerId"), ownerId);
    }

    public static Specification<Housing> createdDateBetween(LocalDateTime startDate, LocalDateTime endDate) {
        return (root, query, criteriaBuilder) ->
                criteriaBuilder.between(root.get("createdDate"), endDate, startDate);
    }
}
