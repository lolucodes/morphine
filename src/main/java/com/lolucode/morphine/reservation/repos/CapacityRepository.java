package com.lolucode.morphine.reservation.repos;

import com.lolucode.morphine.reservation.model.AmenityType;
import com.lolucode.morphine.reservation.model.Capacity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CapacityRepository extends JpaRepository<Capacity, Long> {
    Capacity findByAmenityType(AmenityType amenityType);

}
