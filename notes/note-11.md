# 算法之全排列
## 描述
## 分析
## 思路
## 代码
#### 正确解答
```java
class Solution {

    List<List<Integer>> ret = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums) {
        backtrack(new ArrayList<>(), nums, 0);
        return ret;
    }

    public boolean iscontains(List<Integer> ans, int target){
        for(Integer x: ans){
            if(target == x){
                return true;
            }
        }
        return false;
    }

    public void backtrack(List<Integer> ans, int[] nums, int count){
        if(count == nums.length){
            ret.add(new ArrayList<>(ans));
            return;
        }
        for(int i=0; i<nums.length; i++){
            if(!iscontains(ans, nums[i])){
                ans.add(nums[i]);
                backtrack(ans, nums, count+1);
                ans.remove(count);
            }
        }
    }
}
```
#### 错误解答
```java
class Solution{
    List<List<Integer>> ret = new ArrayList<>();

    public List<List<Integer>> permute(int[] nums){
        int count=0;
        backtrack(new ArrayList<>(), nums, count);
        return ret;
    }

    public boolean isContain(ArrayList<Integer> ans, int target){
        for(Integer x: ans){
            if(x == target){
                return true;
            }
        }
        return false;
    }

    public void backtrack(ArrayList<Integer> ans, int[] nums, int count){
        if(count == nums.length){
            ret.add(new ArrayList<>(ans));
        }
        for(int i=0; i<nums.length; i++){
            if(!isContain(ans, nums[i])){
                ans.add(nums[i]);
                // count = count + 1 导致出错
                count = count + 1;
                backtrack(ans, nums, count);
                ans.remove(count);
            }
        }
    }
}
```