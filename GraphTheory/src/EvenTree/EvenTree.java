package EvenTree;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

public class EvenTree {
	
	private static final String FILE_PATH = "src/EvenTree/sample.txt";
	
	public static int evenTree(int n, int m, int[][] tree) {

        // Complete this function
        int[][] graph = new int[n + 1][n + 1];
        int[] conCount = new int[n + 1]; 
        
        // init graph
        for (int i = 0; i < m; i ++) {
            
            int row = tree[i][0];
            int col = tree[i][1];
                        
            graph[row][col] = 1;  
            graph[col][row] = 1;
        }
        
        // get the count of nodes that a node directly connects to
        for (int i = 1; i < graph.length; i ++) {
            for (int j = 1; j < graph[0].length; j ++) {
                if (graph[i][j] == 1) {
                    conCount[i] ++;  
                }
            }
        }
        
        // from bottom to top, get the number of connected nodes count
        Queue<Integer> nodes = new LinkedList<>();
        Set<Integer> checked = new HashSet<>();
        boolean[] visited = new boolean[n + 1];
        
        // add all leaves to the queue
        for (int i = 1; i < conCount.length; i ++) {
            
            if (conCount[i] == 1) {
                nodes.offer(i);
                checked.add(i);
                visited[i] = true;
            }
            else {
            	// reset non-leaf node counts
            	conCount[i] = 1;
            }
        }
        
        while (nodes.isEmpty() == false) {
            
            int curNode = nodes.poll();
            if (curNode == 1) continue;
            
            for (int i = 1; i < graph.length; i ++) {
                if (graph[curNode][i] == 0 && visited[i] == true) {
                    continue;
                }
                
                conCount[i] += conCount[curNode];
                if (visited[i] == false) {
                	nodes.offer(i);
                	visited[i] = true;
                }
            }
        }
        
        int res = 0;
        
        // test
        for (int i = 1; i < conCount.length; i ++) {
            System.out.println("i: " + i + " val:" + conCount[i]);
        }
        
        // from root node, check the following nodes whether it has even nodes
        // if it has, result + 1, add the node as a new root node for checking
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        
        while (queue.isEmpty() == false) {
            
            int tmpNode = queue.poll();
            
            // node start from 1, find all connected node of cur node
            for (int i = 1; i < graph.length; i ++) {
                
                if (graph[tmpNode][i] == 0) {
                    continue;
                }
                
                // use bidirectional count, so e.g. node 3 has 3 connections
                // (conCount[tmpNode] - 1) % 2 == 0. 1 is the root node.
                if (conCount[tmpNode] % 2 != 0) {
                    res ++;
                    
                    // cut the tree, reduce node count
                    conCount[tmpNode] -= conCount[i];
                    conCount[i] --;
                }          

                
                
                if (conCount[i] > 2) {
                    queue.offer(i);
                }
            }
        }
        
        return res;
    }
	
	public static List<String> getInput (String path) {
		
		List<String> inputList = new LinkedList<>();
		
		try {

            File file = new File(path);
            BufferedReader bufReader = new BufferedReader(new FileReader(file));
            String readLine = "";

            while ((readLine = bufReader.readLine()) != null) {
                // System.out.println(readLine);
                inputList.add(readLine);
            }
            
            bufReader.close();

        } catch (IOException ex) {
            System.out.println ("Fail to read the input from text file: " +
            		ex.getMessage());
            System.exit(0);
        }
		
		return inputList;
	}

	public static void main(String[] args) {
		
		List<String> inputs = getInput(FILE_PATH);
		
		String[] first = inputs.get(0).split(" ");
		int n = Integer.valueOf(first[0]);
        int m = Integer.valueOf(first[1]);
        int[][] tree = new int[m][2];
        
        int line = 1;
        
        for(int tree_i = 0; tree_i < m; tree_i++){
        	
        	String[] tmp = inputs.get(line).split(" ");
        	
            for(int tree_j = 0; tree_j < 2; tree_j++){
                tree[tree_i][tree_j] = Integer.valueOf(tmp[tree_j]);
            }
            
            line ++;
        }
        int result = evenTree(n, m, tree);
        System.out.println(result);
	}

}
