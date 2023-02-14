package com.example.frontend.ui.view.error;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.ErrorParameter;
import com.vaadin.flow.router.HasDynamicTitle;
import com.vaadin.flow.router.HasErrorParameter;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletResponse;

@Tag("div")
public class RouteNotFoundError extends Component implements HasErrorParameter<EntityNotFoundException>, HasDynamicTitle {
    @Override
    public int setErrorParameter(BeforeEnterEvent beforeEnterEvent, ErrorParameter<EntityNotFoundException> errorParameter) {
        getElement().setText("АХТУНГ 404 СТРАНИЦА НЕ НАЙДЕНА");
        return HttpServletResponse.SC_NOT_FOUND;
    }

    @Override
    public String getPageTitle() {
        return "404";
    }
}
