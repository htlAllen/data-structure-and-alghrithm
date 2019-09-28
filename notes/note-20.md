# 算法之删除排序数组中的重复项
## 描述
给定一个排序数组，你需要在原地删除重复出现的元素，使得每个元素只出现一次，返回移除后数组的新长度。
## 分析
## 思路
1. 遍历数组，若该数不重复，则将其添加到数组中，若其重复，则不添加到数组中
## 代码
```java
class Solution {
    public int removeDuplicates(int[] nums) {
        int i=0;
        for(int j=1; j<nums.length; j++){
            if(nums[i] != nums[j]){
                i++;
                nums[i] = nums[j];
            }
        }
        return i+1;
    }
}
```