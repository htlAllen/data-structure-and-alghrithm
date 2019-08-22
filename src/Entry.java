import java.util.HashMap;
public class Entry {

    public static void main(String[] args){

    }
}
/*
*
*
* */

class Test{
    public int[] twoSum(int[] nums, int target){
        HashMap<Integer, Integer> map = new HashMap<Integer, Integer>();
        int find;
        for(int i=0; i<nums.length; i++){
            map.put(nums[i], i);
        }
        for(int i=0; i<nums.length; i++){
            find = target - nums[i];
            if(map.containsKey(find) && map.get(find) != i){
                return new int[] {i, map.get(find)};
            }
        }
        throw new IllegalArgumentException("no");
    }
}