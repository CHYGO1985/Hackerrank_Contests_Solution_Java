import java.util.Arrays;
import java.util.Scanner;


public class FightTheMonsters {

	static int getMaxMonsters(int n, int hit, int t, int[] h){
        // Complete this function
        if (n <= 0 || hit <= 0 || t <= 0) return 0;
        
        Arrays.sort(h);
        int killed = 0;
        for (int mon : h) {
            int times = mon / hit;
            if (mon % hit != 0) times ++;
            t -= times;
            if (t < 0) return killed;
            killed ++;
        }
        
        return killed;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int hit = in.nextInt();
        int t = in.nextInt();
        int[] h = new int[n];
        for(int h_i=0; h_i < n; h_i++){
            h[h_i] = in.nextInt();
        }
        int result = getMaxMonsters(n, hit, t, h);
        System.out.println(result);
    }
}
