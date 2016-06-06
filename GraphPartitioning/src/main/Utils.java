package main;

public class Utils {
	
	public static double inSeconds(long start, long end) {
		
		return (double) (end - start) / 1000;
	}
	
	
	/**
	 * @param array
	 * @return -1 if no value greater then Integer.MIN_VALUE was found,<br> index of the (first occurence of) maximum 
	 */
	public static int indexOfMax(int[] array) {
		
		int max = Integer.MIN_VALUE;
		int index = -1;
		for(int i=0; i<array.length; i++) {
			if(array[i]>max) {
				max = array[i];
				index = i;
			}
		}
		
		return index;
	}
	
	/**
	 * @param array
	 * @return -1 if no value lower then Integer.MAX_VALUE was found,<br> index of the (first occurence of) minimum 
	 */
	public static int indexOfMin(int[] array) {
		
		int min = Integer.MAX_VALUE;
		int index = -1;
		for(int i=0; i<array.length; i++) {
			if(array[i]<min) {
				min = array[i];
				index = i;
			}
		}
		
		return index;
	}

}
