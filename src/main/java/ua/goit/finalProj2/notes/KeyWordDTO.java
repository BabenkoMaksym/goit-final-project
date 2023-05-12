package ua.goit.finalProj2.notes;

import java.util.Arrays;
import java.util.List;

public class KeyWordDTO {
    String words;
    public List<String> getKeyWords(){
        return Arrays.asList(words.split(", "));
    }
}
