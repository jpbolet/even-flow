package com.jpbolet.evenflow.api.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Builder
public class Clinician {

	public enum ClinicianType {
		DOCTOR, NURSE
	}

	@Transient
	public static final String SEQUENCE_NAME = "clinicians_sequence";

	@Id private long id;
	private String firstName;
	private String lastName;
	private ClinicianType clinicianType;
	LocalDateTime startActivity;
	LocalDateTime endActivity;
	boolean isEngagedInActivity = false;

}
