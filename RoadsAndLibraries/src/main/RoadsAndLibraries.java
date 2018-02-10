package main;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

/**
 * 
 * Union-find.
 * 
 * @author jingjiejiang
 * @history
 * 1. Created on Fen 11, 2018
 */
public class RoadsAndLibraries {
	
	private static final String FILE_PATH = "src/main/test1.txt";
	
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
	
	// find the root of i
    static int findRoot (int[] root, int i) {
        
        while (i != root[i]) {
        	root[i] = root[root[i]];
            i = root[i];
        }
        
        return i;
    }

    static long roadsAndLibraries(int n, int c_lib, int c_road, int[][] cities) {
        // Complete this function
        // m: num of roads
        
        int[] root = new int[n + 1];
        // init union set
        for (int i = 0; i < root.length; i ++) {
            root[i] = i;
        }
        
        // assign min val as all cities has its library
        // *** need to convert to long before *
        long res = (long)c_lib * (long)n; 
        
        // get roads one by one, check if costs < min val
        // (build one road, then reduce one libray)
        // if the 1st road cost more than build all library, return min val directly
        for (int i = 0; i < cities.length; i ++) {
            
            int[] routes = cities[i];
            int start = routes[0];
            int end = routes[1];
            // check whether start and end are in the same set ()
            if (findRoot(root, start) == findRoot(root, end)) {
                continue;
            }
            
            // update roots of start and end
            int rootStart = findRoot(root, start);
            int rootEnd = findRoot(root, end);
            root[rootStart] = rootEnd;
            
            // get the current min cost
            long tmpMin = res - c_lib + c_road;
            if (tmpMin > res) {
                break;
            }
            else {
                res = tmpMin;
            }
        }
        
        // return min val
        return res;
    }


	public static void main(String[] args) {
		
		List<String> inputs = getInput(FILE_PATH);
		
		int q = Integer.valueOf(inputs.get(0));
		int line = 1;
        for(int a0 = 0; a0 < q; a0++){
        	
        	String[] strs = inputs.get(line).split(" ");
            int n = Integer.valueOf(strs[0]);
            int m = Integer.valueOf(strs[1]);
            int c_lib = Integer.valueOf(strs[2]);
            int c_road = Integer.valueOf(strs[3]);
            int[][] cities = new int[m][2];
            
            // move to next line
            line++;
            
            for(int cities_i = 0; cities_i < m; cities_i++){
            	
            	String[] temp = inputs.get(line).split(" "); 
            			
                for(int cities_j = 0; cities_j < 2; cities_j++){
                    cities[cities_i][cities_j] = Integer.valueOf(temp[cities_j]);
                }
                line ++;
            }
            
            long result = roadsAndLibraries(n, c_lib, c_road, cities);
            System.out.println(result);
        }
	}

}
