package com.jpbolet.evenflow.api.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Data
@Builder
public class Clinician {

	public enum ClinicianType {
		DOCTOR, NURSE
	}

	@Id private String id;
	private String firstName;
	private String lastName;
	private ClinicianType clinicianType;

}
