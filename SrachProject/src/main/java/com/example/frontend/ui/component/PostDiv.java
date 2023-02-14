package com.example.frontend.ui.component;

import com.vaadin.flow.component.HtmlComponent;
import com.vaadin.flow.component.html.Anchor;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import lombok.Getter;
import lombok.extern.log4j.Log4j2;

@Getter
@Log4j2
public class PostDiv extends Div {
    Label postText = new Label();
    Anchor postAnchor = new Anchor();
    Anchor postNumberAnchor = new Anchor();
    Span postUsername = new Span();
    Span postDateTime = new Span();
    Span op = new Span("OP");
    Span sage = new Span("SAGE");

    public PostDiv(boolean isOp, boolean isSage) {
        Span postInfo = new Span();

        postUsername.setClassName("post-info");

        postDateTime.setClassName("post-info");

        op.setClassName("post-info");
        sage.setClassName("post-info");

        Span postLinks = new Span();
        postLinks.add(postAnchor, postNumberAnchor);
        postLinks.setClassName("post-info");

        postInfo.add(postUsername, postDateTime, postLinks);
        if (isOp){
            postInfo.add(op);
        }
        if (isSage){
            postInfo.add(sage);
        }

        postText.setClassName("post-text");
        postText.setWhiteSpace(WhiteSpace.PRE_LINE);

        add(postInfo, new HtmlComponent("br"), postText);
        setClassName("box-post");
    }
}
