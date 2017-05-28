import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Improve from O(n) to Log(n), still have two test cases got timeout error.
 * 
 * @author jingjiejiang
 * @history May 28 ,2017
 */
public class SimpleFileCommands {
	
	// method 1: addFile O(n) complexity 
	/*
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        // Process each command
        Map<String, List<Integer>> map = new HashMap<>();
        
        for(int a0 = 0; a0 < q; a0++){
            // Read the first string denoting the command type
            String command = in.next();
            // Write additional code to read the command's file name(s) and process the command
            command = command.trim();
            String res = "";
            switch (command) {
                case "crt": 
                    res = "+ " + addFile(map, in.next());
                    break ;
                case "del":
                    res = delFile(map, in.next());
                    break;
                case "rnm":
                    res = rnmFile(map, in.next(), in.next());
                    break;
                default:
                    break;
            }
            // Print the output string appropriate to the command
            System.out.println(res);
        }
    }
    
    static String addFile(Map<String, List<Integer>> map, String fileName) {
        
        if (map.containsKey(fileName) == false) {
            List<Integer> tmpList = new LinkedList<>();
            tmpList.add(0);
            map.put(fileName, tmpList);
            return fileName;   
        }
        else {
            List<Integer> tmpList = map.get(fileName);
            int size = tmpList.size();
            // list contains at least one ele, otherwise it should not exist in the map
            // include only has one ele size = 1, ele val = 0;
            // ***test  for (int i : tmpList) System.out.println(i);
            if (tmpList.get(size - 1) == size - 1) {
               tmpList.add(size);
               // *** test System.out.println("a***");
               return fileName + "(" + size + ")";
            } 
            else {
                // refactor
                int preIndex = 0;
                int postIndex = 1;
                // if 1 3, then add 0 to 0
                while (tmpList.get(0) == 0 && postIndex < size) {
                    int pre = tmpList.get(preIndex ++);
                    if (tmpList.get(postIndex ++) - pre > 1) {
                        // *** test System.out.println("b***");
                        tmpList.add(pre + 1, pre + 1);
                        return fileName + "(" + (pre + 1) + ")";
                    }
                }
                // (size == 1 && tmpList.get(0) > 0)
                tmpList.add(0, 0);
                // *** test System.out.println("c***");
                return fileName;
            }
        }
    }
    
    static String delFile(Map<String, List<Integer>> map, String fileName) {
        
        String res = "";
        String pre = fileName;
        int num = 0;
        int numIndex = fileName.indexOf("(");
        if (numIndex > 0) {
            pre = fileName.substring(0, numIndex);
            // fileName.length() - 1) exclude ")"
            num = Integer.valueOf( fileName.substring(numIndex + 1, fileName.length() - 1) );
        }
        List<Integer> list = map.get(pre);
        int index = Collections.binarySearch(list, num);
        list.remove(index);
        res += "- " + fileName;
        if (list.isEmpty() == true) map.remove(pre);
        
        // *** test for (int i : list) System.out.println(i);
        return res;
    }
    
    static String rnmFile(Map<String, List<Integer>> map, String del, String add) {
        
        delFile(map, del);
        String addRes = addFile(map, add);
        
        return "r " + del + " -> " + addRes;
    }
    */
	
	// Method 2: O(log(n)) for AddFile (use binarySearch)
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int q = in.nextInt();
        // Process each command
        Map<String, List<Integer>> map = new HashMap<>();
        
        for(int a0 = 0; a0 < q; a0++){
            // Read the first string denoting the command type
            String command = in.next();
            // Write additional code to read the command's file name(s) and process the command
            // command = command.trim();
            String res = "";
            switch (command) {
                case "crt": 
                    res = "+ " + addFile(map, in.next());
                    break ;
                case "del":
                    res = delFile(map, in.next());
                    break;
                case "rnm":
                    res = rnmFile(map, in.next(), in.next());
                    break;
                default:
                    break;
            }
            // Print the output string appropriate to the command
            System.out.println(res);
        }
    }
    
    static String addFile(Map<String, List<Integer>> map, String fileName) {
        
        if (map.containsKey(fileName) == false) {
            List<Integer> tmpList = new LinkedList<>();
            tmpList.add(0);
            map.put(fileName, tmpList);
            return fileName;   
        }
        else {
            List<Integer> tmpList = map.get(fileName);
            int size = tmpList.size();
            // list contains at least one ele, otherwise it should not exist in the map
            // include only has one ele size = 1, ele val = 0;
            // ***test  for (int i : tmpList) System.out.println(i);
            if (tmpList.get(size - 1) == size - 1) {
               tmpList.add(size);
               // *** test System.out.println("a***");
               return fileName + "(" + size + ")";
            } 
            else {
                
                // use binary search methods O(n) --> O(log(n))
                if (tmpList.get(0) == 0) {
                    
                    int pos = biSearch(tmpList);
                    tmpList.add(pos, pos);
                    // for (int i : tmpList) System.out.println(i + " " + tmpList.get(i));
                    return fileName + "(" + pos + ")";
                }
                // (size == 1 && tmpList.get(0) > 0)
                tmpList.add(0, 0);
                // *** test System.out.println("c***");
                return fileName;
            }
        }
    }
    
    static int biSearch(List<Integer> tmpList) {
        
        int low = 0;
        int high = tmpList.size() - 1;
        int pos = 0;

        while(low <= high ) {
            int mid = (low+high) /2;
            if (tmpList.get(mid) == mid){
                if (tmpList.get(mid + 1) > mid + 1) {
                    pos = mid + 1;
                    break;
                }
                //System.out.println("mid+1 ==" + (mid + 1));
                if (tmpList.get(mid + 1) == mid + 1) low = mid + 1;
            } else if (tmpList.get(mid) > mid){
                if (tmpList.get(mid - 1) == mid - 1) {
                    pos = mid;
                    break;
                }
                //System.out.println("mid ==" + mid);
                if (tmpList.get(mid - 1) > mid - 1) high = mid - 1;
            } else { // The element has been found
                break;
            }
        }
        return pos;
    }
                             
    static String delFile(Map<String, List<Integer>> map, String fileName) {
        
        String res = "";
        String pre = fileName;
        int num = 0;
        
        // avoid use indexOf (interate from start to end, and substring() )
        int numIndex = 0;
        int i = fileName.length() - 1;
        if (fileName.charAt(i) == ')') {
            StringBuilder numBuilder = new StringBuilder();
            StringBuilder nameBuilder = new StringBuilder();
            for (i -= 1; i >= 0; i --) {
                char tmp = fileName.charAt(i);
                if ( tmp >= '0' && tmp <= '9') numBuilder.insert(0, tmp);
                else if (tmp == '(') continue;
                else if (tmp >= 'a' && tmp <= 'z') nameBuilder.insert(0, tmp);
            }
            pre = nameBuilder.toString();
            num = Integer.valueOf(numBuilder.toString());
        }
        
        List<Integer> list = map.get(pre);
        // refactor 
        int index = Collections.binarySearch(list, num);
        list.remove(index);
        res += "- " + fileName;
        if (list.isEmpty() == true) map.remove(pre);
        
        // *** test 
        //for (int i : list) System.out.println(i);
        return res;
    }
    
    static String rnmFile(Map<String, List<Integer>> map, String del, String add) {
        
        delFile(map, del);
        String addRes = addFile(map, add);
        
        return "r " + del + " -> " + addRes;
    }
	
}
