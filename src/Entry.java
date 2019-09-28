import org.omg.IOP.ENCODING_CDR_ENCAPS;


import java.lang.Thread;
import java.util.concurrent.TimeUnit;

public class Entry{
    public static int removeDuplicates(int[] nums) {
        int i=0;
        for(int j=1; j<nums.length; j++){
            if(nums[i] != nums[j]){
                i++;
                nums[i] = nums[j];
            }
        }
        return i;
    }
    public static void main(String[] args){
        int[] nums = new int[]{1, 1, 2};
        Entry.removeDuplicates(nums);
    }
}
