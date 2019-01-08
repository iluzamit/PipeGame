package Server.searcher_interface;

import java.util.ArrayList;

public interface Searcher<T> {
    Solution search(Searchable<T> searchable);
}