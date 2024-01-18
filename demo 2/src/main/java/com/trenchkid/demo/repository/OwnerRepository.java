package com.trenchkid.demo.repository;

import com.trenchkid.demo.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<Owner, Long> {
    Optional<Owner> findByOwnerUserName(String ownerUserName);
}
