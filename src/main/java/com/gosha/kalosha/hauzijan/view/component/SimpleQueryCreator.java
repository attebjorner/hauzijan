package com.gosha.kalosha.hauzijan.view.component;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.service.SentenceService;
import com.gosha.kalosha.hauzijan.service.WordService;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@SpringComponent
@UIScope
public class SimpleQueryCreator extends VerticalLayout implements KeyNotifier
{
    private TextField tfQuery = new TextField("Enter simple query");

    private final Button btnSearch = new Button("Search", VaadinIcon.SEARCH.create());

    @Setter
    private ChangeHandler changeHandler;

    public interface ChangeHandler
    {
        void onQuery(String query);
    }

    @Autowired
    public SimpleQueryCreator()
    {
        this.add(tfQuery, btnSearch);
        this.addKeyPressListener(Key.ENTER, event -> changeHandler.onQuery(tfQuery.getValue()));
        btnSearch.addClickListener(event -> changeHandler.onQuery(tfQuery.getValue()));
    }
}
