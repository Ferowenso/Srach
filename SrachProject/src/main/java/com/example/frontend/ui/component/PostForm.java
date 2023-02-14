package com.example.frontend.ui.component;

import com.example.backend.exception.PostingErrorException;
import com.example.frontend.pojo.PostPojo;
import com.example.frontend.service.ClientService;
import com.example.frontend.ui.component.base.HideForm;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.BeanValidationBinder;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.validator.StringLengthValidator;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
public class PostForm extends HideForm {

    private Binder<PostPojo> binder = new BeanValidationBinder<>(PostPojo.class);
    private String boardCode;
    private Long threadNumber;
    private Checkbox sage = new Checkbox("SAGE");
    private Checkbox op = new Checkbox("ОП треда");

    public void setBoardCode(String boardCode) {
        this.boardCode = boardCode;
    }

    public void setThreadNumber(Long threadNumber) {
        this.threadNumber = threadNumber;
    }

    public PostForm(@Autowired ClientService clientService){

        binder.forField(postText)
                .withValidator(new StringLengthValidator("Текст поста должен быть от 3 до 2000 символов",3, 2000))
                .bind(PostPojo::getText, PostPojo::setText);
        setClassName("form-post-thread");

        sage.setVisible(false);
        op.setVisible(false);

        Span span = new Span();
        span.add(sage, op);

        addComponentAtIndex(2, span);
        create.addClickListener(buttonClickEvent -> {
            sage.setVisible(true);
            op.setVisible(true);
        });
        cancel.addClickListener(buttonClickEvent -> {
            sage.setVisible(false);
            op.setVisible(false);
        });

        post.addClickListener(buttonClickEvent -> {
            if(binder.validate().isOk()){
                PostPojo newPost = new PostPojo();
                if (!nameField.getValue().isBlank()){
                    newPost.setUsername(nameField.getValue());
                }
                newPost.setOp(op.getValue());
                newPost.setSage(sage.getValue());
                newPost.setText(postText.getValue());
                try{
                    clientService.createPostByBoardCodenameAndThreadnumber(newPost, boardCode, threadNumber);
                    getUI().get().getPage().reload();
                }catch (PostingErrorException e){
                    add(new Notification("Тред закрыт"));
                }
                }
        });
    }

}
