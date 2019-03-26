package com.jpbolet.evenflow.api;

import com.jpbolet.evenflow.api.model.*;
import com.jpbolet.evenflow.api.repository.StageRepository;
import com.jpbolet.evenflow.api.repository.VisitRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import pl.allegro.finance.tradukisto.ValueConverters;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

import static com.jpbolet.evenflow.api.model.Clinician.ClinicianType.*;
import static com.jpbolet.evenflow.api.model.Stage.StageType.*;

@SpringBootApplication
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);
	public static final int MAX_DOCTORS = 2;
	public static final int MAX_NURSES = 4;

	public static final int MIN_DOCTOR_ASSESSMENT_MINUTES = 10;
	public static final int MAX_DOCTOR_ASSESSMENT_MINUTES = 20;

	public static final int MIN_NURSE_BIOPSY_MINUTES = 10;
	public static final int MAX_NURSE_BIOPSY_MINUTES = 20;
	public static final int TEST_DAYS = 60;
	public static final int TEST_PATIENTS = 60;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Autowired
	StageRepository stageRepository;

	@Bean
	public CommandLineRunner loadData(VisitRepository repository) {
		return (args) -> {

			for (int i = 1; i <= TEST_DAYS; i++) {
				for (int j = 1; j <= TEST_PATIENTS; j++) {




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
					.stageType(ASSESSMENT)
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
					.stageType(BIOPSY)
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


			Stage assessmentStage = Stage.builder()
					.eventType(ASSESSMENT)
					.name("initial assessment")
					.maximumAllowedDurationSeconds(1200)
					.build();
			stageRepository.save(assessmentStage);

			Stage biopsyStage = Stage.builder()
					.eventType(BIOPSY)
					.name("biopsy")
					.maximumAllowedDurationSeconds(600)
					.build();
			stageRepository.save(biopsyStage);


				}
			}

			//setupData();
		};
	}

	private void setupData() {
		LocalDateTime end = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);
		LocalDateTime start = end.minus(60, ChronoUnit.DAYS);
		setupDays(start, end);
	}

	private void setupDays(LocalDateTime start, LocalDateTime end) {

		System.out.println("... Setting up test weekdays between " + start.toString() + " and " + end.toString());
		for (LocalDateTime date = start; date.isBefore(end); date = date.plusDays(1)) {
			if(date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY) {
				setupDay(date, 20);
			}
		}
	}

	private void setupDay(LocalDateTime day, int numberOfPatients) {

		System.out.println("# Day=" + day.getDayOfWeek().toString() + " " + day.toString());

		List<Clinician> clinicians = setupClinicians(day);
		clinicians.forEach(clinician -> {
			System.out.println(clinician);
		});

		setupVisits(day, numberOfPatients, clinicians);

	}

	private void setupVisits(LocalDateTime day, int numberOfPatients, List<Clinician> clinicians) {

		LocalDateTime dayStart = day.withHour(9).withMinute(0).withSecond(0).withNano(0);
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		for (int i = 1; i <= numberOfPatients; i++) {

			String nhsId = dateTimeFormatter.format(dayStart) + String.format("%2s", String.valueOf(i)).replace(' ', '0');

			Patient patient = Patient.builder()
					.id(Long.parseLong(nhsId))
					.firstName(capitalizeWordFormOfInt(i))
					.lastName(capitalizeWordFormOfInt(i))
					.nhsId(nhsId)
					.build();
			System.out.println("## Patient - " + patient.toString());

			Map<String, Event> events = new HashMap<String, Event>();


			// get doctor assessment time
			//System.out.println("## Patient - " + capitalizeWordFormOfInt(i) + " " + capitalizeWordFormOfInt(i));

			// nurse biopsy

		}
	}

/*	private Clinician getAvailableClinicianSlot(LocalDateTime slotRequestTime, List<Clinician> clinicians, Clinician.ClinicianType clinicianType) {
		clinicians.forEach(clinician -> {
			if(clinician.getClinicianType().equals(clinicianType)) {
				// get free clinician of the correct type
				if(!clinician.isEngagedInActivity() && clinician.getEndActivity().isBefore(slotRequestTime)) {
					return clinician;
				}
			}
		});

		return null;
	}*/

	private List<Clinician> setupClinicians(LocalDateTime day) {
		List<Clinician>clinicians = new ArrayList<Clinician>();

		// setup one to two doctors
		for (int i = 1; i <= getRandomNumberInRange(1, MAX_DOCTORS); i++) {
			clinicians.add(Clinician.builder()
					.firstName("Dr")
					.lastName(capitalizeWordFormOfInt(i))
					.clinicianType(DOCTOR)
					.isEngagedInActivity(false)
					.startActivity(day.withHour(9).withMinute(0).withSecond(0).withNano(0))
					.endActivity(day.withHour(9).withMinute(0).withSecond(0).withNano(0))
					.build());
		}

		// setup one to four nurses
		for (int i = 1; i <= getRandomNumberInRange(1, MAX_NURSES); i++) {
			clinicians.add(Clinician.builder()
					.firstName("Nurse")
					.lastName(capitalizeWordFormOfInt(i))
					.clinicianType(NURSE)
					.isEngagedInActivity(false)
					.startActivity(day.withHour(9).withMinute(0).withSecond(0).withNano(0))
					.endActivity(day.withHour(9).withMinute(0).withSecond(0).withNano(0))
					.build());
		}

		return clinicians;
	}

	private static int getRandomNumberInRange(int min, int max) {
		Random r = new Random();
		return r.ints(min, (max + 1)).findFirst().getAsInt();
	}

	private String capitalizeWordFormOfInt(int i) {
		ValueConverters converter = ValueConverters.ENGLISH_INTEGER;
		String input = converter.asWords(i);
		return input.substring(0, 1).toUpperCase() + input.substring(1);
	}

}
