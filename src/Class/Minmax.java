package Class;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;

public class Minmax {

    private static class Move {
        private Coordinates coordinates;
        private int value;
    }

    private static Move minmax(Game game) { return minmax(game,3, 1); }
    private static Move minmax(Game game, int depth, int ia_player){
        Move best_move = new Move();
        best_move.value = Integer.MIN_VALUE;
        //int num_player = game.listPlayer().size(); // not used
        if(depth == 0){
            Move m = new Move();
            m.value = eval(game);
        }
        for(int i=0; i<9; i++) {
            for(int j=0; j<9; j++){
                Game g = new Game(game);
                if( g.move(g.getCurrentPlayer(),new Coordinates(i,j))
                    || g.putWall(g.getCurrentPlayer(),new Coordinates(i,j))
                    ){
                   Move m = minmax(g, depth-1, 1);
                   boolean test;
                   if(g.getIdCurrentPlayer() == ia_player){
                        test = m.value < best_move.value;
                   }else{
                        test = m.value > best_move.value;
                   }
                   if(test){
                      best_move.value = m.value;
                   }
                }
           }
        }
        return best_move;
    }

    private static int eval(Game game) {
        ArrayList<Integer> distances = new ArrayList<Integer>();
        for(Player p: game.listPlayer()){
            distances.add(game.findPath(p.getActualCoord(),p.getWinCoord()).size());
            // returns the distance to win position for each player
        }
        int playersDist = distances.get(game.getIdCurrentPlayer());
        distances.remove(game.getIdCurrentPlayer());
        return getMin(distances) - playersDist;
    }

    private static int getMin(ArrayList<Integer> list){
        int min = list.get(0);
        for(int i=1;i<list.size();i++){
            if(list.get(i) < min)
                min = list.get(i);
        }
        return min;
    }

}
