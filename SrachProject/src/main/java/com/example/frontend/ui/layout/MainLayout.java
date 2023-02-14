package com.example.frontend.ui.layout;

import com.example.frontend.pojo.BoardPojo;
import com.example.frontend.service.ClientService;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Label;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
public class MainLayout extends AppLayout {


    public MainLayout(@Autowired ClientService clientService) {

        List<BoardPojo> boardList = clientService.getBoards();
        for (BoardPojo board : boardList) {

            Label separator = new Label("/");
            separator.getStyle().set("font-size", "10pt");

            Anchor boardAnchor = new Anchor("boards/"+board.getCodeName(), board.getCodeName());
            boardAnchor.getStyle()
                    .set("font-size", "10pt")
                    .set("margin-top", "0")
                    .set("margin-left", "7px")
                    .set("margin-right", "7px");

            addToNavbar(boardAnchor, separator);
        }
        Anchor title = new Anchor("/", "[Домой]");
        title.getStyle()
                .set("font-size", "10pt")
                .set("margin-left", "auto")
                .set("margin-top", "0");

        addToNavbar(title);
    }
}
