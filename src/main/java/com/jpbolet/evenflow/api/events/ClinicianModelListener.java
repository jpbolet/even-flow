package com.jpbolet.evenflow.api.events;

import com.jpbolet.evenflow.api.model.Clinician;
import com.jpbolet.evenflow.api.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class ClinicianModelListener extends AbstractMongoEventListener<Clinician> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public ClinicianModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Clinician> event) {
        event.getSource().setId(sequenceGenerator.generateSequence(Clinician.SEQUENCE_NAME));
    }

}