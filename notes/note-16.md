# 算法之0-1背包问题
## 描述
## 分析
## 思路
## 代码
```java
import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

public class Bag {
    // 一些列物品，一个最大承重的背包，求不超过背包承重的情况下，所能装的最大重量
    private int n=5; // 物品的个数
    private int w=9; // 背包的最大承重
    private int[] weight={2, 2, 4, 6, 3};
    private int maxWeight = Integer.MIN_VALUE;

    // 分析：物品的个数为总的阶段数，本例中有五个物品，所有有五个阶段。每个阶段对应选择（每个物品对应的选择）为是和否
    // 此时，带入代码模板就可以很快求出结果
    public void solveProblem(){
        backtrack(0, 0);
        System.out.println(maxWeight);
    }
    // 可以进行优化，可以进行剪枝操作，但是不会，后续会了加上
    public void backtrack(int count, int weightOfBag){
        /*
        * @count: 表示当前是第几个物品
        * @weightOfBat: 表示当前装入的重量
        * */
        if(count == 5){
            return;
        }
        // for()表示所有可能的选择
        // i=0:表示不放入背包， i=1:表示放入背包
        for(int i=0; i<2; i++){
            if(i == 0){
                maxWeight = Math.max(weightOfBag, maxWeight);
                backtrack(count + 1, weightOfBag);
            }
            if(i == 1) {
                if (weightOfBag + weight[count] <= 9) {
                    maxWeight = Math.max(weightOfBag + weight[count], maxWeight);
                    backtrack(count + 1, weightOfBag + weight[count]);
                }
            }
        }
    }
    public static void main(String[] args){
        Bag x = new Bag();
        x.solveProblem();

        Bag2 y = new Bag2();
        y.solveProblem();
    }
}

class Bag2{
    // 假设有一个能装入容量为C的背包和n件重量分别为w1,w2,,...,wn的物品，
    // 能否从n件物品中挑选若干件恰好装满背包,要求找出所有满足上述条件的解。
    // 输入：C=10，各件物品重量为{1,8,4,3,5,2}，
    //                           0,1,2,3,4,5
    // 输出：(1,4,3,2)、(1,4,5)、(8,2)和(3,5,2)
    // 实际输出：(3,5,2)，(8,2)，(1,4,5)，(1,4,5)，(1,4,3,2)  为什么会重复啊？
    private int a = 0;
    private int w = 10;
    private int[] weight = {1,8,4,3,5,2};
    private List<List<Integer>> ret = new ArrayList<>();
    public void solveProblem(){
        backtrack(0, 0, new ArrayList<>());
        for(List<Integer> a: ret){
            for(Integer c: a){
                System.out.print(c+" ");
            }
            System.out.println();
        }
    }
    // 可以进行优化，可以进行剪枝操作，但是不会，后续会了加上
    public void backtrack(int count, int weightOfBag, List<Integer> ans){
        /*
         * @count: 表示当前是第几个物品
         * @weightOfBat: 表示当前装入的重量
         * */
        if(weightOfBag == 10){
            ret.add(new ArrayList<>(ans));
        }
        if(count == weight.length){
            return;
        }
        // for()表示所有可能的选择
        // i=0:表示不放入背包， i=1:表示放入背包
        for(int i=0; i<2; i++){
            if(i == 0){
                backtrack(count + 1, weightOfBag, ans);
            }
            if(i == 1) {
                if(weightOfBag + weight[count] <= 10){
                    ans.add(weight[count]);
                    backtrack(count+1, weightOfBag + weight[count], ans);
                    ans.remove((Integer)weight[count]);
                }
            }
        }
    }
}

```