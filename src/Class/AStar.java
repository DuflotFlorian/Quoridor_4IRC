package Class;

import java.util.ArrayList;

public class AStar {
    // start    : obvious
    // stop     : win position
    // murs     : get grilleMur

    private static class Tile {
        public int G, H, F;
        public boolean wall;

        public  Tile(int G,int H,int F, boolean wall){
            this.G = G;
            this.H = H;
            this.F = F;
            this.wall = wall;
        }
    }

    private static Tile getTile(Tile[][] grid, Coordonnees coord){
        return grid[coord.getX()][coord.getY()];
    }

    private static ArrayList<Coordonnees> getNeighbors(Tile[][] grid, Coordonnees current){
        ArrayList<Coordonnees> neighbors = new ArrayList<Coordonnees>();
        int[][] check = new int[][] {{-1,0}, {0,-1},{1,0},{0,1}};
        for (int i=0; i<4; i++) {
            int x = current.getX() + check[i][0];
            int y = current.getY() + check[i][1];
            Coordonnees neigh = new Coordonnees(x, y);
            if(isInTable(grid,neigh))
                neighbors.add(neigh);
        }
        return neighbors;
    }

    private static void append_neighbors(Tile[][] grid,ArrayList<Coordonnees> open,ArrayList<Coordonnees> close,Coordonnees next,Coordonnees goal
    ){
        for (Coordonnees neigh:

            getNeighbors(grid, next)) {
            Tile neigh_tile = getTile(grid,neigh);
            if(!neigh_tile.wall && !close.contains(neigh)){
                neigh_tile.G = calcG(grid,neigh);
                if(!open.contains(neigh)){
                    neigh_tile.H = calcH(neigh,goal);
                    open.add(neigh);
                }
            }
        }
    }

    private static Coordonnees getNext(Tile[][] grid, ArrayList<Coordonnees> open){
        Coordonnees next;
        next = open.get(0);
        for(int i=1; i < open.size(); i++){
            if (getTile(grid,open.get(i)).F < getTile(grid,next).F)
                next = open.get(i);
            else
            if(getTile(grid,open.get(i)).F == getTile(grid,next).F &&
                    getTile(grid, open.get(i)).H < getTile(grid,next).H){
                next = open.get(i);
            }
        }
        return next;
    }

    private static boolean isInTable(Tile[][] grid, Coordonnees coord){
        return
                coord.getX() < 17 &&
                coord.getY() < 17 &&
                coord.getX() >= 0 &&
                coord.getY() >= 0;
    }

    private static boolean compareCoord(Coordonnees start, Coordonnees stop){
        return start.getX() == stop.getX() && start.getY() == stop.getY();
    }

    private static void popTile(ArrayList<Coordonnees> open, Coordonnees next){
        for(int i = 0; i < open.size(); i++){
            if(compareCoord(next, open.get(i))) {
                open.remove(i);
                break;
            }
        }
    }

    private static int calcG(Tile[][] grid, Coordonnees coord){
        int min = -1;
        int[][] check = new int[][] {{-1,0}, {0,-1},{1,0},{0,1}};

        for (int i=0; i<4; i++){
            int x = coord.getX() + check[i][0];
            int y = coord.getY() + check[i][1];
            Coordonnees c = new Coordonnees(x,y);
            if (isInTable(grid, c)){
                if (min == -1)
                    min = getTile(grid,c).G;
                else

                if (getTile(grid,c).G != -1 && getTile(grid,c).G < min)
                    min = getTile(grid,c).G;
            }
        }
        return min + 1;
    }

    private static int calcH(Coordonnees start, Coordonnees goal){
        return Math.abs(start.getX()-goal.getX()) + Math.abs(start.getY()-goal.getY());
    }

    private static Tile[][] initGrid(GrilleMur listWall){
        Tile[][] grid = new Tile[17][17];
        for(int i=0;i<17;i++){
            for(int j=0;j<17;j++){
                //Par défaut les piosn ne passent pas par les casses de type croisement, donc tous les croisements sont des murs
                if (i%2 == 1 && j%2 == 1){
                    grid[i][j]= new Tile(-1,0,0, true);
                }else {
                    grid[i][j]= new Tile(-1,0,0, false);
                }

            }
        }


        for(int i=0;i<listWall.getNumberWall();i++){
            Mur wall = listWall.getWall(i);
            int x = wall.getCoordonnees().getX();
            int y = wall.getCoordonnees().getY();
            grid[x][y].wall = true;
            if(wall.getSens()) {
                grid[x][y+2].wall = true;
            }
            else{
                grid[x+2][y].wall = true;
            }

        }

        return grid;
    }

    private static Coordonnees turn(Tile[][] grid,ArrayList<Coordonnees> open,ArrayList<Coordonnees> close,Coordonnees goal){
        Coordonnees next = getNext(grid, open);
        popTile(open, next);
        close.add(next);
        if(next!=goal)
            append_neighbors(grid,open,close,next,goal);
        return next;
    }

    public static boolean findPath(GrilleMur listWall, Coordonnees start, Coordonnees goal){
        Tile[][] grid = initGrid(listWall);
        ArrayList<Coordonnees> open = new ArrayList<Coordonnees>();
        ArrayList<Coordonnees> close = new ArrayList<Coordonnees>();

        Coordonnees current = start;
        getTile(grid,start).G = calcG(grid, start);
        open.add(start);

        while (!compareCoord(current, goal) && open.size() >0) {
            current = turn(grid, open, close, goal);
        }

        if(current.getX() == goal.getX() && current.getY() == goal.getY())
            return true;
        return false;
    }

    private static void printGrid(Tile[][] grid){
        for(int i=0;i<17;i++){
            for(int j=0;j<17;j++){
                if(grid[i][j].wall)
                    System.out.print("o");
                else
                    System.out.print(".");
            }
            System.out.println();
        }
    }
}

