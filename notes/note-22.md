# 算法之合并两个有序数组
## 描述
给定两个有序整数数组 nums1 和 nums2，将 nums2 合并到 nums1 中，使得 num1 成为一个有序数组。
说明:
- 初始化 nums1 和 nums2 的元素数量分别为 m 和 n。
- 你可以假设 nums1 有足够的空间（空间大小大于或等于 m + n）来保存 nums2 中的元素。
## 分析
遍历两个数组，进行大小比较。然后再用一个指针指向要插入数字的位置。
## 思路
1. 应该选择最大的元素进行插入。而不是选择最小的元素进行插入。
2. 边界情况的考虑
## 代码
```java
class Solution {
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int len1 = m - 1;
        int len2 = n - 1;
        int k = m + n - 1;
        
        // len1>=0, len2>=0 确保了数组不会出现下标错误的问题
        while(len1 >= 0 && len2 >=0){
            if(nums1[len1] > nums2[len2]){
                nums1[k--] = nums1[len1--];
            }else{
                nums1[k--] = nums2[len2--];
            }
        }
        
        // 其他情况的考虑
        while(len2 >= 0){
            nums1[k--] = nums2[len2--];
        }
    }
}
```