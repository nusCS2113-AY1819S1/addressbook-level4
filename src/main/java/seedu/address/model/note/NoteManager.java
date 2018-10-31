package seedu.address.model.note;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.logic.commands.NoteDeleteCommand;
import seedu.address.model.StorageController;
import seedu.address.storage.adapter.XmlAdaptedNote;
import seedu.address.ui.HtmlCardProcessor;

/**
 * Represents the in-memory model of the Note data.
 */
public class NoteManager {

    public static final String NOTE_PAGE_IDENTIFIER = "<!-- NOTE -->";

    private static final ArrayList<String> NOTE_CSV_HEADERS =
            new ArrayList<>(Arrays.asList(
                    "subject", "startDate", "startTime", "endDate", "endTime", "description", "location"
            ));

    private static final int FILTERED_BY_MODULE_CODE = 1;
    private static final int FILTERED_BY_KEYWORDS = 2;
    private static final int NO_FILTER = 0;

    private static NoteManager noteManager = null;

    private ArrayList<Note> notes = new ArrayList<>();
    private List<Note> filteredNotes;

    private String currentFilter = "";
    private int currentFilterState = 0; // 0 = all, 1 = by moduleCode, 2 = by keywords

    private NoteManager() {
        readNoteList();
        filteredNotes = notes;
    }

    public static NoteManager getInstance() {
        if (noteManager == null) {
            noteManager = new NoteManager();
        }
        return noteManager;
    }

    /**
     * Adds the new note to the in-memory ArrayList.
     */
    public void addNote(Note note) {
        requireNonNull(note);
        notes.add(note);

        refreshFilteredNotes();
    }

    /**
     * Deletes the specified note from the in-memory ArrayList.
     * It also updates the filteredNotes list.
     */
    public void deleteNote(int index) {
        if (index >= filteredNotes.size()) {
            throw new IndexOutOfBoundsException(NoteDeleteCommand.MESSAGE_INVALID_INDEX);
        }
        notes.remove(getNoteAt(index));

        refreshFilteredNotes();
    }

    public String getHtmlNoteList() {
        StringBuilder sb = new StringBuilder();

        int listId = 1;

        sb.append(NOTE_PAGE_IDENTIFIER);
        sb.append("\n");
        for (Note note: filteredNotes) {
            sb.append(HtmlCardProcessor.getCardStart());

            if (note.getTitle().toString().trim().isEmpty()) {
                sb.append(HtmlCardProcessor.renderCardHeader(
                        "h4", "#" + listId + "&nbsp;&nbsp;"
                                + HtmlCardProcessor.CARD_NO_TITLE));
            } else {
                sb.append(HtmlCardProcessor.renderCardHeader(
                        "h4", "#" + listId + "&nbsp;&nbsp;"
                                + HtmlCardProcessor.adaptToHtml(note.getTitle().toString())));
            }

            sb.append(HtmlCardProcessor.getCardBodyStart());
            sb.append(HtmlCardProcessor.renderCardTitle(note.getModuleCode().toString()));
            if (note.getStartDate() != null) {
                sb.append(HtmlCardProcessor.renderCardSubtitle(
                        "From " + note.getStartDate() + " " + note.getStartTime()
                                + " to " + note.getEndDate() + " " + note.getEndTime()));
            }
            sb.append(HtmlCardProcessor.renderCardSubtitle(HtmlCardProcessor
                    .adaptToHtml(note.getLocation().toString())));
            sb.append(HtmlCardProcessor.renderCardText(HtmlCardProcessor
                    .adaptToHtml(note.getNoteText().toString())));
            sb.append(HtmlCardProcessor.getDivEndTag()); // end of card-body

            sb.append(HtmlCardProcessor.getDivEndTag()); // end of card

            listId++;
        }

        return sb.toString();
    }

