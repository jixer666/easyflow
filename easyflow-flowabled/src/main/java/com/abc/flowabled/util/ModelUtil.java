package com.abc.flowabled.util;

import com.abc.flowabled.domain.dto.NodeDTO;
import org.flowable.bpmn.model.BpmnModel;
import org.flowable.bpmn.model.EventListener;
import org.flowable.bpmn.model.Process;

import java.util.ArrayList;
import java.util.List;

public class ModelUtil {


    public static BpmnModel buildBpmnModel(NodeDTO nodeConfig) {
        BpmnModel bpmnModel = new BpmnModel();
        bpmnModel.setTargetNamespace("easyflow");

        Process process = new Process();
        process.setId("1231231");
        process.setName("测试111");

        buildAllEventListener(process);



        return bpmnModel;


    }

    private static void buildAllEventListener(Process process) {
        List<EventListener> allEventListeners = new ArrayList<>();
        EventListener eventListener = new EventListener();
        eventListener.setImplementationType("class");
        // todo
        // eventListener.setImplementation();
        allEventListeners.add(eventListener);
        process.setEventListeners(allEventListeners);
    }
}
