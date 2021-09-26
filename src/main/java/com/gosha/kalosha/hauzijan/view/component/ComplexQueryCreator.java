package com.gosha.kalosha.hauzijan.view.component;

import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;
import lombok.Setter;

import java.util.*;

import static com.gosha.kalosha.hauzijan.model.ParameterType.*;

@SpringComponent
@UIScope
public class ComplexQueryCreator extends VerticalLayout implements KeyNotifier
{
    private final String SHOW_GRAMMAR = "Set up grammar";
    private final String HIDE_GRAMMAR = "Hide grammar";

    private final TextField tfLemma = new TextField("Lemma");
    private final TextField tfPos = new TextField("Pos");

    private final Button btnSearch = new Button("Search", VaadinIcon.SEARCH.create());
    private final Button btnGrammar = new Button(SHOW_GRAMMAR, VaadinIcon.ASTERISK.create());

    private final VerticalLayout vlGrammar = new VerticalLayout();

    private final Map<String, List<String>> gramValues;

    private final Map<String, String> selectedGrammar = new HashMap<>();

    @Setter
    private ChangeHandler<Map<String, Object>> changeHandler;

    @Setter
    private ErrorHandler errorHandler;

    {
        gramValues = new LinkedHashMap<>();
        gramValues.put("Case", List.of("Acc", "Gen", "Tem"));
        gramValues.put("Person", List.of("1", "2", "3", "'1,2,3'"));
        gramValues.put("Gender", List.of("Masc", "Fem", "'Fem,Masc'"));
        gramValues.put("HebBinyan", List.of("HUFAL", "PUAL", "PAAL", "PIEL", "HITPAEL", "NIFAL", "HIFIL"));
        gramValues.put("Number", List.of("Sing", "Dual", "Plur", "'Dual,Plur'", "'Plur,Sing'"));
        gramValues.put("Reflex", List.of("Yes"));
        gramValues.put("VerbForm", List.of("Part", "Inf"));
        gramValues.put("Tense", List.of("Past", "Fut"));
        gramValues.put("PronType", List.of("Dem", "Ind", "Int", "Art", "Emp", "Prs"));
        gramValues.put("Polarity", List.of("Pos", "Neg"));
        gramValues.put("Mood", List.of("Imp"));
        gramValues.put("VerbType", List.of("Mod", "Cop"));
        gramValues.put("Voice", List.of("Pass", "Mid", "Act"));
        gramValues.put("Xtra", List.of("Junk"));
        gramValues.put("Definite", List.of("Cons", "Def"));
        gramValues.put("Prefix", List.of("Yes"));
        gramValues.put("Abbr", List.of("Yes"));
        gramValues.put("HebSource", List.of("ConvUncertainHead", "ConvUncertainLabel"));
        gramValues.put("HebExistential", List.of("True"));
    }

    public ComplexQueryCreator()
    {
        fillCheckboxes();
        this.add(tfLemma, tfPos, btnGrammar, vlGrammar, btnSearch);
        vlGrammar.setVisible(false);
        vlGrammar.setSpacing(false);
        vlGrammar.setMargin(false);
        vlGrammar.setPadding(false);
        btnSearch.addClickListener(event -> createQuery());
        btnGrammar.addClickListener(event -> manageGrammarWindow());
    }

    private void createQuery()
    {
        Map<String, Object> query = new HashMap<>();
        if (!tfLemma.getValue().isEmpty())
        {
            query.put(LEMMA, tfLemma.getValue());
        }
        if (!tfPos.getValue().isEmpty())
        {
            query.put(POS, tfPos.getValue());
        }
        if (!selectedGrammar.isEmpty())
        {
            query.put(GRAM, selectedGrammar);
        }
        if (query.isEmpty())
        {
            errorHandler.onError("The query is empty");
            return;
        }
        changeHandler.onQuery(query);
    }

    private void fillCheckboxes()
    {
        for (var gram : gramValues.entrySet())
        {
            var checkboxGroup = new CheckboxGroup<String>();
            checkboxGroup.setLabel(gram.getKey());
            checkboxGroup.setItems(gram.getValue());
            checkboxGroup.addValueChangeListener(event ->
            {
                String groupName = checkboxGroup.getLabel();
                if (selectedGrammar.containsKey(groupName))
                {
                    String oldSelection = selectedGrammar.get(groupName);
                    Optional<String> newSelection = event.getValue()
                            .stream()
                            .filter(item -> !item.equals(oldSelection))
                            .findFirst();
                    if (newSelection.isEmpty())
                    {
                        selectedGrammar.remove(groupName);
                        return;
                    }
                    checkboxGroup.setValue(Set.of(newSelection.get()));
                    selectedGrammar.put(groupName, newSelection.get());
                }
                else
                {
                    Optional<String> selectedItem = event.getValue().stream().findFirst();
                    if (selectedItem.isEmpty())
                    {
                        return;
                    }
                    selectedGrammar.put(groupName, selectedItem.get());
                }
            });
            vlGrammar.add(checkboxGroup);
        }
    }

    private void manageGrammarWindow()
    {
        if (btnGrammar.getText().equals(SHOW_GRAMMAR))
        {
            btnGrammar.setText(HIDE_GRAMMAR);
            vlGrammar.setVisible(true);
            return;
        }
        btnGrammar.setText(SHOW_GRAMMAR);
        vlGrammar.setVisible(false);
    }
}
