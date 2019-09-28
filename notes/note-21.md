# 算法之移动零
## 描述
给定一个数组 nums，编写一个函数将所有 0 移动到数组的末尾，同时保持非零元素的相对顺序。
## 分析
遍历数组，选择满足条件的元素保存到数组中对应的位置上去
## 思路
1. 遍历数组，依此选择不为0的元素保存到数组的对应位置上去。对应位置由变量i控制
2. 遍历完毕后，以i变量所指的位置为起点，进行补零即可
## 代码
```java
class Solution {
    public void moveZeroes(int[] nums) {
        int i=0;
        for(int j=0; j<nums.length; j++){
            if(nums[j] != 0){
                nums[i++] = nums[j];
            }
        }
        while(i<nums.length){
            nums[i++] = 0;
        }
    }
}
```