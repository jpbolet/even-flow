package com.jpbolet.evenflow.api.events;

import com.jpbolet.evenflow.api.model.Patient;
import com.jpbolet.evenflow.api.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class PatientModelListener extends AbstractMongoEventListener<Patient> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public PatientModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Patient> event) {
        event.getSource().setId(sequenceGenerator.generateSequence(Patient.SEQUENCE_NAME));
    }

}