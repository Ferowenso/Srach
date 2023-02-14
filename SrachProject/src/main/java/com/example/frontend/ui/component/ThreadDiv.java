package com.example.frontend.ui.component;

import com.vaadin.flow.component.HasText;
import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.html.*;
import lombok.Getter;

@Getter
public class ThreadDiv extends Div {

    private Span threadName = new Span();
    private Anchor threadAnchor = new Anchor();
    private Anchor threadNumberAnchor = new Anchor();
    private Span threadUsername = new Span();
    private Span threadDateTime = new Span();
    private Label threadText = new Label();



    public ThreadDiv() {
        Span threadInfoSpan = new Span();

        threadName.setClassName("post-info thread-name");

        Span threadLinks = new Span();
        threadLinks.add(threadAnchor, threadNumberAnchor);
        threadLinks.setClassName("post-info");

        threadUsername.setClassName("post-info");

        threadDateTime.setClassName("post-info");

        threadInfoSpan.setClassName("post-info");
        threadInfoSpan.add(threadName, threadUsername, threadDateTime, threadLinks);

        threadText.setWhiteSpace(HasText.WhiteSpace.PRE_LINE);
        threadText.setClassName("post-text");
        add(threadInfoSpan, new HtmlComponent("br"), threadText);
    }


}
