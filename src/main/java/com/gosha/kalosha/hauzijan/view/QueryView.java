package com.gosha.kalosha.hauzijan.view;

import com.gosha.kalosha.hauzijan.model.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.exception_handing.NoSentencesFoundException;
import com.gosha.kalosha.hauzijan.service.SentenceService;
import com.gosha.kalosha.hauzijan.service.WordService;
import com.gosha.kalosha.hauzijan.view.component.ComplexQueryCreator;
import com.gosha.kalosha.hauzijan.view.component.SimpleQueryCreator;
import com.gosha.kalosha.hauzijan.view.model.SentenceRow;
import com.gosha.kalosha.hauzijan.view.model.WordRow;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Route("query")
public class QueryView extends VerticalLayout
{
    private final WordService wordService;
    private final SentenceService sentenceService;

    private final HorizontalLayout hlPaging = new HorizontalLayout();
    private final Button btnPrevious = new Button("Previous", VaadinIcon.ARROW_LEFT.create());
    private final Button btnNext = new Button("Next", VaadinIcon.ARROW_RIGHT.create());
    private final Label pageLabel = new Label("");

    private final Grid<WordRow> wordGrid = new Grid<>(WordRow.class);
    private final Grid<SentenceRow> sentenceGrid = new Grid<>(SentenceRow.class);

    private final Label errorLabel = new Label("");

    private final HorizontalLayout hlQueryCreators = new HorizontalLayout();
    private final SimpleQueryCreator simpleQueryCreator;
    private final ComplexQueryCreator complexQueryCreator;

    private Object lastQuery;
    private int lastPage = 1;
    private int displayedSentencesCount;

    private final Map<Class<?>, Supplier<List<SentenceDto>>> serviceQueryMethods = new HashMap<>();

    public QueryView(WordService wordService, SentenceService sentenceService,
                     SimpleQueryCreator simpleQueryCreator, ComplexQueryCreator complexQueryCreator)
    {
        this.wordService = wordService;
        this.sentenceService = sentenceService;
        this.simpleQueryCreator = simpleQueryCreator;
        this.complexQueryCreator = complexQueryCreator;
        setUpUI();
    }

    @PostConstruct
    private void fillQueryMethodsMap()
    {
        serviceQueryMethods.put(String.class, () -> sentenceService.getBySimpleQuery((String) lastQuery, lastPage));
        serviceQueryMethods.put(HashMap.class, () -> sentenceService.getByParameters((Map<String, Object>) lastQuery, lastPage));
    }

    private void setUpUI()
    {
        errorLabel.getStyle().set("color", "red");
        errorLabel.setVisible(false);

        setUpQueryCreators();
        setUpGrids();
        setUpPaging();

        this.setAlignItems(Alignment.CENTER);
        this.setHorizontalComponentAlignment(Alignment.CENTER, hlPaging);
        this.add(errorLabel, hlQueryCreators, sentenceGrid, hlPaging, wordGrid);
        this.setAlignItems(Alignment.CENTER);
    }

    private void setUpQueryCreators()
    {
        simpleQueryCreator.setAlignItems(Alignment.END);
        simpleQueryCreator.setChangeHandler(this::executeQuery);
        simpleQueryCreator.setErrorHandler(this::showErrorMessage);
        simpleQueryCreator.setMargin(false);
        simpleQueryCreator.setPadding(false);

        complexQueryCreator.setAlignItems(Alignment.START);
        complexQueryCreator.setChangeHandler(this::executeQuery);
        complexQueryCreator.setErrorHandler(this::showErrorMessage);
        complexQueryCreator.setSpacing(false);
        complexQueryCreator.setMargin(false);
        complexQueryCreator.setPadding(false);

        hlQueryCreators.add(simpleQueryCreator, complexQueryCreator);
        hlQueryCreators.setWidth("100%");
    }

    private void setUpGrids()
    {
        sentenceGrid.setHeight("0%");
        sentenceGrid.getColumns().forEach(sentenceGrid::removeColumn);
        sentenceGrid.addColumn(SentenceRow::getSentence).setHeader("Sentence");

        wordGrid.setHeight("0%");
        wordGrid.getColumns().forEach(wordGrid::removeColumn);
        wordGrid.addColumn(WordRow::getWord).setHeader("Word").setWidth("10%").setSortable(false);
        wordGrid.addColumn(WordRow::getLemma).setHeader("Lemma").setWidth("10%").setSortable(false);
        wordGrid.addColumn(WordRow::getPos).setHeader("Pos").setWidth("10%").setSortable(false);
        wordGrid.addColumn(WordRow::getGrammar).setHeader("Grammar").setWidth("70%").setSortable(false);
    }

    private void setUpPaging()
    {
        hlPaging.setVisible(false);
        hlPaging.add(btnPrevious, pageLabel, btnNext);
        hlPaging.setAlignItems(Alignment.CENTER);

        btnPrevious.addClickListener(event ->
        {
            if (lastPage == 1)
            {
                return;
            }
            --lastPage;
            openNewPage();
        });
        btnNext.addClickListener(event ->
        {
            if (displayedSentencesCount != 20)
            {
                return;
            }
            ++lastPage;
            openNewPage();
        });
    }

    private void openNewPage()
    {
        pageLabel.setText(Integer.toString(lastPage));
        fetchSentences();
    }

    private void executeQuery(Object query)
    {
        lastPage = 1;
        pageLabel.setText(Integer.toString(lastPage));
        lastQuery = query;
        fetchSentences();
    }

    private void fetchSentences()
    {
        try
        {
            List<SentenceDto> sentences = serviceQueryMethods.get(lastQuery.getClass()).get();
            listSentences(sentences);
        }
        catch (NoSentencesFoundException | IllegalArgumentException e)
        {
            showErrorMessage(e.getMessage());
        }
    }

    private void showErrorMessage(String message)
    {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
    }

    private void listSentences(List<SentenceDto> sentences)
    {
        displayedSentencesCount = sentences.size();
        errorLabel.setVisible(false);
        sentenceGrid.setItems(sentences.stream().map(SentenceRow::fromDto).toList());
        sentenceGrid.setHeightByRows(true);
        hlPaging.setVisible(true);
        sentenceGrid.addItemClickListener(event ->
        {
            long sentenceId = event.getItem().getId();
            listWords(sentenceId);
        });
    }

    private void listWords(long id)
    {
        try
        {
            errorLabel.setVisible(false);
            wordGrid.setItems(wordService.getWordlist(id).stream().map(WordRow::fromDto).toList());
            wordGrid.setHeightByRows(true);
        }
        catch (NoSentencesFoundException e)
        {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
    }
}
