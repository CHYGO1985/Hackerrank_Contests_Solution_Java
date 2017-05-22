import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

/**
 * Week of Code 32_Special Substrings
 * 
 * Based on Manacher's Algorithm, but still couldn't pass all test cases during to time out
 * 
 * @author jingjiejiang
 * @history May 21, 2017
 */
public class SpecialSubstrings {

	static int[] propertyOfString(String s){
        // Complete this function
        int[] res = new int[s.length()];
      
        if (null == s || s.length() < 2 || s.length() > 3 * Math.pow(10, 5)) return res;
        // iterate from all the substring of s, s.subString(0, end) end: 1 - s.length()
        // StringBuilder builder = new StringBuilder();
        // 1) keep a hashset G of G(s) (contains A, so do not need A)
        
        // *** ????may change it to map<Integer, String> as only the palindrome is related to highest index
        Map<Integer, String> map = new HashMap<Integer, String>();
        // Set<String> allPalinSet = new HashSet<String>();
        char[] arr = preProcessor(s);
        map = buildPalinArray(arr, map, new HashSet<String>());
        
        /*
        if (charSet.size() == 1) {
        	for (int i = 0; i < res.length; i ++) {
        		res[i] += 1; 
        	}
        	return res;
        }
        */
        
        Set<String> gSet = new HashSet<>();
        
        // *** check continuous repeat chars for substrings of palindrome
        char preChar = '#';
        int repeat = 1;
        
        // 2) from i to s.length() - 1, get the corresponding palindrome from the map and add it the G(s)  
        for (int i = 0; i < s.length(); i ++) {
        	
        	if (arr[2 * (i  + 1)] == preChar) {
        		repeat ++;
        	}
        	else {
        		preChar = arr[2 * (i  + 1)];
        		repeat = 1;
        	}
        	
            String newPali = map.remove(2 * (i + 1));
            // *** did not consider when there is not any new palindrome, res[i] = res[i-1]
            if (newPali == null || newPali.length() == 0) {
                res[i] = res[i - 1];
                continue;
            }
           
            // 3) iterate through newPaliSet, add all substring to G : G.add()
            // must start from 0, otherwise the string will not be correct as the first char is skipped
            if (repeat >= 2 && repeat == newPali.length()) {
            	int len = newPali.length();
            	gSet.add(newPali.substring(len - repeat, len));
            }
            else {
	        	int k = 0;
	            StringBuilder paliBuilder = new StringBuilder();
	            while (k < newPali.length()) {
	            	paliBuilder.append(newPali.charAt(k ++));
	            	gSet.add(paliBuilder.toString());
	            }
            }
            // 4) add G.size() to res array, clear temp set
            res[i] = gSet.size();
        }
        
        return res;
    }
	
	private static char[] preProcessor (String s) {
		
		char[] arr = new char[s.length() * 2 + 3];
		
		arr[0] = '@';
		int index = 1;
		for (; index < arr.length - 1; index ++) {
			arr[index] = (index % 2 == 0) ? s.charAt(index / 2 - 1) : '#';
			
		}
		arr[index] = '&';
		return arr;
	}
	
	private static char[] removeBoundaries(char[] cs) {
        if (cs==null || cs.length<3)
            return "".toCharArray();

        char[] cs2 = new char[(cs.length-1)/2];
        for (int i = 0; i<cs2.length; i++) {
            cs2[i] = cs[i*2+1];
        }
        return cs2;
    } 
	
	// use string insteaf of stringbuilder
	private static Map<Integer, String> buildPalinArray(char[] arr, Map<Integer, String> map, Set<String> set) {
		
		int[] pal = new int[arr.length]; 
		// it is used to keep the temp string corresponding to pal[i], so do not need iterate everytime
		pal[0] = pal[1] = 0;
		// temp;
		
		int center = 0, right = 0, mir = 0;
		for (int i = 1; i < pal.length - 1; i ++) {
			String builder = "";
			
			mir = 2 * center - i;
			if (i % 2 == 0) {
				// add sigle char to map
				builder = String.valueOf(arr[i]);
				if (set.add(builder) == true) {
					//String list = builder.toString();
					map.put(i, builder);
				}
			}
			if (i < right) {
				// *** why use min here?? it might be the case that i + pal[i] > R
				pal[i] = Math.min(pal[mir], right - i);
				char[] copy = Arrays.copyOfRange(arr, i - pal[i], i + pal[i] + 1);
				builder = String.valueOf(removeBoundaries(copy));
				// if (builder != null) palString[i] = builder.toString();
			}
			
			while (arr[i + pal[i] + 1] == arr[i - pal[i] - 1]) {
				
				if ((i + pal[i] + 1) % 2 == 0) {
					builder = arr[i - pal[i] - 1] + builder + arr[i + pal[i] + 1];
					if (set.add(builder) == true) map.put(i + pal[i] + 1, builder);
				}
				pal[i] ++;
			}
			
			if (i + pal[i] > right) {
				center = i;
				right = i + pal[i];
			}
		}
		
		return map;
	}

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        String s = in.next();
        int[] result = propertyOfString(s);
        for (int i = 0; i < result.length; i++) {
            System.out.print(result[i] + (i != result.length - 1 ? "\n" : ""));
        }
        System.out.println("");
        

    }
}
