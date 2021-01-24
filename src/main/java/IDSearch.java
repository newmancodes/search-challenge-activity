import models.SearchNode;
import models.SearchResult;

import java.util.ArrayList;

public class IDSearch {
    private ArrayList<SearchNode> frontier;
    private ArrayList<SearchNode> explored;

    public SearchResult iterativeDeepening(int[][] grid) {
        for (byte depth = 0; depth < 127; depth++) {
            frontier = new ArrayList<SearchNode>();
            explored = new ArrayList<SearchNode>();
            SearchResult result = depthLimitedSearch(grid, depth);
            if (result != SearchResult.Cutoff) {
                return result;
            }
        }

        return SearchResult.Cutoff;
    }

    private SearchResult depthLimitedSearch(int[][] grid, byte limit) {
        SearchNode initialState = null;

        for (byte x = 0; x < 15; x++) {
            for (byte y = 0; y < 15; y++) {
                if (grid[x][y] == 2) {
                    // Located start location.
                    initialState = new SearchNode(x, y, (byte)0);
                    frontier.add(initialState);
                    return recursiveDepthLimitedSearch(initialState, grid, limit);
                }
            }
        }

        return SearchResult.Failure;
    }

    private SearchResult recursiveDepthLimitedSearch(SearchNode searchNode, int[][] grid, byte limit) {
        if (grid[searchNode.getX()][searchNode.getY()] == 3) {
            return SearchResult.Solved;
        } else if (limit == 0) {
            return SearchResult.Cutoff;
        } else {
            boolean cutOffOccurred = false;
            for (SearchNode expandedSearchNode: searchNode.expand()) {

            }
            if (cutOffOccurred) {
                return SearchResult.Cutoff;
            } else {
                return SearchResult.Failure;
            }
        }
    }
}
