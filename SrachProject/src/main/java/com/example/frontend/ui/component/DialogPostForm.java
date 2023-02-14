package com.example.frontend.ui.component;

import com.example.frontend.pojo.PostPojo;
import com.example.frontend.service.ClientService;
import com.example.frontend.ui.component.base.Form;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.Span;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Log4j2
public class DialogPostForm extends Dialog {

    private Form baseForm = new Form();
    private String boardCode;
    private Long threadNumber;

    Checkbox sage = new Checkbox("SAGE");
    Checkbox op = new Checkbox("ОП треда");

    public void setBoardCode(String boardCode) {
        this.boardCode = boardCode;
    }

    public void setThreadNumber(Long threadNumber) {
        this.threadNumber = threadNumber;
        setHeaderTitle("Ответить в тред №" + threadNumber);
    }

    public DialogPostForm(@Autowired ClientService clientService){
        setClassName("dialog-post-form");

        Button close = new Button("X");
        close.setClassName("dialog-post-form-close");
        getHeader().add(close);
        close.addClickListener(buttonClickEvent -> close());

        baseForm.getPostText().setClassName("dialog-post-form-text");
        add(baseForm);

        Span span = new Span();
        span.add(sage, op);
        baseForm.addComponentAtIndex(2, span);

        setDraggable(true);
        setModal(false);
        setCloseOnOutsideClick(false);

        baseForm.getPost().addClickListener(buttonClickEvent -> {
            PostPojo newPost = new PostPojo();
            if (!baseForm.getNameField().getValue().isBlank()){
                newPost.setUsername(baseForm.getNameField().getValue());
            }
            newPost.setOp(op.getValue());
            newPost.setSage(sage.getValue());
            newPost.setText(baseForm.getPostText().getValue());
            clientService.createPostByBoardCodenameAndThreadnumber(newPost, boardCode, threadNumber);
            getUI().get().getPage().reload();
        });
    }

    public void addText(String text){
        String postText = baseForm.getPostText().getValue();
        baseForm.getPostText().setValue(postText+text+"\n");
    }
}
