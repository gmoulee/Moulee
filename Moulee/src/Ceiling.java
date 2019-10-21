import java.io.*;
import java.util.*;

public class Ceiling{
	
	public static int findCeiling(int[] arr,int start, int end, int k) {
		int index = Integer.MAX_VALUE;
		for(int i=start;i<=end;i++) {
			if(arr[i] >= k) {
				index = arr[i];
			}
		}
		return index;
	}
	
	public static void main(String[] args) {
		int[ ] arr = {1,5,10,15,25};
		int len = arr.length;
		int k = 0;
		int index = findCeiling(arr, 0, len-1, k);
		if(index == Integer.MAX_VALUE) {
			System.out.print("ceiling not found");
		}
		else {
			System.out.println("The Ceiling for "+k+" is "+index);
		}
	}
	
}