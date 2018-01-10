package Class;

import java.util.ArrayList;

public class AStar {
    // start    : obvious
    // stop     : win position
    // murs     : get grilleMur

    private static class Tile {
        public int G, H, F;
        public boolean wall;

        public Tile(int G, int H, int F, boolean wall) {
            this.G = G;
            this.H = H;
            this.F = F;
            this.wall = wall;
        }
    }

    private static Tile getTile(Tile[][] grid, Coordinates coord) {
        return grid[coord.getX()][coord.getY()];
    }

    private static ArrayList<Coordinates> getNeighbors(Tile[][] grid, Coordinates current) {
        ArrayList<Coordinates> neighbors = new ArrayList<Coordinates>();
        int[][] check = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};
        for (int i = 0; i < 4; i++) {
            int x = current.getX() + check[i][0];
            int y = current.getY() + check[i][1];
            Coordinates neigh = new Coordinates(x, y);
            if (isInTable(grid, neigh))
                neighbors.add(neigh);
        }
        return neighbors;
    }

    private static void append_neighbors(Tile[][] grid, ArrayList<Coordinates> open, ArrayList<Coordinates> close, Coordinates next, Coordinates goal
    ) {
        for (Coordinates neigh :

                getNeighbors(grid, next)) {
            Tile neigh_tile = getTile(grid, neigh);
            if (!neigh_tile.wall && !close.contains(neigh)) {
                neigh_tile.G = calcG(grid, neigh);
                if (!open.contains(neigh)) {
                    neigh_tile.H = calcH(neigh, goal);
                    open.add(neigh);
                }
            }
        }
    }

    private static Coordinates getNext(Tile[][] grid, ArrayList<Coordinates> open) {
        Coordinates next;
        next = open.get(0);
        for (int i = 1; i < open.size(); i++) {
            if (getTile(grid, open.get(i)).F < getTile(grid, next).F)
                next = open.get(i);
            else if (getTile(grid, open.get(i)).F == getTile(grid, next).F &&
                    getTile(grid, open.get(i)).H < getTile(grid, next).H) {
                next = open.get(i);
            }
        }
        return next;
    }

    private static boolean isInTable(Tile[][] grid, Coordinates coord) {
        return
                coord.getX() < 17 &&
                        coord.getY() < 17 &&
                        coord.getX() >= 0 &&
                        coord.getY() >= 0;
    }

    private static boolean compareCoord(Coordinates start, Coordinates stop) {
        return start.getX() == stop.getX() && start.getY() == stop.getY();
    }

    private static void popTile(ArrayList<Coordinates> open, Coordinates next) {
        for (int i = 0; i < open.size(); i++) {
            if (compareCoord(next, open.get(i))) {
                open.remove(i);
                break;
            }
        }
    }

    private static int calcG(Tile[][] grid, Coordinates coord) {
        int min = -1;
        int[][] check = new int[][]{{-1, 0}, {0, -1}, {1, 0}, {0, 1}};

        for (int i = 0; i < 4; i++) {
            int x = coord.getX() + check[i][0];
            int y = coord.getY() + check[i][1];
            Coordinates c = new Coordinates(x, y);
            if (isInTable(grid, c)) {
                if (min == -1)
                    min = getTile(grid, c).G;
                else if (getTile(grid, c).G != -1 && getTile(grid, c).G < min)
                    min = getTile(grid, c).G;
            }
        }
        return min + 1;
    }

    private static int calcH(Coordinates start, Coordinates goal) {
        return Math.abs(start.getX() - goal.getX()) + Math.abs(start.getY() - goal.getY());
    }

    private static Tile[][] initGrid(WallGrid listWall) {
        Tile[][] grid = new Tile[17][17];
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                //Par défaut les piosn ne passent pas par les casses de type croisement, donc tous les croisements sont des murs
                if (i % 2 == 1 && j % 2 == 1) {
                    grid[i][j] = new Tile(-1, 0, 0, true);
                } else {
                    grid[i][j] = new Tile(-1, 0, 0, false);
                }

            }
        }


        for (int i = 0; i < listWall.getNumberWall(); i++) {
            Wall wall = listWall.getWall(i);
            int x = wall.getCoordinates().getX();
            int y = wall.getCoordinates().getY();
            grid[x][y].wall = true;
            if (wall.getDirection()) {
                grid[x][y + 2].wall = true;
            } else {
                grid[x + 2][y].wall = true;
            }

        }

        return grid;
    }

    private static Coordinates turn(Tile[][] grid, ArrayList<Coordinates> open, ArrayList<Coordinates> close, Coordinates goal) {
        Coordinates next = getNext(grid, open);
        popTile(open, next);
        close.add(next);
        if (next != goal)
            append_neighbors(grid, open, close, next, goal);
        return next;
    }

    // get the path from the visited Tiles
    private static ArrayList<Coordinates> getPath(Tile[][] tab, ArrayList<Coordinates> close, Coordinates goal) {
        ArrayList<Coordinates> path = new ArrayList<Coordinates>();
        path.add(goal);
        Coordinates current = new Coordinates(goal);
        while (getTile(tab,current).G != 0)  {
            for (Coordinates neighbour: getNeighbors(tab,current)) {
                Tile t = getTile(tab,neighbour);
                if(t.G == getTile(tab,current).G-1)
                    path.add(0,neighbour);
                    current = neighbour;
                    break;
            }
        }
        return path;
    }

    // main method, returns the shortest path between start and goal, given all the obstacles
    public static ArrayList<Coordinates> findPath(WallGrid listWall, Coordinates start, Coordinates goal) {
        Tile[][] grid = initGrid(listWall);
        ArrayList<Coordinates> open = new ArrayList<Coordinates>();
        ArrayList<Coordinates> close = new ArrayList<Coordinates>();

        Coordinates current = start;
        getTile(grid, start).G = calcG(grid, start);
        open.add(start);

        while (!compareCoord(current, goal) && open.size() > 0) {
            current = turn(grid, open, close, goal);
        }

        if (current.getX() == goal.getX() && current.getY() == goal.getY())
            return getPath(grid, close, goal);
        return new ArrayList<Coordinates>();
    }

    // only returns a boolean to know if a path exists
    public static boolean isThereAPath(WallGrid listWall, Coordinates start, Coordinates goal) {
       return (findPath(listWall, start, goal).size() > 0);
    }

    private static void printGrid(Tile[][] grid) {
        for (int i = 0; i < 17; i++) {
            for (int j = 0; j < 17; j++) {
                if (grid[i][j].wall)
                    System.out.print("o");
                else
                    System.out.print(".");
            }
            System.out.println();
        }
    }
}

