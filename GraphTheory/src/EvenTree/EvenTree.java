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

/**
 * 
 * To further optimise, can use dfs to build a count dictionary. so do not need to
 * revisit the node to get nodes count.
 * 
 * @author jingjiejiang
 * @history
 * 1. Created on Feb 14, 2018
 */
public class EvenTree {
	
	private static final String FILE_PATH = "src/EvenTree/sample3.txt";
	
	// dfs
	public static int getConnectCount (int row, int[][] graph, boolean[] visited, int parent) {
		
		int res = 1;
		visited[row] = true;
		
		int[] conNodes = graph[row];
		
		for (int i = 2; i < conNodes.length; i ++) {
			
			if (i != parent && conNodes[i] != 0 && visited[i] == false) {
				
				res += getConnectCount (i, graph, visited, row);
			}
		}
		
		return res;
	}
	
	public static int evenTree(int n, int m, int[][] tree) {

        // Complete this function
        int[][] graph = new int[n + 1][n + 1];
        
        // init graph
        for (int i = 0; i < tree.length; i ++) {
        	
            int row = tree[i][0];
            int col = tree[i][1];
            
            graph[row][col] = 1;  
            graph[col][row] = 1;
        }
        
        // from root node, check the following nodes whether it has even nodes
        // if it has, result + 1, add the node as a new root node for checking
        Queue<Integer> queue = new LinkedList<>();
        queue.offer(1);
        int res = 0;
        Set<Integer> visited = new HashSet<>();
        visited.add(1);
        
        while (queue.isEmpty() == false) {
            
            int tmpNode = queue.poll();
            
            // node start from 1, find all connected node of cur node
            for (int i = 2; i < graph.length; i ++) {
                
                if (graph[tmpNode][i] == 0 || visited.contains(i)) {
                    continue;
                }
                
                // use bidirectional count, so e.g. node 3 has 3 connections
                // (conCount[tmpNode] - 1) % 2 == 0. 1 is the root node.
                int count = getConnectCount(i, graph, new boolean[n + 1], tmpNode);
                if (count % 2 == 0) {
                    res ++;
                    
                } 
                
                if (count > 2 && visited.contains(i) == false) {
                    queue.offer(i);
                }
                
            }
            
            visited.add(tmpNode);
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
