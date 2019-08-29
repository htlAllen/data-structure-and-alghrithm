# 算法之回溯法
笼统的讲，回溯算法很多时候都应用**在“搜索”类型的问题上，即从所有解中找到满足条件的解**

回溯算法的处理思想就按照某种方法，不重复和不遗漏的枚举所有情况。 

回溯算法解决问题的主要步骤如下：
1. 将一个问题划分为多个阶段（阶段为递归的深度，如八皇后问题，阶段最长为8）
2. 每个阶段都会做出不同的选择（选择为for循环，如第i阶段（第i个皇后）可以选择1-8个位置）
3. 当发现当前阶段所做出的选择都不对时，就会返回到上一阶段重新做出选择
> 注意： 数组的值中的值不会随着回溯而回到上一个阶段时的值，而基本类型，如整型则会


## 代码模板
```java
class Backtrack{
    public void solveProblem(){
        backtrack(初始阶段);
    }
    public void backtrack(当前阶段){
        if(最后一个阶段){
            // do something
            return;
        }
        for(所有的选择){
            // do something
            backtrack(当前阶段 + 1);  // 进入下一阶段
        }
    }
}

```

## 实例
- [0-1背包](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-16.md)
- [数独](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-14.md)
- [八皇后](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-13.md)
- [全排列](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-11.md)
- [电话号码的数字组合](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-10.md)