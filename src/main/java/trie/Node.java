package trie;

import java.util.HashMap;
import java.util.Map;

public class Node {
    private Map<Character, Node> children = new HashMap<>();
    private boolean isText;
    private int occurrences;

    public Map<Character, Node> getChildren() {
        return children;
    }

    public boolean isText() {
        return isText;
    }

    public void setText(boolean text) {
        isText = text;
    }

    public int getOccurrences() {
        return occurrences;
    }

    public void setOccurrences(int occurrences) {
        this.occurrences = occurrences;
    }
}
