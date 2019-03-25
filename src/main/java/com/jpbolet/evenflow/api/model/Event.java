package com.jpbolet.evenflow.api.model;

import com.jpbolet.evenflow.api.model.Stage.StageType;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;

@Data
@Builder
public class Event {

	public enum EventType {
		ASSESSMENT, BIOPSY
	}

	@Transient
	public static final String SEQUENCE_NAME = "events_sequence";

	@Id private long id;
	private LocalDateTime start;
	private LocalDateTime end;
	private StageType stageType;
	private Clinician clinician;
	private String description;

}
