package seedu.address.model;

import java.util.HashMap;
import java.util.Map;

import seedu.address.model.person.Slot;

/**
 * A list of tutorials.
 */
public class TutorialList {
    private Map<Tutorial, Slot> Tutorials;
    public TutorialList() {
        this.Tutorials = new HashMap<>();
    }
    public Tutorial getTutorial(Slot slot) {
        return Tutorials.entrySet().stream()
                .filter(entry -> entry.getValue().equals(slot))
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);
    }
}
