import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

import org.omg.CORBA.PUBLIC_MEMBER;
import za.ac.wits.snake.DevelopmentAgent;

public class MyAgent extends DevelopmentAgent {

    int move = 5;
    char[][] grid = new char[50][50];
    int[] row = {-1,1,0,0,-1,1,1,-1};
    int[] col = {0,0,-1,1,1,-1,1,-1};
    String head;

    public static void main(String args[]) {
        MyAgent agent = new MyAgent();
        MyAgent.start(agent, args);
    }

    @Override
    public void run() {
        try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in))) {
            String initString = br.readLine();
            String[] temp = initString.split(" ");
            int nSnakes = Integer.parseInt(temp[0]);

            while (true) {
                String line = br.readLine();
                if (line.contains("Game Over")) {
                    break;
                }
                fillArray();
 //               printBoard(grid);

                String apple1 = line;
                String apple2 = br.readLine();
                //do stuff with apples
                String[] ap1 = apple1.split(" ");
                String[] ap2 = apple2.split(" ");

//                insertApples(ap1,ap2);


                int headx = 0;
                int heady = 0;
                int tailx = 0;
                int taily = 0;
                boolean isVisible = false;
                Vertex tailss = null;

                int mySnakeNum = Integer.parseInt(br.readLine());
                for (int i = 0; i < nSnakes; i++) {
                    String snakeLine = br.readLine();
                    if (i == mySnakeNum) {
                        //hey! That's me :)
                        drawSnake(grid,snakeLine,i+1,mySnakeNum);
                        String head1 = snakeHead(snakeLine);
                        String [] heads = head1.split(",");
                        headx = Integer.parseInt(heads[0]);
                        heady = Integer.parseInt(heads[1]);
                        String[] tail = snakeTail(snakeLine).split(",");
                        tailx = Integer.parseInt(tail[0]);
                        taily = Integer.parseInt(tail[1]);
                        tailss = new Vertex(tailx,taily);


                    }
                    //do stuff with other snakes
                    else {
                        drawSnake(grid,snakeLine,i+1,mySnakeNum);
                    }
                    if(snakeLine.contains("inv") && !(i == mySnakeNum)){
                        isVisible = true;
                    }
                }
                //finished reading, calculate move:
                // my move calculations

                int ap1x = Integer.parseInt(ap1[0]);
                int ap2x = Integer.parseInt(ap2[0]);
                int ap1y = Integer.parseInt(ap1[1]);
                int ap2y = Integer.parseInt(ap2[1]);

                Vertex Start = new Vertex(headx,heady);

                Vertex InvAp = new Vertex(ap1x, ap1y);
                Vertex End = new Vertex(ap2x,ap2y);

                if(ap1x!=-1)
                {
                    if(manhattanDistance(Start, InvAp) < manhattanDistance(Start, End) && (bfs(Start,InvAp) != null)){
                        End = InvAp;
                    }
                }

                Vertex d;
                if(isVisible){
                    d = bfsTail(Start,tailss);
                }
                else {
                    d = AStarSearch(Start, End,mySnakeNum+1);
                    if (d == null || this.checkAppleSpace(End, tailss) > 5) {
                        if (this.manhattanDistance(End, tailss) < 5) {
                            //System.err.println(this.manhattanDistance(End, tailss) + " distance from apple to tail");
                            //d = null;
                            continue;
                        }
                        else {
                            d = this.bfsTail(Start, tailss);
                        }

                    }


                    ////version 1
//                    d = bfs(Start, End);
//                    if(!bfsAppleToTail(End,tailss)){
//                        d = null;
//                    }
//                    if(manhattanDistance(End,tailss) < 4) {
//                        d = null;
//                    }

                    // version 2
//                    if(d == null || (checkAppleSpace(End,tailss) > 2)){
//                        if(manhattanDistance(End,tailss) < 3) {
//                            d = null;
//                        }
//                        else {
//                            if((manhattanDistance(Start,tailss) > 2)){
//                                d = bfsTail(Start, tailss);
//                           }
//
//                        }
//                    }
                    
                    // version 3
//                    if(d == null || (checkAppleSpace(End,tailss) > 2)){
//                        if(manhattanDistance(End,tailss) < 4 && (manhattanDistance(Start,tailss) < 4)) {
//                            d = null;
//                        }
//                        else {
//                            if((manhattanDistance(Start,tailss) > 3)){
//                                d = bfsTail(Start, tailss);
//                            }
//                        }
//                    }
                    ////real statements
//                    d = AStarSearch(Start, End);
////                    d = bfs(Start, End);
//                    if(!bfsAppleToTail(End,tailss)){
//                        d = null;
//                    }
////                    if(manhattanDistance(End,tailss) < 4) {
////                        d = null;
////                    }
//                    if(d == null || (checkAppleSpace(End,tailss) > 2)){
//                        d = bfsTail(Start,tailss);
//                    }

                }

                if(d == null){
                    int bestSpace = 0;
                    Vertex bestV = new Vertex(-1,-1);
                    for (int i = 0; i < 4; i++) {
                        int x = Start.xValue + col[i];
                        int y = Start.yValue + row[i];
                        if((x > 0 && x < 49) && (y > 0 && y < 49)){
                            Vertex next = new Vertex(x,y);
                            if (grid[y][x] == '0' || grid[y][x] == 'X'){
                                int currSpace = bfsSpace(next);
                                if(currSpace>bestSpace){
                                    bestV = next;
                                    bestSpace = currSpace;
                                }
                            }
                        }
                    }

                    if(bestV.xValue == -1){
//                        System.err.println(5);
                        System.out.println(5);
                    }
                    else {
//                        System.err.println(Start + "\n" + bestV + "\n" + bestSpace);
//                        System.err.println("apple " + End + "\n" + "tail " + tailss);
                        System.out.println(moves(Start, bestV));
                    }
                }
                else{
                    backtracking(Start,d);
                }
//                printBoard(grid);
//
//                System.err.println(vertices.size());


                //               System.err.println("Head x ..." + getVertex(headx,heady).xValue + " Head y ..." +getVertex(headx,heady).yValue);
//               System.err.println("Head parent ... Using get " +"Head x ..."+ getVertex(headx,heady).getParent().xValue +"  Head y ..."+ getVertex(headx,heady).getParent().yValue);
//               System.err.println("Head parent ... Using vertex "+"Head x ..." + Start.getParent().xValue + " Head y ..." +Start.getParent().yValue);
//                System.err.print(Start.getInTree());
//                System.err.print(End.getInTree());


//                backtracking(getVertex(heady,headx),getVertex(ap2y,ap2x));

//                if((ap2x == headx) || (ap1x == headx)){
//                    if(((heady - ap2y) > 0) || ((heady - ap1y) > 0)){
//                        move = 0;
//                        System.out.println(move);
//                    }
//                    if(((heady - ap2y) < 0)  || ((heady - ap1y) < 0)){
//                        move = 1;
//                        System.out.println(move);
//                    }
//                }
//
//                if((ap2y == heady) || (ap1y == heady)){
//                    if((headx - ap2x) > 0  || ((headx - ap1x) > 0)){
//                        move = 2;
//                        System.out.println(move);
//                    }
//                    if( ((headx - ap2x) < 0) || (headx - ap1x < 0)){
//                        move = 3;
//
//                        System.out.println(move);
//                    }
//                }
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void fillArray(){
        for(int f = 0;f < 50;f++){
            for(int g = 0;g < 50;g++){
                grid[f][g] = '0';
            }
        }
    }

    public  void printBoard(char[][] grid){
        for(int l = 0;l < 50;l++){
            System.out.print(grid[l][0]);
            for(int m = 1;m < 50;m++){
                System.err.print( grid[l][m]);
            }
            System.err.println();
        }
    }

    public String snakeHead(String snake){
        String [] parts = snake.split(" ");

        if(parts[0].startsWith("a")){
            head = parts[3];
        }
        if(parts[0].startsWith("i")){
            head = parts[5];
        }
        return head;
    }

    public String snakeTail(String snake){
        String [] parts = snake.split(" ");
        return parts[parts.length-1];
    }

    public void drawSnake(char[][] grid,String snake,int SnakeNumber,int mySnakeNumber){
        String [] parts = snake.split(" ");

        // SnakeLength = Integer.parseInt(parts[1]);

        if(parts[0].startsWith("a")){
            for(int n = 3;n < parts.length-1;n++){
                drawLine(grid, parts[n], parts[n + 1], SnakeNumber);
                if(SnakeNumber - 1 != mySnakeNumber) {
                    if (n == 3) {
                        String[] headParts = parts[3].split(",");
                        for (int i = 0; i < 8; i++) {
                            int grow = Integer.parseInt(headParts[1]) + row[i];
                            int gcol = Integer.parseInt(headParts[0]) + col[i];
                            if((grow >= 0 && grow < 50) && (gcol >= 0 && gcol < 50)) {
                                if(grid[grow][gcol] == '0'){
                                    grid[grow][gcol] = 'X';
                            }
                            }
                        }

                    }
                }
            }
        }
        if(parts[0].startsWith("i")){
            for(int n = 4;n < parts.length-1;n++){
                drawLine(grid, parts[n], parts[n + 1], SnakeNumber);
                if(SnakeNumber - 1 != mySnakeNumber) {
                    if (n == 4) {
                        String[] headParts = parts[4].split(",");
                        for (int i = 0; i < 8; i++) {
                            int grow = Integer.parseInt(headParts[1]) + row[i];
                            int gcol = Integer.parseInt(headParts[0] )+ col[i];
                            if((grow >= 0 && grow < 50) && (gcol >= 0 && gcol < 50)) {
                                if(grid[grow][gcol] == '0') {
                                    grid[grow][gcol] = 'X';
                                }
                            }
                        }

                    }
                }
            }
        }
    }

    public void drawLine(char[][] grid,String segment1,String segment2,int SnakeNumber){

        int x1,x2,y1,y2;
        String[] part1 = segment1.split(",");
        String [] part2 = segment2.split(",");
        char SnakeNumber1 = (char)(SnakeNumber + '0');
        //  int PartLength = Math.max(Math.abs(Integer.parseInt(part1[0]) - Integer.parseInt(part2[0])) ,Math.abs(Integer.parseInt(part1[1]) - Integer.parseInt(part2[1])));
        //  LongestPart = Math.max(PartLength,LongestPart);

        x1 = Integer.parseInt(part1[1]);
        y1 = Integer.parseInt(part1[0]);

        x2 = Integer.parseInt(part2[1]);
        y2 = Integer.parseInt(part2[0]);

        if(x1 == x2 && y1 > y2){
            for(int i = y2;i < y1+1;i++){
                grid[x1][i] = SnakeNumber1;
            }
        }
        else if(y1 == y2 && x1 > x2){
            for(int i = x2; i < x1+1;i++){
                grid[i][y1] = SnakeNumber1;
            }
        }
        if(x1 == x2 && y1 < y2){
            for(int i = y1;i < y2+1;i++){
                grid[x1][i] = SnakeNumber1;
            }
        }
        else if(y1 == y2 && x1 < x2){
            for(int i = x1; i < x2+1;i++){
                grid[i][y1] = SnakeNumber1;
            }
        }
    }

//    public void insertApples(String [] apple1,String[] apple2){
//        grid[Integer.parseInt(apple2[1])][Integer.parseInt(apple2[0])] = '7';
//
//        if(!apple1[0].startsWith("-")){
//            grid[Integer.parseInt(apple1[1])][Integer.parseInt(apple1[0])] = '7';
//        }
//
//    }

//    public Vertex getVertex(int x, int y){
//        int j = 0;
//        Vertex t = null;
//        Vertex n = new Vertex(x,y);
//
//        for(int i = 0; i < vertices.size();i++){
//            if(vertices.get(i).xValue == n.xValue && vertices.get(i).yValue == n.yValue){
//                t = vertices.get(i);
//            }
//        }
//        return t;
//    }


    //// Bfs code
    public Vertex bfs(Vertex Start,Vertex End){
        LinkedList<Vertex> queue = new LinkedList<>();
        HashSet<String> visited = new HashSet<>();
        String n = Start.xValue+", "+Start.yValue;
        visited.add(n);
        queue.add(Start);
        while(!queue.isEmpty()){
            Vertex Current = queue.poll();
            if(Current.equals(End)){
                return Current;
            }
            for (int i = 0; i < 4; i++) {
                int x = Current.xValue + col[i];
                int y = Current.yValue + row[i];
                if((x >= 0 && x < 50) && (y >= 0 && y < 50)){
                    Vertex next = new Vertex(x,y);
                    n = x+", "+y;
                    if (grid[y][x] == '0'){
                        if(!visited.contains(n)){
//                            next.setGCost(manhattanDistance(Start,next));
//                            next.setHCost(manhattanDistance(End,next));
                            next.setParent(Current);
                            visited.add(n);
                            queue.add(next);
                        }
                    }
                }
            }
        }
        return null;
    }

    // Start is a head and End is the tail
    // finding path from head to tail
    public Vertex bfsTail(Vertex Start,Vertex End){
        Queue<Vertex> queue = new ArrayDeque<>();
        HashSet<String> visited = new HashSet<>();
        String n = Start.xValue+", "+Start.yValue;
        visited.add(n);
        queue.add(Start);

        while(!queue.isEmpty()){
            Vertex Current = queue.poll();
            if(Current.equals(End)){
                return Current;
            }

            for (int i = 0; i < 4; i++) {
                int x = Current.xValue + col[i];
                int y = Current.yValue + row[i];
                if((x >= 0 && x < 50) && (y >= 0 && y < 50)){
                    Vertex next = new Vertex(x,y);
                    n = x+", "+y;
                    // if End.equals(next) in next run the code will stop
                    if (grid[y][x] == '0' || End.equals(next)){
                        if(!visited.contains(n)){
                            next.setParent(Current);
                            visited.add(n);
                            queue.add(next);
                        }
                    }

                }

            }
        }
        return null;
    }

    public int bfsSpace(Vertex Start){
        int space =0;
        Queue<Vertex> queue = new ArrayDeque<>();
        HashSet<String> visited = new HashSet<>();
        String n = Start.xValue+", "+Start.yValue;
        visited.add(n);
        queue.add(Start);

        while(!queue.isEmpty()){
            Vertex Current = queue.poll();
            for (int i = 0; i < 4; i++) {
                int x = Current.xValue + col[i];
                int y = Current.yValue + row[i];
                if((x > 0 && x < 49) && (y > 0 && y < 49)){
                    Vertex next = new Vertex(x,y);
                    n = x+", "+y;
                    if (grid[y][x] == '0' ||grid[y][x] == 'X'){
                        if(!visited.contains(n)){
                            next.setParent(Current);
                            visited.add(n);
                            space++;
                            queue.add(next);
                        }
                    }
                }
            }
        }
        return space;
    }

    public void backtracking(Vertex Start,Vertex End){
        while(End.getParent() != Start){
            if(End.getParent() == null){
                break;
            }
            End = End.getParent();
           }
        System.out.println(moves(Start,End));
        }

    public int moves(Vertex Start,Vertex End){
        if(End.xValue - Start.xValue < 0){
            move = 2;
        }
        else if(End.xValue -Start.xValue > 0){
            move = 3;
        }
        else if(End.yValue - Start.yValue < 0){
            move = 0;
        }
        else if(End.yValue -Start.yValue > 0){
            move = 1;
        }
        return move;
    }

    // returns the distance between apple and head
    public int manhattanDistance(Vertex Start,Vertex End){
        return Math.abs(Start.xValue - End.xValue) + (Math.abs(Start.yValue - End.yValue));
    }

    // check if the apple nodes/vertex child are occupied
    // return the number of occupied child's
    // if is > 2 the snake must chase the tail
    public int checkAppleSpace(Vertex End,Vertex tail) {
        int appleCloseArea = 0;
        for (int i = 0; i < 4; i++) {
            int x = End.xValue + col[i];
            int y = End.yValue + row[i];
            if ((x >= 0 && x < 50) && (y >= 0 && y < 50)) {
                Vertex next = new Vertex(x,y);
                if (grid[y][x] != '0' || next.equals(tail)) {
                    appleCloseArea = appleCloseArea +1;
                }

            }
        }
        return appleCloseArea;
    }

    //returns a boolean true if there is a way from apple to snake tail
    // to avoid biting its self
//    public boolean bfsAppleToTail(Vertex Start,Vertex End){
//        Queue<Vertex> queue = new ArrayDeque<>();
//        HashSet<String> visited = new HashSet<>();
//        String n = Start.xValue+", "+Start.yValue;
//        visited.add(n);
//        queue.add(Start);
//
//        while(!queue.isEmpty()){
//            Vertex Current = queue.poll();
//            if(Current.equals(End)){
//                return true;
//            }
//            for (int i = 0; i < 4; i++) {
//                int x = Current.xValue + col[i];
//                int y = Current.yValue + row[i];
//                if((x >= 0 && x < 50) && (y >= 0 && y < 50)){
//                    Vertex next = new Vertex(x,y);
//                    n = x+", "+y;
//                    if (grid[y][x] == '0'|| End.equals(next)){
//                        if(!visited.contains(n)){
//                            next.setParent(Current);
//                            visited.add(n);
//                            queue.add(next);
//                        }
//                    }
//                }
//            }
//        }
//        return false;
//    }

    // implementing A star pathfinding
    public Vertex AStarSearch(Vertex Start,Vertex End,int MySnakeNumber){
        PriorityQueue<Vertex> queue = new PriorityQueue<>();
        HashSet<String> visited = new HashSet<>();
        HashSet<String> Closed = new HashSet<>();
        String n = Start.xValue+", "+Start.yValue;
        visited.add(n);
        Closed.add(n);
        queue.add(Start);
        Start.setGCost(manhattanDistance(Start,Start));
        Start.setHCost(manhattanDistance(Start,End));

        while (!queue.isEmpty()){
            Vertex Current = queue.poll();
            if(Current.equals(End)){
                return Current;
            }
            for (int i = 0; i < 4; i++) {
                int x = Current.xValue + col[i];
                int y = Current.yValue + row[i];
                if((x >= 0 && x < 50) && (y >= 0 && y < 50)){
                    Vertex next = new Vertex(x,y);
                    n = x+", "+y;
                    if (grid[y][x] == '0' || grid[y][x] == 'X'){
                        if(!visited.contains(n)){
                            int CurrentCost = Current.getGCost() + RiskCost(next,MySnakeNumber);
                            if(queue.contains(next) && next.getGCost() <= CurrentCost){
                                continue;
                            }
                            else if(Closed.contains(n) && next.getGCost() <= CurrentCost){
                                continue;
                            }
                            next.setGCost(CurrentCost);
                            next.setHCost(manhattanDistance(End,next));
                            next.setParent(Current);
                            visited.add(n);
                            queue.add(next);
                        }
                    }
                }
            }
            Closed.add(Current.xValue+", "+Current.yValue);
        }
        return null;
    }

    public int RiskCost(Vertex Current,int MySnakeNumber){
        char number = (char) ((char) MySnakeNumber +'0');
        int risk = 0;
        for (int i = 0; i < 4; i++) {
            int x = Current.xValue + col[i];
            int y = Current.yValue + row[i];
            if ((x >= 0 && x < 50) && (y >= 0 && y < 50)) {
                if (grid[y][x] == number) {
                    risk = risk + 1;
                }
                else {
                    if (grid[y][x] != '0') {
                        risk = risk + 100;
                    } else {
                        risk = risk + 3;
                    }

                }

            }
        }
        return risk;
    }

}
