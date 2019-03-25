package com.jpbolet.evenflow.api.events;

import com.jpbolet.evenflow.api.model.Event;
import com.jpbolet.evenflow.api.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class EventModelListener extends AbstractMongoEventListener<Event> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public EventModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Event> event) {
        event.getSource().setId(sequenceGenerator.generateSequence(Event.SEQUENCE_NAME));
    }

}