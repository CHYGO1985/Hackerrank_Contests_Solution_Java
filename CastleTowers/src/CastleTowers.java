
public class CastleTowers {
	
	static int castleTowers(int n, int[] ar) {
        /*
        Arrays.sort(ar, Collections.reverseOrder());
        int maxi = ar[0];
        int cnt = 1;
        for (int i = 1; i < n; i++) {
            if (maxi == ar[i]) {
                cnt += 1;
            } else {
                break;
            }
        }
        */
        
        int max = Integer.MIN_VALUE;
        int cnt = 1;
        
        for (int i = 0; i < n; i ++) {
            if (ar[i] > max) {
                max = ar[i];
                cnt = 1;
            }
            else if (ar[i] == max) {
                cnt ++;
            }
        }
        
        return cnt;
    }

}
