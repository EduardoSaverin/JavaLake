package trie;

import trie.interfaces.Trie;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PruningTrie implements Trie {

    private Node root;

    public PruningTrie() {
        this(new Node());
    }

    public PruningTrie(Node root) {
        this.root = root;
    }

    @Override
    public void insert(String text) {
        if (Objects.isNull(text) || "".equals(text)) {
            root.setText(Boolean.TRUE);
            return;
        }
        // Extract first character and rest of substring
        char s = text.charAt(0);
        String tail = text.substring(1);
        if (!root.getChildren().containsKey(s)) {
            Node newNode = new Node();
            root.getChildren().put(s, newNode);
        }
        makeTree(root.getChildren().get(s), tail);
    }

    private void makeTree(Node node, String text) {
        if ("".equals(text)) {
            if (node.isText()) {
                // Already Exists Condition
            }
            node.setOccurrences(node.getOccurrences()+1);
            node.setText(Boolean.TRUE);
            return;
        }
        // Extract first character and rest of substring
        char s = text.charAt(0);
        String tail = text.substring(1);
        if (!node.getChildren().containsKey(s)) {
            Node newNode = new Node();
            node.getChildren().put(s, newNode);
        }
        makeTree(node.getChildren().get(s), tail);
    }

    @Override
    public void delete(String text) {

    }

    @Override
    public Node getNode(String text) {
        return searchNode(root, text);
    }

    @Override
    public boolean search(String text) {
        if (Objects.isNull(text) || "".equals(text)) {
            return Boolean.TRUE;
        }
        Node node = searchNode(root, text);
        if (Objects.isNull(node) || !node.isText()) {
            return Boolean.FALSE;
        }
        return Boolean.TRUE;
    }

    @Override
    public List<String> getAllStoredWords() {
        List<String> storedTexts = new ArrayList<>();
        for(Character character : root.getChildren().keySet()) {
            storedTexts.addAll(treeDSFTravel(root.getChildren().get(character), character.toString()));
        }
        return storedTexts;
    }

    public List<String> treeDSFTravel(Node node, String text) {
        List<String> texts = new ArrayList<>();
        if (Objects.isNull(node) || node.isText()) {
            return Collections.singletonList(text);
        }
        for (Character character : node.getChildren().keySet()) {
            texts.addAll(treeDSFTravel(node.getChildren().get(character), text + character.toString()));
        }
        return texts;
    }

    private Node searchNode(Node node, String text) {
        if ("".equals(text) || Objects.isNull(node)) {
            return node;
        }
        char s = text.charAt(0);
        return searchNode(node.getChildren().get(s), text.substring(1));
    }
}
