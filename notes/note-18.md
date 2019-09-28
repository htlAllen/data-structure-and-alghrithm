# 算法之在排序数组中查找元素的第一个和最后一个位置
## 描述
给定一个按照升序排列的整数数组 nums，和一个目标值 target。找出给定目标值在数组中的开始位置和结束位置。
## 分析
二分法的升级版本，即找到目标值还不行，还需要判断是否是第一个（最后一个）出现的，若是第一个（最后一个）则结束，若不是，则继续二分查找
## 思路
1. 在正常二分查找的基础上添加如下操作：
2. 如果当前元素为目标元素则进行判断，若是第一个（最后一个）出现的，则结束，若不是，则继续二分查找
> 注意：考虑到边界条件非常关键
## 代码
```java
class Solution {
    public int[] searchRange(int[] nums, int target) {
        int[] ret = new int[]{-1, -1};
        ret[0] = findFirst(nums, target);
        ret[1] = findLast(nums, target);
        return ret;
    }
    public int findFirst(int[] nums, int target){
        int low=0, high=nums.length-1;
        int mid = (low + high) / 2;
        while(low<=high){
            mid = (low + high) / 2;
            if(nums[mid]==target){
                // 判断是否是第一个,但是要注意边界情况
                // || 逻辑判断符--》如果mid==0成立，后边的便不会进行判断
                
                if(mid==0 || nums[mid-1]!=target){
                    // 这是第一个
                    return mid;
                }else{
                    // 这个不是第一个，继续二分查找
                    high = mid - 1;
                }
            }else if(nums[mid]>target){
                    high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return -1;
    }
    public int findLast(int[] nums, int target){
        int low=0, high=nums.length-1;
        int mid = (low + high) / 2;
        while(low<=high){
            mid = (low + high) / 2;
            if(nums[mid]==target){
                // 判断是否是最后一个，同理也需要判断边界条件
                if(mid == nums.length-1 || nums[mid+1]!=target){
                    // 是最后一个
                    return mid;
                }else{
                    // 不是最后一个，继续二分查找
                    low = mid + 1;
                }
            }else if(nums[mid]>target){
                    high = mid - 1;
            }else{
                low = mid + 1;
            }
        }
        return -1;        
    }
}
// [1] 1  --> [0, 0]
// [5,7,7,8,8,10] 8 --> [3, 4];
// [5,7,7,8,8,10] 6 --> [-1, -1]
```