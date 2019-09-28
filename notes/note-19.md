# 算法之移除元素
## 描述
给定一个数组 nums 和一个值 val，你需要原地移除所有数值等于 val 的元素，返回移除后数组的新长度。
## 分析
双指针，一个指针用来遍历数组，一个指针用来指向当前插入元素的位置，遍历的过程中选择满足条件的放到数组中去
## 思路

## 代码
```java
class Solution {
    // 拷贝覆盖
    public int removeElement(int[] nums, int val) {
        int i=0;
        for(int j=0; j<nums.length; j++){
            if(nums[j] != val){
                nums[i++] = nums[j];
            }
        }
        return i;
    }
}

```