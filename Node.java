import java.util.*;


public class Node{
    public int nodeNum;     		// Node number
    public List<Integer> items;     // Items in the node
    public int level;       		// level currently on 
    public int profit = 0;     		 // total profit
    public int weight = 0;     		 // total weight
    public int bound;       		// bound 
    public int relations[] = new int[3]; // 0 parent, 1 left child, 2 right child
    public List<Integer> cantUse;

    public void Node(int num, List<Integer> item, int p, int w, int b, List cantUseNums){
        
        nodeNum = num;
        items = item;
        profit = p;
        weight = w;
        bound = b;
        cantUse = cantUseNums;

    }
}