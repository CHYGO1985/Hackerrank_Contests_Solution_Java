/**
 * I tried to from O(n^2) with using BigInteger --> O(n) with normal int, but still get timeout error 
 *
 * @author jingjiejiang
 * @history May 28, 2017
 */
public class NumberString {
	
	// method 1: run time error
	/*
	static int getMagicNumber(String s, int k, int b, int m){
        // Complete this function
		if (null == s || s.length() < 1 || k < 1 || k > s.length() 
                || m < 1 || m > 1000 || b < 2 || b > 10) return -1;
        
        int sum = 0;
        List<String> strList = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        // according to k, build a BigInteger list
        char[] arr = s.toCharArray();
        int index = 0;
        for (; index < k; index ++) builder.append(arr[index]);
        
        strList.add(builder.toString());
        
        for (int delPos = 0; index < arr.length; index ++) {
            // every round, always delete the first digit (always at 0)
            builder.deleteCharAt(delPos);
            builder.append(arr[index]);
            strList.add(builder.toString()); 
        }
        
        // according to base b, cal the sum, return to the list
        List<BigInteger> numList = new ArrayList<>(strList.size()); 
        for (String tmp : strList) {
            BigInteger tmpSum = new BigInteger("0");
            for (int i = 0; i < tmp.length(); i ++) {
            	BigInteger cur = new BigInteger(String.valueOf(b));
                // *** ??? multiply(Integer.valueOf(tmp.charAt(i))) do i need to chagen to biginteger first
                cur = cur.pow(k - i - 1).multiply(new BigInteger( String.valueOf(tmp.charAt(i))));
                tmpSum = tmpSum.add(cur);
            }
            numList.add(tmpSum);
        }
        
        // iterate through list, % m, return sum (in int)
        for (BigInteger tmp : numList) {
            sum += tmp.mod( new BigInteger(String.valueOf(m)) ).intValue();
        }
        
        return sum;
    }
	*/
	
	// method 2: do not save a list of strings and a list of BigInteger // timeout error
	// 1. use only one, then every time get a new string
	// 2. every time get a big integer, cal the mode result and then add it to sum
	/*	
	static int getMagicNumber(String s, int k, int b, int m){
		// Complete this function
		if (null == s || s.length() < 1 || k < 1 || k > s.length() 
                || m < 1 || m > 1000 || b < 2 || b > 10) return -1;
        
        int sum = 0;
        //List<String> strList = new LinkedList<>();
        StringBuilder builder = new StringBuilder();
        // according to k, build a BigInteger list
        char[] arr = s.toCharArray();
        int index = 0;
        for (; index < k; index ++) builder.append(arr[index]);
        
        BigInteger modVal = new BigInteger(String.valueOf(m));
        for (int delPos = 0; index <= arr.length; index ++) {
            // every round, always delete the first digit (always at 0)
            String tmp = builder.toString();
            BigInteger tmpSum = new BigInteger("0");
            for (int i = 0; i < tmp.length(); i ++) {
                BigInteger cur = new BigInteger(String.valueOf(b));
                // *** ??? multiply(Integer.valueOf(tmp.charAt(i))) do i need to chagen to biginteger first
                cur = cur.pow(k - i - 1).multiply(new BigInteger( String.valueOf(tmp.charAt(i))));
                tmpSum = tmpSum.add(cur);
            }
            
            sum += tmpSum.mod(modVal).intValue();
            
            builder.deleteCharAt(delPos);
            if(index < arr.length) builder.append(arr[index]);
        }
        
        return sum;
    }
	*/
	
	// method 3: chagne to O(n) time complexity // still timeout
	/*
	 *   1 2 2 1 2
	 * 3 2 2 2
	 *     1 1 1
	 *       0 0 0
	 * ---------------
	 * it can be seen that for every k, base ^ (length - 1 - round)
	 * 1. for every k num, k + 1 = (k -  (index(0)) * 3 + new number 
	 */
	/*
	static int getMagicNumber(String s, int k, int b, int m){
		// Complete this function
		if (null == s || s.length() < 1 || k < 1 || k > s.length() 
                || m < 1 || m > 1000 || b < 2 || b > 10) return -1;
        
        int sum = 0;
        char[] arr = s.toCharArray();
        // store the biggest factor
        BigInteger max = new BigInteger(String.valueOf(b));
        max = max.pow(k - 1);
        // the modulo
        BigInteger modVal = new BigInteger(String.valueOf(m));
        BigInteger base = new BigInteger(String.valueOf(b));
        
        int index = 0;
        BigInteger tmpSum = new BigInteger("0");
        for (; index < k; index ++) {
            BigInteger cur = new BigInteger(String.valueOf(b));
            cur = cur.pow(k - index - 1).multiply( new BigInteger( String.valueOf(arr[index]) ) );
            tmpSum = tmpSum.add(cur);
        }
        
        sum += tmpSum.mod(modVal).intValue();
        
        for (int delPos = 0; index < arr.length; index ++) {
            // every round, always delete the first digit (always at 0)
            // for every k num, k + 1 = (k -  (index(0)) * 3 + new number 
            BigInteger reduce = new BigInteger( String.valueOf(arr[index - k]) );
            BigInteger inc = new BigInteger( String.valueOf(arr[index]) );                                            
            tmpSum = tmpSum.subtract( reduce.multiply(max) ).multiply(base).add(inc);
            sum += tmpSum.mod(modVal).intValue();
        }
        
        return sum;
    }
	*/	
	
