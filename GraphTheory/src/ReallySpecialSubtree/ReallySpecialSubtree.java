package ReallySpecialSubtree;

import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Scanner;
import java.util.Set;

/**
 * 
 * quick union + path compression
 * 
 * @author jingjiejiang
 * @history
 * 1. Created on Feb 15, 2018
 */
public class ReallySpecialSubtree {

	static int findRoot (int i, int[] root) {
        
        while (i != root[i]) {
            root[i] = root[root[i]];
            i = root[i];
        }
        
        return i;
    }

    static int mst(int n, int[][] edges) {
        // Complete this function
        
        int res = 0;
        
        // set to check whether all the nodes has been added
        Set<Integer> added = new HashSet<>();
        
        // roots array
        int[] root = new int[n + 1];
        for (int i = 0; i < root.length; i ++) {
        	root[i] = i;
        }
        
        // use priority queue to sort edges
        PriorityQueue<int[]> queue = new PriorityQueue<>(new Comparator<int[]>(){
           
            @Override
            public int compare (int[] a, int[] b) {
            	if (a[2] != b[2]) {
                    return a[2] - b[2];
                }
                else {
                    return (a[0] + a[1] + a[2]) - (b[0] + b[1] + b[2]);
                }
            }
        });
        
        // init priority queue
        for (int i = 0; i < edges.length; i ++) {
            queue.offer(edges[i]);
        }
        
        // use quick union + path compression to check whether the new nodes of the new edge are in the set
        while (queue.isEmpty() == false) {
            
            int[] tmp = queue.poll();
            
            int startRoot = findRoot(tmp[0], root);
            int endRoot = findRoot(tmp[1], root);
            // if in the same set already, skip
            if (startRoot == endRoot) {
            	
            	if (added.size() >= n) {
                    break;
                }
                continue;
            }
            
            // add to root table
            root[startRoot] = endRoot;
            
            res += tmp[2];
            
            added.add(tmp[0]);
            added.add(tmp[1]);
            
            // if not add to the set if set size = n, then quit 
            
        }
        
        return res;
    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        int m = in.nextInt();
        int[][] edges = new int[m][3];
        for(int edges_i = 0; edges_i < m; edges_i++){
            for(int edges_j = 0; edges_j < 3; edges_j++){
                edges[edges_i][edges_j] = in.nextInt();
            }
        }
        int result = mst(n, edges);
        System.out.println(result);
        in.close();
    }

}
