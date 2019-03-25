package com.jpbolet.evenflow.api.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class Visit {

	@Transient
	public static final String SEQUENCE_NAME = "visits_sequence";

	@Id private long id;
	private LocalDateTime visitStart;
	private LocalDateTime visitEnd;
	private String serviceId;
	private Patient patient;
	private Map<String, Event> events;
}
