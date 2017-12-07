import java.io.*;
import java.util.*;

public class KnapSack{
	
	//private int weight, itemAmount;
	private static int[][] table;
	static int COLUMNS = 4;
	
	
	public static void main(String[] args) {
		
		String filename;
		
		System.out.println("Please input filename: ");
		filename = System.console().readLine();
		System.out.println();
		
		try{
			Scanner in = new Scanner(new File(filename));
			int weight;
			int itemCount;
			
			weight = in.nextInt();
			System.out.println("WEIGHT: " + weight);
			itemCount = in.nextInt();
			System.out.println("ITEMS: " + itemCount);
			
			//String line;
			int count = 1;
			table = new int[itemCount][COLUMNS];			
			while(in.hasNext())
			{
				//int price = in.nextInt();
				//int itemWeight = in.nextInt();

				for(int i = 0; i < itemCount; i++){
					table[i][0] = i+1; //item number in column 1
					table[i][1] = in.nextInt(); //price
					table[i][2] = in.nextInt(); //weight
					table[i][3] = (table[i][1] / table[i][2]);
					
				}
				//System.out.println("ITEM: " + count + " PRICE: " + price + " ITEMWEIGHT: " + itemWeight);
				
				
			}
			printMatrix(table, itemCount);
			in.close(); //close the file 
		
		}catch(Exception e){
		
			e.printStackTrace();
		
		}
		
		
	}//end main 
	
	
	//----------------------------------PRINT MATRIX--------------------------------------
	public static void printMatrix(int[][] table, int rows){
		
		System.out.println("----------------------PRICE/WEIGHTS------------------------");
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < COLUMNS; j++)
			{
				System.out.print(table[i][j]);
				System.out.print("\t");
			}
			System.out.println("\n");
			
		}
				
	
	}//end printMatrix
	


}//end KnapSack Class