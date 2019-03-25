package com.jpbolet.evenflow.api.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;

import java.time.LocalDateTime;

@Data
@Builder
public class Event {

	public enum EventType {
		ASSESSMENT, BIOPSY
	}

	@Id private String id;
	private LocalDateTime start;
	private LocalDateTime end;
	private EventType eventType;
	private Clinician clinician;
	private String description;

}
