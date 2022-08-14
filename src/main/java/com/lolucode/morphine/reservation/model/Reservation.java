package com.lolucode.morphine.reservation.model;

import com.lolucode.morphine.appuser.AppUser;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;


@Entity
@Table
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reservation {

    @Id
    @Column(nullable = false, updatable = false)
    @SequenceGenerator(
            name = "primary_sequence",
            sequenceName = "primary_sequence",
            allocationSize = 1,
            initialValue = 10000
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "primary_sequence"
    )
    private Long id;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(nullable = false)
    private LocalDate reservationDate;

    @DateTimeFormat(pattern = "HH:mm")
    @Column
    private LocalTime startTime;

    @DateTimeFormat(pattern = "HH:mm")
    @Column
    private LocalTime endTime;

    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser appUser;

    @Column(nullable = false, updatable = false)
    private OffsetDateTime dateCreated;

    @Column(nullable = false)
    private OffsetDateTime lastUpdated;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AmenityType amenityType;

    @PrePersist
    public void prePersist() {
        dateCreated = OffsetDateTime.now();
        lastUpdated = dateCreated;
    }

    @PreUpdate
    public void preUpdate() {
        lastUpdated = OffsetDateTime.now();
    }

    public Reservation(LocalDate reservationDate,
                       LocalTime startTime,
                       LocalTime endTime,
                       AppUser appUser,
                       AmenityType amenityType) {
        this.reservationDate = reservationDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.appUser = appUser;
        this.amenityType = amenityType;
    }


}
