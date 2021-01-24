import models.SearchNode;
import models.SearchResult;

import java.awt.*;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IDSearch {
    private Logger logger = LoggerFactory.getLogger(IDSearch.class);
    private ArrayList<SearchNode> frontier;
    private ArrayList<SearchNode> explored;

    public SearchResult iterativeDeepening(int[][] grid) {
        for (int depth = 0; depth < 500; depth++) {
            frontier = new ArrayList<SearchNode>();
            explored = new ArrayList<SearchNode>();
            SearchResult result = depthLimitedSearch(grid, depth);
            if (result != SearchResult.Cutoff) {
                return result;
            }
        }

        return SearchResult.Cutoff;
    }

    private SearchResult depthLimitedSearch(int[][] grid, int limit) {
        SearchNode initialState = null;

        for (byte x = 0; x < 15; x++) {
            for (byte y = 0; y < 15; y++) {
                if (grid[x][y] == 2) {
                    // Located start location.
                    initialState = new SearchNode(x, y, (byte)0);
                    frontier.add(initialState);
                    this.logger.info("Starting depth limited search with depth limit {}", limit);
                    this.logger.info("Initial frontier {}", frontier);
                    return recursiveDepthLimitedSearch(initialState, grid, limit);
                }
            }
        }

        return SearchResult.Failure;
    }

    private SearchResult recursiveDepthLimitedSearch(SearchNode searchNode, int[][] grid, int limit) {
        if (grid[searchNode.getX()][searchNode.getY()] == 3) {
            return SearchResult.Solved;
        } else if (limit == 0) {
            return SearchResult.Cutoff;
        } else {
            boolean cutOffOccurred = false;
            frontier.remove(searchNode);
            explored.add(searchNode);
            for (SearchNode childSearchNode: searchNode.expand()) {
                if (grid[childSearchNode.getX()][childSearchNode.getY()] == 1) {
                    // Traffic / Roadblock -> ignore
                } else if (!frontier.contains(childSearchNode) && !explored.contains(childSearchNode)) {
                    frontier.add(childSearchNode);
                }
            }
            this.logger.info("Expanded node: {}. New frontier: {}", searchNode, frontier);
            SearchResult result = recursiveDepthLimitedSearch(frontier.get(frontier.size() - 1), grid, limit - 1);
            if (result == SearchResult.Cutoff) {
                cutOffOccurred = true;
            } if (result != SearchResult.Failure) {
                return result;
            }
            if (cutOffOccurred) {
                return SearchResult.Cutoff;
            } else {
                return SearchResult.Failure;
            }
        }
    }
}
