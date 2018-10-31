package seedu.planner.ui;

import java.util.Collection;

import org.apache.poi.ss.formula.functions.T;
import org.controlsfx.control.textfield.AutoCompletionBinding;

import com.sun.javafx.event.EventHandlerManager;

import impl.org.controlsfx.autocompletion.SuggestionProvider;
import impl.org.controlsfx.skin.AutoCompletePopup;
import impl.org.controlsfx.skin.AutoCompletePopupSkin;
import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ObjectPropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.event.EventDispatchChain;
import javafx.event.EventHandler;
import javafx.event.EventTarget;
import javafx.event.EventType;
import javafx.scene.Node;
import javafx.scene.control.ListView;
import javafx.scene.control.Skin;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import javafx.util.StringConverter;

public class NewAutoCompletionBinding implements EventTarget {
    /***************************************************************************
     *                                                                         *
     * Private fields                                                          *
     *                                                                         *
     **************************************************************************/
    private static final long AUTO_COMPLETE_DELAY = 50;
    private final Node completionTarget;
    private final AutoCompletePopup<T> autoCompletionPopup;
    private final Object suggestionsTaskLock = new Object();

    private FetchSuggestionsTask suggestionsTask = null;
    private Callback<AutoCompletionBinding.ISuggestionRequest, Collection<T>> suggestionProvider = null;
    private boolean ignoreInputChanges = false;

    private String oldText = "";
    private final StringConverter<T> converter;

    /**
     * String converter to be used to convert suggestions to strings.
     */
    private static <T> StringConverter<T> defaultStringConverter() {
        return new StringConverter<T>() {
            @Override
            public String toString(T t) {
                return t == null ? null : t.toString();
            }

            @SuppressWarnings("unchecked")
            @Override
            public T fromString(String string) {
                return (T) string;
            }
        };
    }

    /***************************************************************************
     *                                                                         *
     * Constructors                                                            *
     *                                                                         *
     **************************************************************************/

    public NewAutoCompletionBinding(TextField textField,
                                    Callback<AutoCompletionBinding.ISuggestionRequest, Collection<T>> suggestionProvider) {
        this(textField, suggestionProvider, defaultStringConverter());
    }

    /**
     * Creates a new NewAutoCompletionBinding
     *
     * @param completionTarget The target node to which auto-completion shall be added
     * @param suggestionProvider The strategy to retrieve suggestions
     * @param converter The converter to be used to convert suggestions to strings
     */
    protected NewAutoCompletionBinding(Node completionTarget,
                                       Callback<AutoCompletionBinding.ISuggestionRequest, Collection<T>> suggestionProvider,
                                       StringConverter<T> converter){

        this.completionTarget = completionTarget;
        this.suggestionProvider = suggestionProvider;
        this.autoCompletionPopup = new AutoCompletePopup<>();
        this.converter = converter;
        this.autoCompletionPopup.setConverter(converter);

        autoCompletionPopup.setOnSuggestion(sce -> {
            try {
                setIgnoreInputChanges(true);
                completeUserInput(sce.getSuggestion());
                fireAutoCompletion(sce.getSuggestion());
                hidePopup();
            } finally {
                // Ensure that ignore is always set back to false
                setIgnoreInputChanges(false);
            }
        });

        getCompletionTarget().caretPositionProperty().addListener(caretChangeListener);
        getCompletionTarget().focusedProperty().addListener(focusChangedListener);
    }

    /***************************************************************************
     *                                                                         *
     * Public API                                                              *
     *                                                                         *
     **************************************************************************/

    /**
     * Specifies whether the PopupWindow should be hidden when an unhandled
     * escape key is pressed while the popup has focus.
     *
     * @param value
     */
    public void setHideOnEscape(boolean value) {
        autoCompletionPopup.setHideOnEscape(value);
    }

    /**
     * Set the current text the user has entered
     * @param userText
     */
    public final void setUserInput(String userText){
        if(!isIgnoreInputChanges()){
            onUserInputChanged(userText);
        }
    }

