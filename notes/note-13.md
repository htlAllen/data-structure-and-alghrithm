# 算法之八皇后
## 描述
## 分析
Q1： 用什么样的数组结构来存储当前棋盘上皇后的位置？以及在此数据结构上，如何判断皇后是否能放在某一个位置上面？

A1：简单的办法，就一个二维数据来进行存储。判断的话就依此遍历行列和对角线
> 存储空间O(n x n)        ,    时间复杂度O(3xn)

A2: 用一个一维数组来存储当前棋盘上皇后的位置，即数组的下标为皇后所在的行，数组的值为皇后所在的列 
## 思路
## 代码
```java

public class Queens {
    // 用一维数组就可以存储八个皇后
    int[] result = new int[8];
    int count = 0;
    public void solveNQueens(int n){
        backtrack(0);
        System.out.println(count);
    }
    public boolean isPlace(int[] result, int row, int col){
        int leftup = col - 1, rightup = col + 1;
        int i = row - 1;
        while(i >= 0){
            // 列
            if(result[i] == col){
                return false;
            }
            // 左上角
            if(leftup>=0 && result[i] == leftup)
            {
                return false;
            }
            // 右下角
            if(leftup<8 && result[i] == rightup){
                return false;
            }
            leftup--;
            rightup++;
            i--;
        }
        return true;
    }
    public void backtrack(int row){
        if(row == 8){
            count++;
            return;
        }
        for(int i=0; i<8; i++){
            if(isPlace(result, row, i)){
                result[row] = i;
                backtrack(row+1);
            }
        }
    }
    public static void main(String[] args){
        Queens x = new Queens();
        x.solveNQueens(8);
    }
}
```