import javafx.util.Pair;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
public class Entry {

    public static void main(String[] args) {
        Permute x = new Permute();
        List<List<Integer>> b;
        b = x.permute(new int[]{1,2,3});
        for(List<Integer> a: b){
            for(Integer c: a){
                System.out.print(c+" ");
            }
            System.out.println();
        }
    }
}
class Permute{
    int[] nums;
    List<List<Integer>> ret = new ArrayList<>();
    public List<List<Integer>> permute(int[] nums) {
        this.nums = nums;
        backtrack(0, nums.length, new ArrayList<>());
        return ret;
    }
    private boolean isContain(List<Integer> ans, int target){
        for(Integer x: ans){
            if(x == target){
                return true;
            }
        }
        return false;
    }
    public void backtrack(int current, int allNeed, List<Integer> ans){
        if(current == allNeed){
            ret.add(new ArrayList<>(ans));
            return;
        }
        for(int i=0; i<nums.length; i++){
            if(!isContain(ans, nums[i])){
            ans.add(nums[i]);
            backtrack(current+1, allNeed, ans);
            ans.remove((Integer)nums[i]);
            }
        }
    }
}