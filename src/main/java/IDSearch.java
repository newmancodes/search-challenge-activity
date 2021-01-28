import models.SearchNode;
import models.SearchResult;

import java.util.ArrayList;
import java.util.Stack;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IDSearch {
    private Logger logger = LoggerFactory.getLogger(IDSearch.class);

    public SearchResult iterativeDeepening(int[][] grid) {
        for (int depth = 0; depth < 500; depth++) {
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
                    this.logger.info("Starting depth limited search with depth limit {}", limit);
                    return depthLimitedSearch(initialState, grid, limit);
                }
            }
        }

        return SearchResult.Failure;
    }

    private SearchResult depthLimitedSearch(SearchNode initialState, int[][] grid, int limit) {
        Stack<SearchNode> frontier = new Stack<SearchNode>();
        frontier.push(initialState);
        this.logger.info("Initial frontier {}", frontier);
        ArrayList<SearchNode> explored = new ArrayList<SearchNode>();
        boolean depthLimitReached = false;

        while (!frontier.isEmpty()) {
            SearchNode searchNode = frontier.pop();
            if (grid[searchNode.getX()][searchNode.getY()] == 3) {
                this.logger.info("Found a path to {}", searchNode);
                String path = "";
                SearchNode step = searchNode;
                while (step != null) {
                    path = String.format("%s %s", step.toString(), path);
                    step = step.getParent();
                }
                this.logger.info(path);
                return SearchResult.Solved;
            }
            explored.add(searchNode);
            if (searchNode.getDepth() < limit) {
                for (SearchNode childSearchNode : searchNode.expand(grid)) {
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
                        frontier.push(childSearchNode);
                    }
                }
                this.logger.info("Expanded node: {}. New frontier: {}", searchNode, frontier);
            } else {
                depthLimitReached = true;
            }
        }

        if (depthLimitReached) {
            return SearchResult.Cutoff;
        }

        return SearchResult.Failure;
    }
}
