package com.gosha.kalosha.hauzijan.view;

import com.vaadin.flow.router.*;
import org.springframework.web.servlet.NoHandlerFoundException;

import javax.servlet.http.HttpServletResponse;

public class NotFoundRoute extends RouteNotFoundError
{
    @Override
    public int setErrorParameter(BeforeEnterEvent event, ErrorParameter<NotFoundException> parameter)
    {
        event.rerouteToError(NoHandlerFoundException.class);
        return HttpServletResponse.SC_NOT_FOUND;
    }
}
