package com.jpbolet.evenflow.api.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

@Data
@Builder
public class Patient {

	@Transient
	public static final String SEQUENCE_NAME = "patients_sequence";

	@Id private long id;
	private String firstName;
	private String lastName;
	private String nhsId;

}