    /**
     * Gets the target node for auto completion
     * @return the target node for auto completion
     */
    public TextField getCompletionTarget() {
        return (TextField) completionTarget;
    }

    /**
     * Disposes the binding.
     */
    public void dispose() {
        getCompletionTarget().caretPositionProperty().removeListener(caretChangeListener);
        getCompletionTarget().focusedProperty().removeListener(focusChangedListener);
    }

    /***************************************************************************
     *                                                                         *
     * Popup Window Modifiers                                                  *
     *                                                                         *
     **************************************************************************/

    /**
     * Set the maximum number of rows to be visible in the popup when it is
     * showing.
     *
     * @param value
     */
    public final void setVisibleRowCount(int value) {
        autoCompletionPopup.setVisibleRowCount(value);
    }

    /**
     * Return the maximum number of rows to be visible in the popup when it is
     * showing.
     *
     * @return the maximum number of rows to be visible in the popup when it is
     * showing.
     */
    public final int getVisibleRowCount() {
        return autoCompletionPopup.getVisibleRowCount();
    }

    /**
     * Return an property representing the maximum number of rows to be visible
     * in the popup when it is showing.
     *
     * @return an property representing the maximum number of rows to be visible
     * in the popup when it is showing.
     */
    public final IntegerProperty visibleRowCountProperty() {
        return autoCompletionPopup.visibleRowCountProperty();
    }

    /**
     * Sets the prefWidth of the popup.
     *
     * @param value
     */
    public final void setPrefWidth(double value) {
        autoCompletionPopup.setPrefWidth(value);
    }

    /**
     * Return the pref width of the popup.
     *
     * @return the pref width of the popup.
     */
    public final double getPrefWidth() {
        return autoCompletionPopup.getPrefWidth();
    }

    /**
     * Return the property associated with the pref width.
     * @return
     */
    public final DoubleProperty prefWidthProperty() {
        return autoCompletionPopup.prefWidthProperty();
    }

    /**
     * Sets the minWidth of the popup.
     *
     * @param value
     */
    public final void setMinWidth(double value) {
        autoCompletionPopup.setMinWidth(value);
    }

    /**
     * Return the min width of the popup.
     *
     * @return the min width of the popup.
     */
    public final double getMinWidth() {
        return autoCompletionPopup.getMinWidth();
    }

    /**
     * Return the property associated with the min width.
     * @return
     */
    public final DoubleProperty minWidthProperty() {
        return autoCompletionPopup.minWidthProperty();
    }

    /**
     * Sets the maxWidth of the popup.
     *
     * @param value
     */
    public final void setMaxWidth(double value) {
        autoCompletionPopup.setMaxWidth(value);
    }

    /**
     * Return the max width of the popup.
     *
     * @return the max width of the popup.
     */
    public final double getMaxWidth() {
        return autoCompletionPopup.getMaxWidth();
    }

    /**
     * Return the property associated with the max width.
     * @return
     */
    public final DoubleProperty maxWidthProperty() {
        return autoCompletionPopup.maxWidthProperty();
    }

    /***************************************************************************
     *                                                                         *
     * Protected methods                                                       *
     *                                                                         *
     **************************************************************************/

    /**
     * Complete the current user-input with the provided completion.
     * Sub-classes have to provide a concrete implementation.
     * @param completion
     */
    protected void completeUserInput(T completion) {
        String newText = oldText + converter.toString(completion);
        getCompletionTarget().setText(newText);
        getCompletionTarget().positionCaret(newText.length());
    }

    /**
     * Show the auto completion popup
     */
    protected void showPopup(){
        autoCompletionPopup.show(completionTarget);
        selectFirstSuggestion(autoCompletionPopup);
    }

    /**
     * Hide the auto completion targets
     */
    protected void hidePopup(){
        autoCompletionPopup.hide();
    }

