package com.jpbolet.evenflow.api.events;

import com.jpbolet.evenflow.api.model.Visit;
import com.jpbolet.evenflow.api.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class VisitModelListener extends AbstractMongoEventListener<Visit> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public VisitModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Visit> event) {
        event.getSource().setId(sequenceGenerator.generateSequence(Visit.SEQUENCE_NAME));
    }

}