package com.jpbolet.evenflow.api.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.time.LocalDateTime;

@Data
@Builder
public class Stage {

	public enum StageType {
		ASSESSMENT, BIOPSY
	}

	@Transient
	public static final String SEQUENCE_NAME = "stages_sequence";

	@Id private long id;
	private StageType eventType;
	private String name;
	private long maximumAllowedDurationSeconds;

}
