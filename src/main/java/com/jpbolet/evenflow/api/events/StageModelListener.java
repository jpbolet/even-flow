package com.jpbolet.evenflow.api.events;

import com.jpbolet.evenflow.api.model.Stage;
import com.jpbolet.evenflow.api.services.SequenceGeneratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.mapping.event.AbstractMongoEventListener;
import org.springframework.data.mongodb.core.mapping.event.BeforeConvertEvent;
import org.springframework.stereotype.Component;


@Component
public class StageModelListener extends AbstractMongoEventListener<Stage> {

    private SequenceGeneratorService sequenceGenerator;

    @Autowired
    public StageModelListener(SequenceGeneratorService sequenceGenerator) {
        this.sequenceGenerator = sequenceGenerator;
    }

    @Override
    public void onBeforeConvert(BeforeConvertEvent<Stage> event) {
        event.getSource().setId(sequenceGenerator.generateSequence(Stage.SEQUENCE_NAME));
    }

}