    /**
     * Retrieves the Note object at the specified {@code index}.
     *
     * @param index index of the element to retrieve from the filteredNotes
     * @return Note object at the specified index, or null if index is out of bounds.
     */
    public Note getNoteAt(int index) {
        Note noteToGet;

        try {
            noteToGet = filteredNotes.get(index);
            return noteToGet;
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Gets the note list from storage and converts it to a Notes array list.
     */
    private void readNoteList() {
        ArrayList<XmlAdaptedNote> xmlNoteList = StorageController.getNoteStorage();
        for (XmlAdaptedNote xmlNote : xmlNoteList) {
            notes.add(xmlNote.toModelType());
        }
    }

    /**
     * Converts the Note array list and invokes the StorageController to save the current note list to file.
     */
    public void saveNoteList() {
        ArrayList<XmlAdaptedNote> xmlAdaptedNotes =
                notes.stream().map(XmlAdaptedNote::new).collect(Collectors.toCollection(ArrayList::new));
        StorageController.setNoteStorage(xmlAdaptedNotes);
        StorageController.storeData();
    }

    public ArrayList<Note> getNotes() {
        return this.notes;
    }

    public void setFilteredNotesNoFilter() {
        filteredNotes = notes;
        currentFilter = "";
        currentFilterState = NO_FILTER;
    }

    /**
     * Filter all notes that do not match the given {@code moduleCode} parameter.
     *
     * @param moduleCode contains the Module Code to be compared with the note's moduleCode attribute
     */
    public void setFilteredNotesByModuleCode(String moduleCode) {
        requireNonNull(moduleCode);
        String trimmedModuleCode = moduleCode.trim();

        if (!trimmedModuleCode.isEmpty()) {
            filteredNotes = notes.stream()
                    .filter(n -> n.getModuleCode().toString().equalsIgnoreCase(trimmedModuleCode))
                    .collect(Collectors.toList());

            if (filteredNotes.size() > 0) {
                currentFilter = moduleCode;
                currentFilterState = FILTERED_BY_MODULE_CODE;
            }
        } else {
            setFilteredNotesNoFilter();
        }
    }

    /**
     * Filter all notes that do not match the given {@code matcher} parameter.
     *
     * @param matcher contains the RegEx string for finding keyword(s)
     *                from a Note object's {@code title} or {@code noteText}
     */
    public void setFilteredNotesByKeyword(String matcher) {
        requireNonNull(matcher);

        filteredNotes = notes.stream()
                .filter(n -> n.getNoteText().toString().replaceAll("(\r\n|\n)", " ")
                        .matches(matcher)
                        || n.getTitle().toString()
                        .matches(matcher))
                .collect(Collectors.toList());

        if (filteredNotes.size() > 0) {
            currentFilter = matcher;
            currentFilterState = FILTERED_BY_KEYWORDS;
        }
    }

    /**
     * Rebuilds the viewable notes list depending on the latest
     * non-empty resulting filter.
     */
    public void refreshFilteredNotes() {
        switch (currentFilterState) {
        case NO_FILTER:
            setFilteredNotesByModuleCode("");
            break;
        case FILTERED_BY_MODULE_CODE:
            setFilteredNotesByModuleCode(currentFilter);
            break;
        case FILTERED_BY_KEYWORDS:
            setFilteredNotesByKeyword(currentFilter);
            break;
        default:
            setFilteredNotesByModuleCode("");
        }
    }

    public List<Note> getFilteredNotes() {
        return filteredNotes;
    }

    /**
     * Removes all elements in notes list.
     */
    public void clearNotes() {
        notes.clear();
        filteredNotes.clear();
        currentFilter = "";
    }

    public ArrayList<String> getCsvHeaders() {
        return NOTE_CSV_HEADERS;
    }

    public ArrayList<Note> getExportableNotes() {
        ArrayList<Note> exportableNotes;

        exportableNotes = notes.stream()
                .filter(p -> (p.getStartDate() != null)).collect(Collectors.toCollection(ArrayList::new));

        return exportableNotes;
    }

    /**
     * Deletes all notes that contains the {@code moduleCode}.
     */
    public static void deleteNotesByModuleCode(String moduleCode) {
        noteManager.getNotes().removeIf(n -> n.getModuleCode().toString().equalsIgnoreCase(moduleCode));
        noteManager.refreshFilteredNotes();
    }
}
