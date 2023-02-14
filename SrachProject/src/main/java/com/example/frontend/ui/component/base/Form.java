package com.example.frontend.ui.component.base;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.upload.Upload;
import com.vaadin.flow.component.upload.receivers.MultiFileMemoryBuffer;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

import java.io.InputStream;

@Getter
@Log4j2
public class Form extends VerticalLayout {
    protected TextField nameField = new TextField("Имя");
    protected TextArea postText = new TextArea();
    protected Button post = new Button("Запостить");
    MultiFileMemoryBuffer buffer = new MultiFileMemoryBuffer();
    Upload upload = new Upload(buffer);


    public Form() {

        add(nameField);
        add(postText);

        add(upload);

        add(post);
        upload.addSucceededListener(event -> {
            String fileName = event.getFileName();
            InputStream inputStream = buffer.getInputStream(fileName);
            log.info(fileName);
            log.info(inputStream);
        });

    }
}
