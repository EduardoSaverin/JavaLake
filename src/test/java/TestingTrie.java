import org.junit.Test;
import trie.PruningTrie;

import java.util.List;

public class TestingTrie {
    @Test
    public void checkForStoredData() {
        PruningTrie trie = new PruningTrie();
        trie.insert("java");
        assert trie.search("java") == Boolean.TRUE;
    }

    @Test
    public void checkForNonStoredData() {
        PruningTrie trie = new PruningTrie();
        trie.insert("java");
        assert trie.search("test") == Boolean.FALSE;
    }

    @Test
    public void getAllStoredDataFromTrie() {
        PruningTrie trie = new PruningTrie();
        trie.insert("java");
        trie.insert("Python");
        List<String> allStoredWords = trie.getAllStoredWords();
        assert allStoredWords.size() == 2;
    }
}
