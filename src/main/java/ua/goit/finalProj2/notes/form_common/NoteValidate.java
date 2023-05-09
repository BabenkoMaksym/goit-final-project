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
    }

    private static void checkNoteName(Note note) throws NoteCreateException {
        if (!note.getName().matches(NAME_REGEX)) {
            throw new NoteCreateException("Довжина імені нотатки має бути не менше ніж "
                    + NAME_MIN_LENGTH + ", та не довше ніж "
                    + NAME_MAX_LENGTH + " символів");
        }
    }

    private static void checkNoteContent(Note note) throws NoteCreateException {
        if (!note.getContent().matches(CONTENT_REGEX)) {
            throw new NoteCreateException("Довжина тексту нотатки має бути не менше ніж "
                    + CONTENT_MIN_LENGTH + ", та не довше ніж "
                    + CONTENT_MAX_LENGTH + " симовлів");
        }
    }
}
