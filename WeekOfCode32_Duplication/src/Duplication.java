import java.util.Scanner;

// Week of Code 32_Duplication
// JJJ May 16, 2017
// Pass all test cases
public class Duplication {
	
	static String duplication(int x){
        // Complete this function
        // build string s, when length >=x, stop
        if (x < 0) return String.valueOf(x);
        String s = "0";
        while (s.length() <= x) {
            StringBuilder builder = new StringBuilder();
            char[] wordS = s.toCharArray();
            char[] wordT = new char[wordS.length];
            for (int i = 0; i < wordT.length; i++) {
                wordT[i] = (char)('0' + ('1' - wordS[i]));
            }
            
            builder.append(s).append(wordT);
            s = builder.toString();
        }
        
        return String.valueOf(s.charAt(x));
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            int x = in.nextInt();
            String result = duplication(x);
            System.out.println(result);
        }
    }

}
