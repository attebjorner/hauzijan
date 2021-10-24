package com.gosha.kalosha.hauzijan.view;

import com.gosha.kalosha.hauzijan.exception_handing.NoSentencesFoundException;
import com.vaadin.flow.router.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

public class NotFoundRoute extends RouteNotFoundError
{
    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter)
    {
        event.rerouteToError(NoSentencesFoundException.class, "abc");
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
