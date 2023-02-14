package com.example.frontend.ui.view;

import com.example.frontend.pojo.BoardPojo;
import com.example.frontend.pojo.PostPojo;
import com.example.frontend.pojo.ThreadPojo;
import com.example.frontend.service.ClientService;
import com.example.frontend.ui.layout.MainLayout;
import com.example.frontend.ui.component.DialogPostForm;
import com.example.frontend.ui.component.PostDiv;
import com.example.frontend.ui.component.PostForm;
import com.example.frontend.ui.component.ThreadDiv;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityNotFoundException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;

@Route(value = "boards/:boardCode/:threadNumber", layout = MainLayout.class)
@Log4j2
@AnonymousAllowed
public class ThreadView  extends VerticalLayout implements BeforeEnterObserver, HasDynamicTitle {

    private ClientService clientService;
    private String boardCode;
    private Long threadNumber;
    private Label boardName = new Label();
    private DialogPostForm dialogPostForm;
    private PostForm postForm;
    private ThreadDiv threadDiv;
    private ThreadPojo thread;
    private BoardPojo board;
    private Collection<Component> postList;
    private VerticalLayout verticalLayout;


    public ThreadView(@Autowired ClientService clientService) {
        this.clientService = clientService;

        boardName.setClassName("header-title");
        add(boardName);

        dialogPostForm = new DialogPostForm(clientService);
        add(dialogPostForm);

        postForm = new PostForm(clientService);
        add(postForm);

        threadDiv = new ThreadDiv();
        postList = new ArrayList<>();
        add(threadDiv);

        verticalLayout = new VerticalLayout();

        add(verticalLayout);
    }

    @Override
    public String getPageTitle() {
        return "/"+boardCode+"/"+" - "+thread.getName();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
        boardCode = beforeEnterEvent.getRouteParameters().get("boardCode").orElseThrow(EntityNotFoundException::new);
        board = clientService.getBoardByCodename(boardCode);
        threadNumber = Long.valueOf(beforeEnterEvent.getRouteParameters().get("threadNumber").orElseThrow(EntityNotFoundException::new));
        thread = clientService.getThreadByBoardCodenameAndThreadNumber(boardCode, threadNumber);

        boardName.setText("/" + boardCode + "/ - " + board.getName());

        if (thread.isLocked()){
            dialogPostForm.setVisible(false);
            postForm.setVisible(false);
        }

        dialogPostForm.setThreadNumber(threadNumber);
        dialogPostForm.setBoardCode(boardCode);

        postForm.setThreadNumber(threadNumber);
        postForm.setBoardCode(boardCode);

        threadDiv.getThreadAnchor().setHref("boards/"+boardCode+"/"+thread.getThreadNumber());
        threadDiv.getThreadAnchor().setText("№"+ threadNumber);
        threadDiv.getThreadName().setText(thread.getName());
        threadDiv.getThreadUsername().setText(thread.getUsername());
        threadDiv.getThreadDateTime().setText(thread.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:s")));
        threadDiv.getThreadText().setText(thread.getText());

        verticalLayout.removeAll();

        for (PostPojo post : clientService.getPostsByBoardCodenameAndTheadNumber(boardCode, threadNumber)) {
            PostDiv postDiv = new PostDiv(post.isOp(), post.isSage());
            postDiv.getPostText().setText(post.getText());
            postDiv.getPostUsername().setText(post.getUsername());
            postDiv.getPostDateTime().setText(post.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")));
            postDiv.getPostAnchor().setText("№");
            postDiv.getPostNumberAnchor().setText(String.valueOf(post.getPostNumber()));
            postDiv.getPostNumberAnchor().getElement().addEventListener("click", e -> {
                dialogPostForm.open();
                dialogPostForm.addText(">>"+postDiv.getPostNumberAnchor().getText());
            });
            postDiv.getElement().setAttribute("id", "p"+post.getPostNumber());
            threadDiv.add(postDiv);
        }
    }
}
