package ShortestReach;

import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 * Dijkstra Algorithm.
 * 
 * @author jingjiejiang
 * @history
 * 1. Created on Feb 20, 2018
 */
public class ShortestReach {

	// dijkstra
	// n: number of nodes, m: number of edges, s: starting node
	static int[] bfs(int n, int m, int[][] edges, int s) {
        // Complete this function
		
		int[] res = new int[n - 1];
		// adjacent matrix
        int[][] graph = new int[n + 1][n + 1];
        // array to record shortest route for each node
        int[] distance = new int[n + 1];
        // nodes has been counted
        boolean[] isCounted = new boolean[n + 1];
        Arrays.fill(isCounted, false);
        
        // init adjacent matrix with edges
        for (int i = 0; i < edges.length; i ++) {
        	
        	int row = edges[i][0];
        	int col = edges[i][1];
        	
        	graph[row][col] = graph[col][row] = 6;
        }
        
        // init distance array
        for (int i = 1; i < graph[0].length; i ++) {
        	
        	if (graph[s][i] != 0) {
        		distance[i] = graph[s][i];
        	}
        	else {
        		distance[i] = Integer.MAX_VALUE;
        	}
        }
        
        // use dijksrta algorithm
        
        for (int i = 2; i <= n; i ++) {
        	
        	int curDis = Integer.MAX_VALUE;
        	// 1) get the current shortest route from s to X
        	int curNode = 0;
        	for (int j = 1; j <= n; j ++) {
        		
        		if (isCounted[j] == false && distance[j] < curDis) {
        			curNode = j;
        			curDis = distance[j];
        		}
        	}
        	
        	isCounted[curNode] = true;
        
        	// 2) check whether from s-X-Y is shorter than from s-Y
        	// if so update the shortest distance of Y
        	for (int y = 1; y <= n; y ++) {
        		
        		if (isCounted[y] == false && 
        			graph[curNode][y] > 0 &&
        			curDis + graph[curNode][y] < distance[y]) {
        			
        			distance[y] = curDis + graph[curNode][y];
        		}
        	}
        }
        
        // covert distance to res array
        for (int i = 0, j = 1; i < res.length; i ++, j ++) {
        	
        	if (j == s) {
        		i --;
        		continue;
        	}
        	
        	res[i] = distance[j] != Integer.MAX_VALUE ?
        			distance[j] : -1;
        }
        
        return res;
    }

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        for(int a0 = 0; a0 < q; a0++){
            int n = in.nextInt();
            int m = in.nextInt();
            int[][] edges = new int[m][2];
            for(int edges_i = 0; edges_i < m; edges_i++){
                for(int edges_j = 0; edges_j < 2; edges_j++){
                    edges[edges_i][edges_j] = in.nextInt();
                }
            }
            int s = in.nextInt();
            int[] result = bfs(n, m, edges, s);
            for (int i = 0; i < result.length; i++) {
                System.out.print(result[i] + (i != result.length - 1 ? " " : ""));
            }
            System.out.println("");


        }
        in.close();

	}

}
