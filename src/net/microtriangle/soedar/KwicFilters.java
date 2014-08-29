package net.microtriangle.soedar;

import net.microtriangle.soedar.eventmanager.EventCallback;
import net.microtriangle.soedar.eventmanager.EventManager;
import net.microtriangle.soedar.filters.*;

import java.util.Collection;

/**
 * Created by soedar on 30/8/14.
 */
public class KwicFilters {
    private final EventManager eventManager;
    private final Collection<String> ignoredWords;

    public KwicFilters(EventManager eventManager, Collection<String> ignoredWords) {
        this.eventManager = eventManager;
        this.ignoredWords = ignoredWords;
    }

    public void setup() {
        Filter circularShiftFilter = new Filter(eventManager, new CircularShift());
        Filter convertCaseFilter = new Filter(eventManager, new ConvertCase(ignoredWords));
        Filter dropTitleFilter = new Filter(eventManager, new DropTitles(ignoredWords));
        Filter sorterFilter = new Filter(eventManager, new Sorter());

        circularShiftFilter.addInputEvent(KwicEvent.INPUT);
        circularShiftFilter.addOutputEvent(KwicEvent.CIRCULAR_SHIFT_OUTPUT);

        convertCaseFilter.addInputEvent(KwicEvent.CIRCULAR_SHIFT_OUTPUT);
        convertCaseFilter.addOutputEvent(KwicEvent.CONVERT_CASE_OUTPUT);

        dropTitleFilter.addInputEvent(KwicEvent.CONVERT_CASE_OUTPUT);
        dropTitleFilter.addOutputEvent(KwicEvent.DROP_TITLES_OUTPUT);

        sorterFilter.addInputEvent(KwicEvent.DROP_TITLES_OUTPUT);
        sorterFilter.addOutputEvent(KwicEvent.SORTER_OUTPUT);

        sorterFilter.addOutputEvent(KwicEvent.OUTPUT);
    }
}