    protected void fireAutoCompletion(T completion){
        Event.fireEvent(this, new AutoCompletionEvent<>(completion));
    }


    /***************************************************************************
     *                                                                         *
     * Private methods                                                         *
     *                                                                         *
     **************************************************************************/

    /**
     * Selects the first suggestion (if any), so the user can choose it
     * by pressing enter immediately.
     */
    private void selectFirstSuggestion(AutoCompletePopup<?> autoCompletionPopup){
        Skin<?> skin = autoCompletionPopup.getSkin();
        if(skin instanceof AutoCompletePopupSkin){
            AutoCompletePopupSkin<?> au = (AutoCompletePopupSkin<?>)skin;
            ListView<?> li = (ListView<?>)au.getNode();
            if(li.getItems() != null && !li.getItems().isEmpty()){
                li.getSelectionModel().select(0);
            }
        }
    }

    /**
     * Occurs when the user text has changed and the suggestions require an update
     * @param userText
     */
    private final void onUserInputChanged(final String userText){
        synchronized (suggestionsTaskLock) {
            if(suggestionsTask != null && suggestionsTask.isRunning()){
                // cancel the current running task
                suggestionsTask.cancel();
            }
            // create a new fetcher task
            suggestionsTask = new FetchSuggestionsTask(userText);
            new Thread(suggestionsTask).start();
        }
    }

    /**
     * Shall changes to the user input be ignored?
     * @return
     */
    private boolean isIgnoreInputChanges(){
        return ignoreInputChanges;
    }

    /**
     * If IgnoreInputChanges is set to true, all changes to the user input are
     * ignored. This is primary used to avoid self triggering while
     * auto completing.
     * @param state
     */
    private void setIgnoreInputChanges(boolean state){
        ignoreInputChanges = state;
    }

    /***************************************************************************
     *                                                                         *
     * Inner classes and interfaces                                            *
     *                                                                         *
     **************************************************************************/

    /**
     * This task is responsible to fetch suggestions asynchronous
     * by using the current defined suggestionProvider
     *
     */
    private class FetchSuggestionsTask extends Task<Void> implements AutoCompletionBinding.ISuggestionRequest {
        private final String userText;

        public FetchSuggestionsTask(String userText){
            this.userText = userText;
        }

        @Override
        protected Void call() throws Exception {
            Callback<AutoCompletionBinding.ISuggestionRequest, Collection<T>> provider = suggestionProvider;
            if(provider != null){
                long start_time = System.currentTimeMillis();
                final Collection<T> fetchedSuggestions = provider.call(this);
                long sleep_time = start_time + AUTO_COMPLETE_DELAY - System.currentTimeMillis();
                if (sleep_time > 0 && !isCancelled()) {
                    Thread.sleep(sleep_time);
                }
                if(!isCancelled()) {
                    Platform.runLater(() -> {
                        if(fetchedSuggestions != null && !fetchedSuggestions.isEmpty()) {
                            autoCompletionPopup.getSuggestions().setAll(fetchedSuggestions);
                            showPopup();
                        } else {
                            // No suggestions found, so hide the popup
                            hidePopup();
                        }
                    });
                }
            } else {
                hidePopup();
            }
            return null;
        }

        @Override
        public String getUserText() {
            return userText;
        }
    }

    /***************************************************************************
     *                                                                         *
     * Listeners                                                               *
     *                                                                         *
     **************************************************************************/

    private final ChangeListener<Number> caretChangeListener = (obs, oldNumber, newNumber) -> {
        String text = getCompletionTarget().getText().substring(0, newNumber.intValue());
        int index;
        for (index = text.length() - 1; index >= 0 && !Character.isWhitespace(text.charAt(index)); index--);
        if (index > 0) {
            oldText = text.substring(0, index) + " ";
        } else {
            oldText = "";
        }

        String newText = text.substring(index + 1);
        if (getCompletionTarget().isFocused()) {
            setUserInput(newText); // updates the input text to new user input
        }
    };

