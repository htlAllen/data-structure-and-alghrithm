# 算法之电话号码的字符组合
## 描述
## 分析
## 思想
> 注： 不能使用多个for来遍历解决，应为digits的位数不同，导致写代码的时候不知道有多少个for循环。所以只能使用回溯法
## 代码
```java
class Solution{
    HashMap<String, String> map = new HashMap<String, String>() {
        {
            put("2", "abc");
            put("3", "def");
            put("4", "ghi");
            put("5", "jkl");
            put("6", "mno");
            put("7", "pqrs");
            put("8", "tuv");
            put("9", "wxyz");
        }
    };

    List<String> ret = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        backtrack("", digits, digits.length());
        return ret;
    }

    private void backtrack(String ans, String dights, int count){
        if(ans.length() == count){
            ret.add(ans);
            return;
        }
        String key = dights.substring(0, 1);
        String value = map.get(key);
        for(int i=0; i<value.length(); i++){
            backtrack(ans + value.substring(i, i+1), dights.substring(1), count);
        }
    }
}
```