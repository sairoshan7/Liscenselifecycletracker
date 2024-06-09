package com.training.liscenselifecycletracker.entities;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "software")
public class Software {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "software_seq")
	@SequenceGenerator(name = "software_seq", sequenceName = "software_seq", initialValue = 500)
	@Column(name = "software_id")
	private Long softwareId;

    @Column(name = "software_name")
    private String softwareName;

    @Column(name = "license_key")
    private String licenseKey;

    @Column(name = "purchase_date")
    private LocalDate purchaseDate;

    @Column(name = "expiry_date")
    private LocalDate expiryDate;

    @Column(name = "support_end_date")
    private LocalDate supportEndDate;

    @Column(name = "status")
    private String status;


}
