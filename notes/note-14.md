# 算法之求解数独
## 描述
## 分析

## 思路

## 代码
```java
public class Sudoku {
    public static void main(String[] args){
        char[][] board = new char[][]{
            {'5', '3', '.', '.', '7', '.', '.', '.', '.'},
            {'6', '.', '.', '1', '9', '5', '.', '.', '.'},
            {'.', '9', '8', '.', '.', '.', '.', '6', '.'},
            {'8', '.', '.', '.', '6', '.', '.', '.', '3'},
            {'4', '.', '.', '8', '.', '3', '.', '.', '1'},
            {'7', '.', '.', '.', '2', '.', '.', '.', '6'},
            {'.', '6', '.', '.', '.', '.', '2', '8', '.'},
            {'.', '.', '.', '4', '1', '9', '.', '.', '5'},
            {'.', '.', '.', '.', '8', '.', '.', '7', '9'}
        };
    }
}

class Error {
    public void Error(char[][] board) {
        backtrack(board, 0, 0);
    }
    // 很明显这三个判断条件可以写到一个函数里面，这样写太冗余了
    public boolean isContainInrow(char[][] board, int row, char value){
        for(int i=0; i<9; i++){
            if(board[row][i] == value){
                return true;
            }
        }
        return false;
    }
    public boolean isContainInColumn(char[][] board, int column, char value){
        for(int i=0; i<9; i++){
            if(board[i][column] == value){
                return true;
            }
        }
        return false;
    }
    public boolean isContainInTriangle(char[][] board, int row, int column, char value){
        if(row>=0 && row<=2){
            if(column>=0 && column<=2){
                for(int i=0; i<=2; i++){
                    for(int j=0; j<=2; j++){
                        if(board[i][j] == value){
                            return true;
                        }
                    }
                }
                return false;
            }else if(column>=3 && column<=5){
                for(int i=0; i<=2; i++){
                    for(int j=3; j<=5; j++){
                        if(board[i][j] == value){
                            return true;
                        }
                    }
                }
                return false;
            }else{
                for(int i=0; i<=2; i++){
                    for(int j=6; j<=8; j++){
                        if(board[i][j] == value){
                            return true;
                        }
                    }
                }
                return false;
            }
        }else if(row>=3 && row<=5){
            if(column>=0 && column<=2){
                for(int i=3; i<=5; i++){
                    for(int j=0; j<=2; j++){
                        if(board[i][j] == value){
                            return true;
                        }
                    }
                }
                return false;
            }else if(column>=3 && column<=5){
                for(int i=3; i<=5; i++){
                    for(int j=3; j<=5; j++){
                        if(board[i][j] == value){
                            return true;
                        }
                    }
                }
                return false;
            }else{
                for(int i=3; i<=5; i++){
                    for(int j=6; j<=8; j++){
                        if(board[i][j] == value){
                            return true;
                        }
                    }
                }
                return false;
            }
        }else{
            if(column>=0 && column<=2){
                for(int i=6; i<=8; i++){
                    for(int j=0; j<=2; j++){
                        if(board[i][j] == value){
                            return true;
                        }
                    }
                }
                return false;
            }else if(column>=3 && column<=5){
                for(int i=6; i<=8; i++){
                    for(int j=3; j<=5; j++){
                        if(board[i][j] == value){
                            return true;
                        }
                    }
                }
                return false;
            }else{
                for(int i=6; i<=8; i++){
                    for(int j=6; j<=8; j++){
                        if(board[i][j] == value){
                            return true;
                        }
                    }
                }
                return false;
            }
        }

    }
    // 这个回溯函数问题很大
    // 1. 终止条件错误if((m==8) && (n==8)),因为m=8和n=8有值就会出现问题
    // 2. 这个回溯是错误的，看看这个时间复杂读，（需要填写的数字）的9*9*9次方
    //    for(){
    //        for(){
    //            for(){
    //                backtrack();
    //            }
    //        }
    //    }
    public void backtrack(char[][] board, int m, int n){
        if((m==8) && (n==8)){
            for(int j=0; j<9; j++){
                for(int k=0; k<9; k++){
                    System.out.print(board[j][k]);
                }
                System.out.println();
            }
            return;
        }
        // 52的9*9*9次方
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j] == '.'){
                    for(char val='1'; val<='9'; val++){
                        if(!(isContainInrow(board, i, (char)val) || isContainInColumn(board, j, (char)val) || isContainInTriangle(board, i, j, val))){
                            board[i][j] = (char)val;
                            backtrack(board, i, j);
                            if(i==8 && j==8){
                                continue;
                            }
                            board[i][j] = '.';
                        }
                    }
                }
            }
        }

    }
}

class Right{
    char board[][];
    public Right(char[][] board){
        // this.board = new char[][](board); 如何在新建一个board，其值以参数作为初始化？
        this.board = board;
    }
    public int getLength(){
        // 获得数独中有多少数，还需要填写
        int count = 0;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j] == '.'){
                    count++;
                }
            }
        }
        return count;
    }
    public boolean isOk(int row, int column, char value) {
        // 判断行列的情况
        for (int i = 0; i < 9; i++) {
            if (board[row][i] == value) {
                return false;
            }
            if (board[i][column] == value) {
                return false;
            }

        }
        // 判断三宫格的情况
        if (row >= 0 && row <= 2) {
            if (column >= 0 && column <= 2) {
                for (int m = 0; m <= 2; m++) {
                    for (int n = 0; n <= 2; n++) {
                        if (board[m][n] == value) {
                            return false;
                        }
                    }
                }
            }
            if (column >= 3 && column <= 5) {
                for (int m = 0; m <= 2; m++) {
                    for (int n = 3; n <= 5; n++) {
                        if (board[m][n] == value) {
                            return false;
                        }
                    }
                }
            }
            if (column >= 6 && column <= 8) {
                for (int m = 0; m <= 2; m++) {
                    for (int n = 6; n <= 8; n++) {
                        if (board[m][n] == value) {
                            return false;
                        }
                    }
                }
            }
        }
        if (row >= 3 && row <= 5) {
            if (column >= 0 && column <= 2) {
                for (int m = 3; m <= 5; m++) {
                    for (int n = 0; n <= 2; n++) {
                        if (board[m][n] == value) {
                            return false;
                        }
                    }
                }
            }
            if (column >= 3 && column <= 5) {
                for (int m = 3; m <= 5; m++) {
                    for (int n = 3; n <= 5; n++) {
                        if (board[m][n] == value) {
                            return false;
                        }
                    }
                }
            }
            if (column >= 6 && column <= 8) {
                for (int m = 3; m <= 5; m++) {
                    for (int n = 6; n <= 8; n++) {
                        if (board[m][n] == value) {
                            return false;
                        }
                    }
                }
            }
        }
        if (row >= 6 && row <= 8) {
            if (column >= 0 && column <= 2) {
                for (int m = 6; m <= 8; m++) {
                    for (int n = 0; n <= 2; n++) {
                        if (board[m][n] == value) {
                            return false;
                        }
                    }
                }
            }
            if (column >= 3 && column <= 5) {
                for (int m = 6; m <= 8; m++) {
                    for (int n = 3; n <= 5; n++) {
                        if (board[m][n] == value) {
                            return false;
                        }
                    }
                }
            }
            if (column >= 6 && column <= 8) {
                for (int m = 6; m <= 8; m++) {
                    for (int n = 6; n <= 8; n++) {
                        if (board[m][n] == value) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;

    }
    public void solveSuduko(){
        int needFill = getLength();
        int current = 0;
        backtrack(current, needFill);
    }
    public void backtrack(int current, int allNeed){
        // 递归的终止条件
        // 这个可以优化，因为结果唯一，如何此时找到了答案就不用再递归了，就可以终止整个循环了。
        if(current == allNeed){
            for(int i=0; i<9; i++){
                for(int j=0; j<9; j++){
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
            return;
        }
        // 寻找未填写的行和列
        int row=8, col=8;
        boolean flag = false;
        for(int i=0; i<9; i++){
            for(int j=0; j<9; j++){
                if(board[i][j] == '.'){
                    row = i;
                    col = j;
                    flag = true;
                    break;
                }
            }
            if(flag){
                break;
            }
        }
        // 对上一步找到的行和列依次放入1-9
        for(char i='1'; i<='9'; i++){
            if(isOk(row, col, i)){
                board[row][col] = i;
                backtrack(current + 1, allNeed);
                board[row][col] = '.';
            }
        }

    }

}
```