    private final ChangeListener<Boolean> focusChangedListener = (obs, oldFocused, newFocused) -> {
        if (newFocused == false) {
            hidePopup();
        }
    };


    /***************************************************************************
     *                                                                         *
     * Events                                                                  *
     *                                                                         *
     **************************************************************************/

    /**
     * Represents an Event which is fired after an auto completion.
     */
    @SuppressWarnings("serial")
    public static class AutoCompletionEvent<TE> extends Event {

        /**
         * The event type that should be listened to by people interested in
         * knowing when an auto completion has been performed.
         */
        @SuppressWarnings("rawtypes")
        public static final EventType<AutoCompletionEvent> AUTO_COMPLETED = new EventType<>("AUTO_COMPLETED"); //$NON-NLS-1$

        private final TE completion;

        /**
         * Creates a new event that can subsequently be fired.
         */
        public AutoCompletionEvent(TE completion) {
            super(AUTO_COMPLETED);
            this.completion = completion;
        }

        /**
         * Returns the chosen completion.
         */
        public TE getCompletion() {
            return completion;
        }
    }


    private ObjectProperty<EventHandler<AutoCompletionEvent<T>>> onAutoCompleted;

    /**
     * Set a event handler which is invoked after an auto completion.
     * @param value
     */
    public final void setOnAutoCompleted(EventHandler<AutoCompletionEvent<T>> value) {
        onAutoCompletedProperty().set( value);
    }

    public final EventHandler<AutoCompletionEvent<T>> getOnAutoCompleted() {
        return onAutoCompleted == null ? null : onAutoCompleted.get();
    }

    public final ObjectProperty<EventHandler<AutoCompletionEvent<T>>> onAutoCompletedProperty() {
        if (onAutoCompleted == null) {
            onAutoCompleted = new ObjectPropertyBase<EventHandler<AutoCompletionEvent<T>>>() {
                @SuppressWarnings({ "rawtypes", "unchecked" })
                @Override protected void invalidated() {
                    eventHandlerManager.setEventHandler(
                            AutoCompletionEvent.AUTO_COMPLETED,
                            (EventHandler<AutoCompletionEvent>)(Object)get());
                }

                @Override
                public Object getBean() {
                    return this;
                }

                @Override
                public String getName() {
                    return "onAutoCompleted"; //$NON-NLS-1$
                }
            };
        }
        return onAutoCompleted;
    }


    /***************************************************************************
     *                                                                         *
     * EventTarget Implementation                                              *
     *                                                                         *
     **************************************************************************/

    final EventHandlerManager eventHandlerManager = new EventHandlerManager(this);

    /**
     * Registers an event handler to this EventTarget. The handler is called when the
     * menu item receives an {@code Event} of the specified type during the bubbling
     * phase of event delivery.
     *
     * @param <E> the specific event class of the handler
     * @param eventType the type of the events to receive by the handler
     * @param eventHandler the handler to register
     * @throws NullPointerException if the event type or handler is null
     */
    public <E extends Event> void addEventHandler(EventType<E> eventType, EventHandler<E> eventHandler) {
        eventHandlerManager.addEventHandler(eventType, eventHandler);
    }

    /**
     * Unregisters a previously registered event handler from this EventTarget. One
     * handler might have been registered for different event types, so the
     * caller needs to specify the particular event type from which to
     * unregister the handler.
     *
     * @param <E> the specific event class of the handler
     * @param eventType the event type from which to unregister
     * @param eventHandler the handler to unregister
     * @throws NullPointerException if the event type or handler is null
     */
    public <E extends Event> void removeEventHandler(EventType<E> eventType, EventHandler<E> eventHandler) {
        eventHandlerManager.removeEventHandler(eventType, eventHandler);
    }

    /** {@inheritDoc} */
    @Override public EventDispatchChain buildEventDispatchChain(EventDispatchChain tail) {
        return tail.prepend(eventHandlerManager);
    }


}
