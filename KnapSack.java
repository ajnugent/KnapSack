import java.io.*;
import java.util.*;

public class KnapSack{
	
	//private int weight, itemAmount;
	//private int[][] table;
	static int COLUMNS = 4;
	
	
	public static void main(String[] args) {
		int[][] table;
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
			while(in.hasNext())
			{
				int price = in.nextInt();
				int itemWeight = in.nextInt();
				//String[] priceWeight = line.split("\\W+");
				
				
				//String price = priceWeight[0];
				System.out.println("ITEM: " + count + "PRICE: " + price + "ITEMWEIGHT: " + itemWeight);
				
				//String itemWeight = priceWeight[1];
				//System.out.println("itemWeight: " + itemWeight);
				count++;
			}
			
			in.close(); //close the file 
		}catch(Exception e){
			e.printStackTrace();
		}
	}//end main 
	


}//end KnapSack Class