package com.gosha.kalosha.hauzijan.view.component;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@SpringComponent
@UIScope
public class SimpleQueryCreator extends VerticalLayout implements KeyNotifier
{
    private final TextField tfQuery = new TextField("Enter simple query");

    private final Button btnSearch = new Button("Search", VaadinIcon.SEARCH.create());

    @Setter
    private ChangeHandler<String> changeHandler;

    @Setter
    private ErrorHandler errorHandler;

    @Autowired
    public SimpleQueryCreator()
    {
        this.add(tfQuery, btnSearch);
        this.addKeyPressListener(Key.ENTER, event -> sendQuery());
        btnSearch.addClickListener(event -> sendQuery());
    }

    public void sendQuery()
    {
        String query = tfQuery.getValue();
        if (query.isEmpty())
        {
            errorHandler.onError("The query is empty");
            return;
        }
        changeHandler.onQuery(query);
    }
}
