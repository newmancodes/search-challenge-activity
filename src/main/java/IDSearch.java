import models.SearchNode;
import models.SearchResult;

public class IDSearch {
    public SearchResult iterativeDeepening(int[][] grid) {
        for (byte depth = 0; depth < 127; depth++) {
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
                    initialState = new SearchNode(x, y, (byte)0);
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
            Boolean cutOffOccurred = false;
            if (cutOffOccurred) {
                return SearchResult.Cutoff;
            } else {
                return SearchResult.Failure;
            }
        }
    }
}
