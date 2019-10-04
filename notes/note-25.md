# 算法之零钱兑换
## 描述
给定不同面额的硬币 coins 和一个总金额 amount。编写一个函数来计算可以凑成总金额所需的最少的硬币个数。如果没有任何一种硬币组合能组成总金额，返回 -1。

## 分析
很正常的一套dp问题，状态很容易定义并且状态转移矩阵也很容易想到。但**实际编码时比较难**
## 思路
> 首先需要弄明白amount可能被筹齐也可能筹不齐，弄清除什么时候筹齐，什么时候筹不齐是关键所在

1. 定义状态矩阵states[amount+1], 定义硬币集合set；
2. 从1,...,amount遍历状态矩阵，依此填写状态矩阵states[amount]的值，针对每次编译做如下操作：
    1.  判断当前amount是否出现在硬币集合set中，若出现则states[i]=1,并结束当前循环，若未出现往下继续
    2.  设置最小值min，用来求最小值
    3.  遍历coins数组，做如下操作：
        1.  判断(i-coin)是否大于0，若大于0，则满足数组下标要求，进入下一步。若不满足结束本次循环
        2.  判断states[i-coin]是否能被筹齐，若能筹齐，进入下一步。若不能被筹齐，则结束本次循环
        3.  将states[i-coin]与最小值min比较，并更新最小值
    4.   判断min是否被更新，若被更新了则states[i]=min,若未被更新则states[i]=-1;
3. 返回states[amount]。

---
不用set集合完成
1. 定义状态矩阵states[amount+1], 定义硬币集合set；
2. 从1,...,amount遍历状态矩阵，依此填写状态矩阵states[amount]的值，针对每次编译做如下操作：
    1.  设置最小值min，用来求最小值
    2.  遍历coins数组，做如下操作：
        1.  判断(i-coin)是否大于**等于**0，若大于**等于**0，则满足数组下标要求，进入下一步。若不满足结束本次循环
        2.  判断states[i-coin]是否能被筹齐，若能筹齐，进入下一步。若不能被筹齐，则结束本次循环
        3.  将states[i-coin]与最小值min比较，并更新最小值
    3.   states[i]=min;
3. 判断states[amount]能否被筹齐，若能被筹齐则返回对应的值，若不能则返回-1。
## 代码
```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] states = new int[amount+1];  // 定义状态矩阵
        HashSet<Integer> set = new HashSet<>(); // 定义硬币集合
        int min;
        int tmp;
        for(int coin: coins){   // 初始化硬币集合
            set.add(coin);
        }
        for(int i=1; i<=amount; i++){
            if(set.contains(i)){  // 对应当前面额i，如果硬币集合中包含i则states[i]=1
                states[i]=1;
                continue;
            }
            min = Integer.MAX_VALUE;  // 初始化min，在下面遍历coins的时候，用来保存最小值
            for(int coin: coins){
                tmp = i - coin; // tmp表示states状态矩阵的下标
                if(tmp>=1 && states[tmp] != -1){ // 如下标合法并且states[tmp]可以被筹齐，则更新min
                    min = Math.min(states[tmp], min);
                }
            }
            if(min == Integer.MAX_VALUE){  // 判断min是否被更新，若未被更新，则说明当前面额i不能被筹齐
                states[i] = -1;
            }else{
                states[i] = min+1;
            }
            
        }
        return states[amount];
    }
}
```
---
```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] states = new int[amount+1];    // 定义状态矩阵
        int min,tmp;
        for(int i=1; i<=amount; i++){
            min = Integer.MAX_VALUE;   // // 初始化min，在下面遍历coins的时候，用来保存最小值
            for(int coin: coins){
                tmp = i - coin; // tmp表示states状态矩阵的下标
                if(tmp>=0 && states[tmp] != Integer.MAX_VALUE){
                    min = Math.min(min, states[tmp] + 1); // 如下标合法并且states[tmp]可以被筹齐，则更新min
                }
            }
            states[i] = min;
        }
        return states[amount]==Integer.MAX_VALUE? -1:states[amount];
    }
}
```