	// method 4 : delete some unnecessary variable
	/*
	static int getMagicNumber(String s, int k, int b, int m){
		// Complete this function
		if (null == s || s.length() < 1 || k < 1 || k > s.length() 
                || m < 1 || m > 1000 || b < 2 || b > 10) return -1;
        
        int sum = 0;
        char[] arr = s.toCharArray();
        // store the biggest factor
        BigInteger max = new BigInteger(String.valueOf(b));
        max = max.pow(k - 1);
        // the modulo
        BigInteger modVal = new BigInteger(String.valueOf(m));
        BigInteger base = new BigInteger(String.valueOf(b));
        
        int index = 0;
        BigInteger tmpSum = new BigInteger("0");
        for (; index < k; index ++) {
            BigInteger cur = base.pow(k - index - 1).multiply( new BigInteger(String.valueOf(arr[index])) );
            tmpSum = tmpSum.add(cur);
        }
        
        sum += tmpSum.mod(modVal).intValue();
        
        for (int delPos = 0; index < arr.length; index ++) {
            // every round, always delete the first digit (always at 0)
            // for every k num, k + 1 = (k -  (index(0)) * 3 + new number 
            BigInteger reduce = new BigInteger( String.valueOf(arr[index - k]) );
            BigInteger inc = new BigInteger( String.valueOf(arr[index]) );                                            
            tmpSum = tmpSum.subtract( reduce.multiply(max) ).multiply(base).add(inc);
            sum += tmpSum.mod(modVal).intValue();
        }
        
        return sum;
    }
    */
	
	// method 5: still O(n), but without using BigInteger
	// *** java: Integer.valueOf('0') will get the ascii value of '0' not 0
	/*
	static int getMagicNumber(String s, int k, int b, int m){
		// Complete this function
		if (null == s || s.length() < 1 || k < 1 || k > s.length() 
                || m < 1 || m > 1000 || b < 2 || b > 10) return -1;
        
        int sum = 0;
        char[] arr = s.toCharArray();
        // store the biggest factor
        
       
        // the max of pow is k - 1
        
        int tempSum = arr[k - 1] - '0';
        int preRemain = 1;
        for (int i = k - 2; i > 0; i --) {
        	preRemain = preRemain * b  % m;
        	tempSum += (arr[i] - '0') * preRemain;
        }
        int scdRemain = preRemain;
        // *** the tempSum should not be bigger than the max_value of int, as the max of scdRemain = (max of m - 1) = 999
        // and every index, the max is 9. so for every sum of at index i, is 9 * 10 ^ 3
        // the max length of k is 3 * 10 ^ 5, so the max sum of k - 1, is 3 * 10 ^ 5 * (9 * 10 ^ 3)
        // so the max num at k is 9 * 10 * 10 ^ 3
        // the max sum is 9 * 10 * 10 ^ 3 + (3 * 10 ^ 5 * (9 * 10 ^ 3)) = 9 * 10^4 + 9*10^4*10*(3*10^3) = 9*10^4(1 + 3*10^4) = 27*10^8 = 2.7 *`10^9
        
        tempSum += (arr[0] - '0') * scdRemain * b;
        sum += tempSum % m;
        
        for (int index = k; index < arr.length; index ++) {
        	
        	// every k number is the tempSum of k - 1 + (arr[k] - '0') * scdRemain * b, so everytime the k substring shift
        	// subtract (arr[k] - '0') * scdRemain * b from the sum then * b
        	// *** will tempSum < 0 ? no, cause tempSum consists of (arr[k] - '0') * scdRemain * b and other numbers
        	tempSum = (tempSum - (arr[index - k] - '0') * scdRemain * b) * b + (arr[index] - '0');
        	sum += tempSum % m;
        }
        
        return sum;
    }
    */

}
