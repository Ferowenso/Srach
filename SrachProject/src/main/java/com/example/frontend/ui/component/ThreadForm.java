package com.example.frontend.ui.component;

import com.example.backend.dto.ThreadDto;
import com.example.frontend.pojo.ThreadPojo;
import com.example.frontend.service.ClientService;
import com.example.frontend.ui.component.base.HideForm;
import com.example.backend.service.ThreadService;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class ThreadForm extends HideForm {

    private String boardCode;
    TextField threadNameField = new TextField("Название треда");
    Binder<ThreadPojo> binder = new BeanValidationBinder<>(ThreadPojo.class);

    public void setBoardCode(String boardCode) {
        this.boardCode = boardCode;
    }

    public ThreadForm(@Autowired ClientService clientService){
        binder.forField(threadNameField)
                .withValidator(new StringLengthValidator("Название треда должно быть от 3 до 100 символов", 3, 100))
                .bind(ThreadPojo::getName, ThreadPojo::setName);
        binder.forField(postText)
                        .withValidator(new StringLengthValidator("Текст треда должен быть от 3 до 2000 символов",3, 2000))
                                .bind(ThreadPojo::getText, ThreadPojo::setText);
        threadNameField.setVisible(false);
        addComponentAtIndex(1, threadNameField);
        create.addClickListener(buttonClickEvent -> threadNameField.setVisible(true));
        cancel.addClickListener(buttonClickEvent -> threadNameField.setVisible(false));
        post.addClickListener(buttonClickEvent -> {
            if(binder.validate().isOk()){
                ThreadPojo newThread = new ThreadPojo();
                if (!nameField.getValue().isBlank()){
                    newThread.setUsername(nameField.getValue());
                }
                newThread.setName(threadNameField.getValue());
                newThread.setText(postText.getValue().formatted());
                clientService.createThreadByBoardCodename(newThread, boardCode);
                getUI().get().getPage().reload();
            }
        });
    }
}
