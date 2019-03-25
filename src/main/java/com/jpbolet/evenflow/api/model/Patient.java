package com.jpbolet.evenflow.api.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
@Builder
public class Patient {

	@Id private String id;
	private String firstName;
	private String lastName;
	private String nhsId;

}
