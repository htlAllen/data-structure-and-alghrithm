import javafx.util.Pair;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.HashSet;
public class Entry {

    public static void main(String[] args){
        int[] x = {8, 11, 19, 23, 30, 30, 30, 37};
        Test y = new Test();
        System.out.println(y.binearSearch(x, 20));

    }
}
/*
*
*
* */

class Test{
    public int binearSearch(int nums[], int target) {
        int mid, low, high;
        low = 0;
        high = nums.length - 1;
        Arrays.sort(nums);
        // 此处需要等号，当有等号时，mid可以取到下标为high（low）的值，若没有等会将会取不到该值，就会出现漏找的情况
        while (low <= high) {
            // 取中间值可以优化
            // mid = low + ((high - low) >> 1);
            mid = (low + high) / 2;
            if (nums[mid] <= target) {
                if(nums[mid+1] <= target){
                    low = mid + 1;
                }else{
                    return mid;
                }

            } else {
                high = mid - 1;
            }
        }
        return -1;
    }
}