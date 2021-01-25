import models.SearchNode;
import models.SearchResult;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
            this.logger.info("Found a path to {}", searchNode);
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
                } else {
                    boolean shouldAddToFrontier = false;
                    // If the frontier includes this x,y but at a greater depth -> replace it
                    ArrayList<SearchNode> inFrontierButDeeper = frontier.stream().filter(n -> n.getX() == childSearchNode.getX() && n.getY() == childSearchNode.getY() && n.getDepth() > childSearchNode.getDepth()).collect(Collectors.toCollection(ArrayList::new));
                    if (!inFrontierButDeeper.isEmpty()) {
                        inFrontierButDeeper.forEach(frontier::remove);
                        shouldAddToFrontier = true;
                    }

                    // If the explored includes this x,y but at a greater depth -> remove from explored and add to frontier
                    ArrayList<SearchNode> inExploredButDeeper = explored.stream().filter(n -> n.getX() == childSearchNode.getX() && n.getY() == childSearchNode.getY() && n.getDepth() > childSearchNode.getDepth()).collect(Collectors.toCollection(ArrayList::new));
                    if (!inExploredButDeeper.isEmpty()) {
                        inExploredButDeeper.forEach(explored::remove);
                        shouldAddToFrontier = true;
                    }

                    if (shouldAddToFrontier || (!frontier.contains(childSearchNode) && !explored.contains(childSearchNode))) {
                        frontier.add(childSearchNode);
                    }
                }
                if (frontier.contains(childSearchNode)) {
                    SearchResult result = recursiveDepthLimitedSearch(childSearchNode, grid, limit - 1);
                    if (result == SearchResult.Cutoff) {
                        cutOffOccurred = true;
                    } else if (result != SearchResult.Failure) {
                        return result;
                    }
                }
                this.logger.info("Expanded node: {}. New frontier: {}", searchNode, frontier);
            }
            if (cutOffOccurred) {
                return SearchResult.Cutoff;
            } else {
                return SearchResult.Failure;
            }
        }
    }
}
