package com.gosha.kalosha.hauzijan.view;

import com.gosha.kalosha.hauzijan.dto.SentenceDto;
import com.gosha.kalosha.hauzijan.exception_handing.NoSentencesFoundException;
import com.gosha.kalosha.hauzijan.view.component.ComplexQueryCreator;
import com.gosha.kalosha.hauzijan.view.model.SentenceRow;
import com.gosha.kalosha.hauzijan.view.model.WordRow;
import com.gosha.kalosha.hauzijan.service.SentenceService;
import com.gosha.kalosha.hauzijan.service.WordService;
import com.gosha.kalosha.hauzijan.view.component.SimpleQueryCreator;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import java.util.List;
import java.util.Map;

@Route("query")
public class MainView extends VerticalLayout
{
    private final WordService wordService;

    private final SentenceService sentenceService;

    private final HorizontalLayout hlQueryCreators = new HorizontalLayout();

    private final Grid<WordRow> wordGrid = new Grid<>(WordRow.class);

    private final Grid<SentenceRow> sentenceGrid = new Grid<>(SentenceRow.class);

    private final Label errorLabel = new Label("");

    private final SimpleQueryCreator simpleQueryCreator;

    private final ComplexQueryCreator complexQueryCreator;

    public MainView(WordService wordService, SentenceService sentenceService, SimpleQueryCreator simpleQueryCreator, ComplexQueryCreator complexQueryCreator)
    {
        this.wordService = wordService;
        this.sentenceService = sentenceService;
        this.simpleQueryCreator = simpleQueryCreator;
        this.complexQueryCreator = complexQueryCreator;
        setUpUI();
    }

    private void setUpUI()
    {
        simpleQueryCreator.setAlignItems(Alignment.END);
        complexQueryCreator.setAlignItems(Alignment.START);
        simpleQueryCreator.setChangeHandler(this::fetchSentences);
        complexQueryCreator.setChangeHandler(this::fetchSentences);

        hlQueryCreators.add(simpleQueryCreator, complexQueryCreator);
        hlQueryCreators.setWidth("100%");

        errorLabel.getStyle().set("color", "red");
        errorLabel.setVisible(false);

        sentenceGrid.setHeight("0%");
        sentenceGrid.getColumns().forEach(sentenceGrid::removeColumn);
        sentenceGrid.addColumn(SentenceRow::getSentence).setHeader("Sentence");

        wordGrid.setHeight("0%");
        wordGrid.getColumns().forEach(wordGrid::removeColumn);
        wordGrid.addColumn(WordRow::getWord).setHeader("Word").setWidth("15%").setSortable(false);
        wordGrid.addColumn(WordRow::getLemma).setHeader("Lemma").setWidth("15%").setSortable(false);
        wordGrid.addColumn(WordRow::getPos).setHeader("Pos").setWidth("10%").setSortable(false);
        wordGrid.addColumn(WordRow::getGrammar).setHeader("Grammar").setWidth("60%").setSortable(false);
        wordGrid.getStyle().set("word-wrap", "break-word");
        wordGrid.getStyle().set("white-space", "normal");

        this.add(errorLabel, hlQueryCreators, sentenceGrid, wordGrid);
        this.setAlignItems(Alignment.CENTER);
    }

    private void fetchSentences(String query)
    {
        try
        {
            List<SentenceDto> sentences = sentenceService.getBySimpleQuery(query);
            listSentences(sentences);
        }
        catch (NoSentencesFoundException e)
        {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    private void fetchSentences(Map<String, Object> query)
    {
        try
        {
            List<SentenceDto> sentences = sentenceService.getByParameters(query);
            listSentences(sentences);
        }
        catch (NoSentencesFoundException | IllegalArgumentException e)
        {
            errorLabel.setText(e.getMessage());
            errorLabel.setVisible(true);
        }
    }

    private void listSentences(List<SentenceDto> sentences)
    {
        errorLabel.setVisible(false);
        sentenceGrid.setItems(sentences.stream().map(SentenceRow::fromDto).toList());
        sentenceGrid.setHeightByRows(true);
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
