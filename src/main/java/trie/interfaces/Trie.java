package trie.interfaces;

import trie.Node;

import java.util.List;

public interface Trie {
    public void insert(String text);
    public void delete(String text);
    public Node getNode(String text);
    public boolean search(String text);
    public List<String> getAllStoredWords();
}
