import java.io.*;
import java.util.*;

public class Ceiling{
	
	public static int findCeiling(int[] arr,int start, int end, int k) {
		int index = Integer.MAX_VALUE;
		if(arr[start] >= k) {
			index = arr[start];
		}
		for(int i=start+1;i<=end;i++) {
			if(arr[i] == k) {
				return arr[i];				// If the element is present in the array return the element.
			}
			if(arr[i] > k && arr[i-1] < k) {
				index = arr[i];				// The smallest element greater then k is the ceiling.
			}
		}
		return index;
	}
	
	public static void main(String[] args) {
		int[ ] arr = {1,5,10,15,25};
		int len = arr.length;
		int k = 1;
		int index = findCeiling(arr, 0, len-1, k);
		if(index == Integer.MAX_VALUE) {
			System.out.print("ceiling not found");
		}
		else {
			System.out.println("The Ceiling for "+k+" is "+index);
		}
	}
	
}