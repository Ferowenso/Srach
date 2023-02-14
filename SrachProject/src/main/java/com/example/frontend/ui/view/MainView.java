package com.example.frontend.ui.view;

import com.example.frontend.pojo.BoardPojo;
import com.example.frontend.service.ClientService;
import com.example.frontend.ui.layout.MainLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(layout = MainLayout.class)
@CssImport("main.css")
@PageTitle("Srach")
@AnonymousAllowed
public class MainView extends VerticalLayout {


    public MainView(@Autowired ClientService clientService) {

        List<BoardPojo> boardList = clientService.getBoards();
        HorizontalLayout boardsBox = new HorizontalLayout();
        boardsBox.setClassName("box-outer");
        add(boardsBox);

        VerticalLayout adultCollumn = new VerticalLayout();
        VerticalLayout otherCollumn = new VerticalLayout();
        VerticalLayout creativeCollumn = new VerticalLayout();
        VerticalLayout interestsCollumn = new VerticalLayout();
        VerticalLayout videoGamesCollumn = new VerticalLayout();

        boardsBox.add(adultCollumn, otherCollumn, creativeCollumn, interestsCollumn, videoGamesCollumn);

        adultCollumn.add("18+");
        otherCollumn.add("Другое");
        creativeCollumn.add("Творчество");
        interestsCollumn.add("Хобби");
        videoGamesCollumn.add("Видеоигры");

        for (BoardPojo board : boardList) {
            switch (board.getSubject()){
                case "ADULT" -> adultCollumn.add(new Anchor("boards/"+board.getCodeName(), board.getName()));
                case "OTHER" -> otherCollumn.add(new Anchor("boards/"+board.getCodeName(), board.getName()));
                case "CREATIVE" -> creativeCollumn.add(new Anchor("boards/"+board.getCodeName(), board.getName()));
                case "INTERESTS" -> interestsCollumn.add(new Anchor("boards/"+board.getCodeName(), board.getName()));
                case "VIDEO_GAMES" -> videoGamesCollumn.add(new Anchor("boards/"+board.getCodeName(), board.getName()));
            }
        }
    }

}
