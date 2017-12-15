/*
Jackie Nugent 
Project 6 CS4050 Algorithms
Fall 2017

*/
import java.io.*;
import java.util.*;

public class KnapSack{
	

	static double[][] items; //holds our table 
	static int COLUMNS = 3;
	
	static int capacity, itemCount;
	static double weight, profit, profitweight;
	static int nodeCount = 1; //start nodeCount at 1 for sanity 
	
	static ArrayList<Node> activeNodes = new ArrayList<>();
	
	public static void main(String[] args) {
		
		String filename;
		
		System.out.println("Please input filename: ");
		filename = System.console().readLine();
		System.out.println();
		
		try{
			Scanner in = new Scanner(new File(filename));
			//int weight;
			//int itemCount;
			
			capacity = in.nextInt();
			System.out.println("WEIGHT: " + capacity);
			itemCount = in.nextInt();
			System.out.println("ITEMS: " + itemCount + "\n");
			
			//read in the txt file and put info in a table 
			int count = 1;
			items = new double[COLUMNS][itemCount];			
			
			int j = 0;
			while(in.hasNext())
			{
				

				for(int i = 0; i < itemCount; i++){
					profit = in.nextInt();
					weight = in.nextInt();
					profitweight = profit/weight;
				
					
					items[0][j] = profit; //profit of item
					items[1][j] = weight; //weight of item 
					items[2][j] = profitweight; // Pi/Wi
					
					j++;
				}
				//System.out.println("ITEM: " + count + " PRICE: " + price + " ITEMWEIGHT: " + itemWeight);
				
				
			}
			
			//printMatrix(items, itemCount); //checking purposes  
			in.close(); //close the file 
		
		}catch(Exception e){
		
			e.printStackTrace();
		
		}
		
		//print the Matrix 
		System.out.println("----------------------PRICE/WEIGHTS------------------------");
		for (int i = 0; i < items[2].length; i++) {
            System.out.println("ITEM" + "\t" + (i + 1) + " : \t" + (int) items[0][i] + "\t " + (int)items[1][i] +
                    "\t " + items[2][i]);
        }
        System.out.println("-----------------------------------------------------------");
		System.out.println("");
		
		RootNode(); //let's get started!!!! 
		
	}//end main 
	
	
	//----------------------------------PRINT MATRIX--------------------------------------
	public static void printMatrix(int[][] items, int rows){
		int itemCount = 1;
		System.out.println("----------------------PRICE/WEIGHTS------------------------");
		for(int i = 0; i < COLUMNS; i++)
			
		{
			for(int j = 0; j < rows; j++)
			{
				
				System.out.print(items[i][j]);
				System.out.print("\t");
				//itemCount++;
			}
			System.out.println("\n");
			
		}
		System.out.println("-----------------------------------------------------------");		
	
	}//end printMatrix
	
	
	//----------------------------------CREATE ROOT NODE--------------------------------------
	public static void RootNode() {
       
        if (activeNodes.size() == 0) {
            List<Integer> empty = new ArrayList<>();
            Node node = new Node();
            node.nodeNum = nodeCount;
            node.level = 0;
            node.items = empty;        
            node.cantUse = new ArrayList<>();
            node = GetBound(node, false);
            activeNodes.add(node);
            BranchBound(node);
        } else {
            System.out.println("Oops! Active Tree");
        }

    }//end GenerateRoot


	//----------------------------------BRANCH + BOUND -----------------------------------
	public static void BranchBound(Node parent) {

        if (parent.level == (items[2].length) && parent == BestOption()) {
            PrintNodes(parent, "*** BEST NODE ***");
        }
        else if (parent.weight == capacity && parent == BestOption()){
            PrintNodes(parent, "*** BEST NODE ***");
        }
        else {
            PrintNodes(parent, "Exploring");
            GenerateChildren(parent);
            BranchBound(BestOption());
        }
    }//end BranchBound
	
