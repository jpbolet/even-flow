package com.jpbolet.evenflow.api.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;
import java.util.Map;

@Data
@Builder
public class Visit {

	@Id private String visitId;
	private LocalDateTime visitStart;
	private LocalDateTime visitEnd;
	private String serviceId;
	private Patient patient;
	private Map<String, Event> events;
}
