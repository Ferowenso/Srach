package com.example.frontend.ui.component.base;

import com.vaadin.flow.component.button.Button;
import lombok.Getter;

@Getter
public class HideForm extends Form {

    protected Button create = new Button("Создать");
    protected Button cancel = new Button("Отмена");

    public HideForm(){

        nameField.setVisible(false);
        postText.setVisible(false);
        post.setVisible(false);

        cancel.setVisible(false);

        upload.setVisible(false);

        add(create);

        add(cancel);

        create.addClickListener(buttonClickEvent -> {
            nameField.setVisible(true);
            postText.setVisible(true);
            post.setVisible(true);
            cancel.setVisible(true);
            create.setVisible(false);
            upload.setVisible(true);
        });

        cancel.addClickListener(buttonClickEvent -> {
            nameField.setVisible(false);
            postText.setVisible(false);
            post.setVisible(false);
            cancel.setVisible(false);
            create.setVisible(true);
            upload.setVisible(false);
        });

    }
}
