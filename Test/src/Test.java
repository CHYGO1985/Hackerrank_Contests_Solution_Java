import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;


public class Test {

	

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int[] c = new int[n];
        for(int c_i = 0; c_i < n; c_i++){
            c[c_i] = in.nextInt();
        }
        
        // record the max size of each node
        int[] count = new int[n];
        
        for (int i = 0; i < c.length; i ++) {
            c[i] = c[i] == 0 ? 1 : -1;
            count[i] = c[i];
        }
        
        Map<Integer, Set<Integer>> map = new HashMap<>();
        // init map with its own value
        for (int i = 0; i < n; i ++) {
        	Set<Integer> set = new HashSet<>();
        	set.add(i);
        	map.put(i, set);
        }
        
        for(int a0 = 0; a0 < n-1; a0++){
            int x = in.nextInt();
            int y = in.nextInt();
            // Write Your Code Here
            // union find + weighted path compression
            x = x - 1;
            y = y - 1;
            
            int tempX = count[x];
            if (count[x] <= count[x] + count[y]) {
                Set<Integer> set = map.get(x);
                set.add(x);
                set.add(y);
                map.put(x, set);
                count[x] = count[x] + count[y];
            }
            
            if (count[y] <= tempX + count[y]) {
                Set<Integer> set = map.get(y);
                set.add(x);
                set.add(y);
                map.put(y, set);
                count[y] = tempX + count[y];
            }
        }
        
        int max = Integer.MIN_VALUE;
        List<Integer> resList = new LinkedList<>();
        for (Integer i : map.keySet()) {
        	
        	// set the set for current num
        	Set<Integer> curSet = map.get(i);
        	Set<Integer> tmpSet = new HashSet<>();
        	
        	// check all the numbers in current num
        	// if if there are contained in other set.
        	for (Integer num : curSet) {
        		if (count[num] > 0)
        			tmpSet.addAll(map.get(num));
        	}
        	
        	int curSum = 0;
        	for (Integer tmpNum : tmpSet)
        		curSum += c[tmpNum];
        	
        	
        	if (curSum > max) {
        		max = curSum;
        		resList.clear();
        		for (Integer temp : tmpSet)
        			resList.add(temp);
        	}
        	
        	map.put(i, tmpSet);
        }
        
        System.out.println(max);
        System.out.println(resList.size());
        
        for (int i = 0; i < resList.size(); i ++) {
            System.out.print(resList.get(i) + " ");
        }
        in.close();

	}
}