	//---------------------------------- GET BOUND --------------------------------------
	    public static Node GetBound(Node node, Boolean left) {
        int i = 0;
        int possibleLoad = node.weight;   
        int load = node.weight;
        int cantUse = -1;

        while (possibleLoad <= capacity && i < items[2].length) {

            for (int k = 0; k < node.items.size(); k++ ) {
                if ((i + 1) == node.items.get(k)) {
                    
                    cantUse = node.items.get(k);
                    
                    node.bound += items[0][cantUse - 1];
                }
            }
            for (int k = 0; k < node.cantUse.size(); k++ ) {
                    if ((i + 1) == node.cantUse.get(k)) {
                        cantUse = node.cantUse.get(k);
                        break;
                    }
                }
            if ((i + 1) != cantUse) {
                 possibleLoad += items[1][i];
                // Add profit to the bound if its weight is under capacity
                if (possibleLoad <= capacity) {
                    load += items[1][i];
                    node.bound += items[0][i];
                }
                if (possibleLoad >= capacity) {
                    break;
                }
                else if ((i + 1) >= items[2].length){
                    i++;
                    break;
                }
            }
            i++;
        }
        if (load != capacity && (i + 1) <= items[2].length) {
            int remainingLoad = capacity - load;
            node.bound += remainingLoad * items[2][i];
        }
        return node;
    }//end GetBound
    
   
    //---------------------------------- GENERATE CHILDREN --------------------------------------
    public static void GenerateChildren(Node parent) {
//
        // Left Child
        Node node = new Node();

        node.items = new ArrayList<Integer>(parent.items);
       
        node.level = parent.level + 1;
       
        node.profit = parent.profit;
       
        node.weight = parent.weight;
       
        nodeCount++;
       
        node.nodeNum = nodeCount;
       
        node.cantUse = new ArrayList<Integer>(parent.cantUse);
       
        node.cantUse.add(node.level);
       
        node = GetBound(node, true);
       
        activeNodes.add(node);
       
        PrintNodes(node, "    Left child is");

        // Right Child
        Node nodeR = new Node();

        nodeR.items = new ArrayList<Integer>(parent.items);
        
        nodeR.level = parent.level + 1;
        
        nodeR.profit = parent.profit;
        
        nodeR.weight = parent.weight;
        
        nodeR.cantUse = new ArrayList<Integer>(parent.cantUse);

        nodeCount++;
        
        nodeR.nodeNum = nodeCount;
        
        nodeR.items.add(node.level);
        
        nodeR.profit += items[0][nodeR.level - 1];
        
        nodeR.weight += items[1][nodeR.level - 1];
        
        //check to see if it exceeds capacity
        if (nodeR.weight > capacity){
            nodeR.profit = -1;
            nodeR.bound = -1;
            PrintNodes(nodeR, "    Right child is");
            System.out.println("\tPruned because too heavy");
            activeNodes.remove(nodeR);
        }
        
        else {
            nodeR = GetBound(nodeR, false);
            activeNodes.add(nodeR);
            PrintNodes(nodeR, "    Right");
        }
        
        activeNodes.remove(parent);
        System.out.println("");


        }//end GenerateChildren
    
	//---------------------------------- FIND BEST OPTION --------------------------------
    public static Node BestOption() {
        Node best = null;
        for (int i = 0; i < activeNodes.size(); i++) {
            if (best == null) {
                best = activeNodes.get(i);
            }
            else if (best.bound < activeNodes.get(i).bound) {
                best = activeNodes.get(i);
            }
        }
        return best;

    }//end BestOption


	//---------------------------------- PRINT NODES--------------------------------------
    public static void PrintNodes(Node node, String who){
        String itemString = "[";
        if (node.items.size() != 0) {
            itemString += node.items.get(0);
        }
        for (int i = 1; i < node.items.size(); i++) {
            itemString += ", " +node.items.get(i);
        }
        itemString += "]";

        String noUse = "[";
        if (node.cantUse.size() != 0) {
            noUse += node.cantUse.get(0);
        }

        for (int i = 1; i < node.cantUse.size(); i++) {
            noUse += ", " + node.cantUse.get(i);
        }
        noUse += "]";
        
        System.out.println(who + " Node " + node.nodeNum +
                ": ITEMS: " + itemString +
                " LEVEL: " + node.level +
                " PROFIT: " + node.profit +
                " WEIGHT: " + node.weight +
                " BOUND: " + node.bound + "\t----->" + "\tItems Not Used: " + noUse);
    }//end PrintNodes 
  
	



}//end KnapSack Class