# 算法之寻找缺失的第一个正整数
## 描述
给定一个未排序的整数数组，找出其中没有出现的最小的正整数。
## 分析
1. 这个最小正整数的范围是确定的
2. 可以利用数组的下标作为一个范围
> 本质上就是一个萝卜一个坑
## 思路
1. 遍历数组nums
2. 如果数组中的值不满足一下所有条件则进入第3步
    1. 数组中的值不等于数组下标+1
    2. 数组中的值要大于1或者小于数组的长度
    3. 要交换的那个元素不能和当前的值相等
3. 将当前的值存放的下标为当前值位置的数组中去
## 代码
```java
class Solution {
    public int firstMissingPositive(int[] nums) {
        int len = nums.length, i=0;
        int tmp;
        while(i < len){
            // 萝卜和坑对应上了，或者萝卜没有坑放了
            // nums[i] == nums[nums[i]-1]是防止重复的情况
            if(nums[i] == (i+1) || nums[i] < 1 || nums[i] > len || nums[i] == nums[nums[i]-1]){
                i++;
                continue;
            }
            tmp = nums[nums[i]-1];
            nums[nums[i]-1] = nums[i];
            nums[i] = tmp;
        }
        
        for(i=0; i<len; i++){
            if(nums[i] != (i+1)){
                break;
            }
        }
        
        return i+1;
    }
}
```