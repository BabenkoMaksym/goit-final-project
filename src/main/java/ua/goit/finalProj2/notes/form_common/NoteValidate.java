package ua.goit.finalProj2.notes.form_common;

import ua.goit.finalProj2.notes.Note;

public class NoteValidate {
    private static final int NAME_MIN_LENGTH = 5;
    private static final int NAME_MAX_LENGTH = 100;
    private static final int CONTENT_MIN_LENGTH = 5;
    private static final int CONTENT_MAX_LENGTH = 10000;
    private static final String NAME_REGEX = "^[\\s\\S]{"
            + NAME_MIN_LENGTH + "," + NAME_MAX_LENGTH + "}$";

    private static final String CONTENT_REGEX = "^[\\s\\S]{"
            + CONTENT_MIN_LENGTH + "," + CONTENT_MAX_LENGTH + "}$";

    public static void validateNoteCreating(Note note) throws NoteCreateException {
        checkNoteName(note);
        checkNoteContent(note);
        checkNoteKeyWords(note);
    }

    private static void checkNoteName(Note note) throws NoteCreateException {
        if (!note.getName().matches(NAME_REGEX)) {
            throw new NoteCreateException("The note name must be at least "
                    + NAME_MIN_LENGTH + " and no longer than "
                    + NAME_MAX_LENGTH + "characters long");
        }
    }

    private static void checkNoteContent(Note note) throws NoteCreateException {
        if (!note.getContent().matches(CONTENT_REGEX)) {
            throw new NoteCreateException("The length of the note text should be no less than "
                    + CONTENT_MIN_LENGTH + " and no longer than "
                    + CONTENT_MAX_LENGTH + " characters");
        }
    }
    private static void checkNoteKeyWords(Note note) throws NoteCreateException{
        //todo
    }
}
