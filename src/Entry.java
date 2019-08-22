import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
public class Entry {

    public static void main(String[] args){
        int[] x = {2,4,1,5,6};
        Arrays.sort(x);
        System.out.println(Arrays.toString(x));
    }
}
/*
*
*
* */

class Test{
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> res = new ArrayList<>();
        // 排好序了，从小到大
        Arrays.sort(nums);
        int L, R;
        int pre=-1000;
        for(int i=0; i<nums.length-2; i++){
            if(nums[i] > 0){
                break;
            }
            if(pre == nums[i]){
                continue;
            }
            L = i+1;
            R = nums.length - 1;
            while(L < R){
                if(nums[i] + nums[L] + nums[R] == 0){
                    res.add(Arrays.asList(nums[i], nums[L], nums[R]));
                    L++;
                    R--;
                } else if(nums[i] + nums[L] + nums[R] > 0){
                    R--;
                }else{
                    L++;
                }
            }
            pre = nums[i];
        }
        // 程序结果：[[-1,-1,2],[-1,0,1],[-1,0,1]]
        // 正确结果：[[-1,-1,2],[-1,0,1]]
        return res;
    }
}