package com.example.frontend.ui.view;

import com.example.frontend.pojo.BoardPojo;
import com.example.frontend.pojo.PostPojo;
import com.example.frontend.pojo.ThreadPojo;
import com.example.frontend.service.ClientService;
import com.example.frontend.ui.component.DialogPostForm;
import com.example.frontend.ui.component.PostDiv;
import com.example.frontend.ui.component.ThreadDiv;
import com.example.frontend.ui.component.ThreadForm;
import com.example.frontend.ui.layout.MainLayout;
import com.vaadin.flow.component.accordion.Accordion;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.router.*;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.security.PermitAll;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Route(value="boards/:boardCode", layout = MainLayout.class)
@Log4j2
@AnonymousAllowed
public class BoardsView extends VerticalLayout implements BeforeEnterObserver , HasDynamicTitle {

    private String boardCode;
    private BoardPojo board;
    private Label boardName;
    private DialogPostForm dialogPostForm;
    private ClientService clientService;
    private ThreadForm threadForm;
    private VerticalLayout verticalLayout = new VerticalLayout();

    public BoardsView(@Autowired ClientService clientService){
        this.clientService = clientService;

        boardName = new Label();
        boardName.setClassName("header-title");
        add(boardName);

        dialogPostForm = new DialogPostForm(clientService);
        add(dialogPostForm);

        threadForm = new ThreadForm(clientService);
        add(threadForm);

        add(verticalLayout);
    }

    @Override
    public String getPageTitle() {
        return board.getName();
    }

    @Override
    public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {

        boardCode = beforeEnterEvent.getRouteParameters().get("boardCode").get();
        board = clientService.getBoardByCodename(boardCode);
        boardName.setText("/" + boardCode + "/ - " + board.getName());
        threadForm.setBoardCode(boardCode);
        threadForm.setClassName("form-post-thread");

        dialogPostForm.setBoardCode(boardCode);

        List<ThreadPojo> threadList = clientService.getThreadsByBoardCodename(boardCode);

        verticalLayout.removeAll();

        for (ThreadPojo thread : threadList) {

            ThreadDiv threadDiv = new ThreadDiv();
            threadDiv.getThreadAnchor().setHref("boards/"+boardCode+"/"+thread.getThreadNumber());
            threadDiv.getThreadAnchor().setText("№");
            threadDiv.getThreadNumberAnchor().setText(String.valueOf(thread.getThreadNumber()));
            threadDiv.getThreadNumberAnchor().setHref("boards/"+boardCode+"#t"+thread.getThreadNumber());
            if(!thread.isLocked()){
                threadDiv.getThreadNumberAnchor().getElement().addEventListener("click", e -> {
                    dialogPostForm.setThreadNumber(thread.getThreadNumber());
                    dialogPostForm.open();
                    dialogPostForm.addText(">>"+thread.getThreadNumber());
                });
            }
            threadDiv.getThreadText().setText(thread.getText());
            threadDiv.getThreadUsername().setText(thread.getUsername());
            threadDiv.getThreadName().setText(thread.getName());
            threadDiv.getThreadDateTime().setText(thread.getCreatedAt().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")));
            threadDiv.getElement().setAttribute("id","t" + thread.getThreadNumber());

            verticalLayout.getElement().appendChild(new Element("hr"));

            Accordion skipPosts = new Accordion();
            skipPosts.close();

            int postsCount = clientService.getPostsByBoardCodenameAndTheadNumber(boardCode, thread.getThreadNumber()).size();

            verticalLayout.add(threadDiv);

            if(postsCount>3){
                VerticalLayout skipPostsLayout = new VerticalLayout();
                skipPostsLayout.setClassName("skip-posts");
                for(int i = 0; i < postsCount-3; i++){
                    PostPojo post = clientService.getPostsByBoardCodenameAndTheadNumber(boardCode, thread.getThreadNumber()).get(i);
                    skipPostsLayout.add(createPostDiv(post, thread.getThreadNumber()));
                }
                skipPosts.add("Пропущено постов: " + (postsCount-3), skipPostsLayout);
                skipPosts.getElement().setAttribute("data-id", String.valueOf(thread.getThreadNumber()));
                threadDiv.add(skipPosts);
            }

            for (PostPojo post : clientService.getLastPostsByBoardCodenameAndTheadNumber(boardCode, thread.getThreadNumber())) {
                threadDiv.add(createPostDiv(post, thread.getThreadNumber()));
            }
            verticalLayout.getElement().appendChild(new Element("hr"));
        }
    }

    private PostDiv createPostDiv(PostPojo post, Long threadNumber) {
        PostDiv postDiv = new PostDiv(post.isOp(), post.isSage());
        postDiv.getPostText().setText(post.getText());
        postDiv.getPostUsername().setText(post.getUsername());
        postDiv.getPostDateTime().setText(post.getDateTime().format(DateTimeFormatter.ofPattern("dd/MM/yy HH:mm:ss")));
        postDiv.getPostAnchor().setText("№");
        postDiv.getPostAnchor().setHref("boards/"+boardCode+"/"+threadNumber+"#p"+post.getPostNumber());
        postDiv.getPostNumberAnchor().setHref("boards/"+boardCode+"/"+threadNumber+"#p"+post.getPostNumber());
        postDiv.getPostNumberAnchor().setText(String.valueOf(post.getPostNumber()));
        if (!post.getThread().isLocked()){
            postDiv.getPostNumberAnchor().getElement().addEventListener("click", e -> {
                dialogPostForm.setThreadNumber(threadNumber);
                dialogPostForm.open();
                dialogPostForm.addText(">>"+postDiv.getPostNumberAnchor().getText());
            }).addEventData("event.preventDefault()");
        }
        postDiv.getElement().setAttribute("id","p" + post.getPostNumber());
        return postDiv;
    }

}
