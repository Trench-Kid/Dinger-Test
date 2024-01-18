package com.trenchkid.demo.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.trenchkid.demo.common.datatype.Money;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "housing")
public class Housing {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "housing_id")
    private Long id;
    @Column(name = "housing_name")
    private String housingName;
    @Column(name = "address")
    private String address;
    @Column(name = "number_of_floors")
    private Integer numberOfFloors;
    @Column(name = "number_of_master_rooms")
    private Integer numberOfMasterRoom;
    @Column(name = "number_of_single_rooms")
    private Integer numberOfSingleRoom;
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "amount", column = @Column(name = "amount")),
            @AttributeOverride(name = "currency", column = @Column(name = "currency"))
    })
    private Money amount;
    @Column(name = "owner_id")
    private Long ownerId;
    @Column(name = "created_date")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDateTime createdDate;
    @Column(name = "updated_date")
    @JsonFormat(pattern = "yyyy-MM-dd", shape = JsonFormat.Shape.STRING)
    private LocalDateTime updatedDate;

}
