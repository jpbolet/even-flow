package com.jpbolet.evenflow.api;

import com.jpbolet.evenflow.api.model.*;
import com.jpbolet.evenflow.api.repository.VisitRepository;
import com.jpbolet.evenflow.api.services.SequenceGeneratorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

import static com.jpbolet.evenflow.api.model.Clinician.ClinicianType.*;
import static com.jpbolet.evenflow.api.model.Event.EventType.*;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Bean
	public CommandLineRunner loadData(VisitRepository repository) {
		return (args) -> {

			LocalDateTime visitStartDate = LocalDateTime.now();
			LocalDateTime doctor1Date = visitStartDate.plus(10, ChronoUnit.MINUTES);

			Patient patient1 = Patient.builder()
					.firstName("Jean-Pierre")
					.lastName("Bolet")
					.nhsId("1234567890")
					.build();

			Map<String, Event> events = new HashMap<String, Event>();
			events.put("1", Event.builder()
					.clinician(
							Clinician.builder()
									.firstName("Mister")
									.lastName("Hyde")
									.clinicianType(DOCTOR)
									.build())
					.eventType(ASSESSMENT)
					.description("Initial assesment")
					.start(doctor1Date)
					.end(doctor1Date.plus(15, ChronoUnit.MINUTES))
					.build());
			events.put("2", Event.builder()
					.clinician(
							Clinician.builder()
									.firstName("Florence")
									.lastName("Nightingale")
									.clinicianType(NURSE)
									.build())
					.eventType(BIOPSY)
					.description("taken biopsy")
					.start(doctor1Date.plus(30, ChronoUnit.MINUTES))
					.end(doctor1Date.plus(40, ChronoUnit.MINUTES))
					.build());

			Visit visit = Visit.builder()
					.serviceId("TEST_SERVICE")
					.patient(patient1)
					.events(events)
					.visitStart(visitStartDate)
					.visitEnd(doctor1Date.plus(40, ChronoUnit.MINUTES))
					.build();

			repository.save(visit);

		};
	}


}
