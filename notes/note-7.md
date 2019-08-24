# 数据结构之堆
## 概念
#### 定义： 只需要满足以下两个条件就是堆
1. 堆是一颗完全二叉树
2. 堆中每一个节点的父节点都比其左右孩子节点的值要大（小）(关键)

#### 实现： 一般用数组（不用链表）做为存储单位 + 从上往下堆化 + 从下往上堆化

#### 应用场景：排序， xx，xx等
## 实现
#### 从上往下堆化
```java
class Heap{
    public void heapify(int heap[], int parent, int count){
        /**
         * @parent: 父节点所在的索引
         * @count: 当前堆中元素的个数
         */
        int childIndex = parent * 2 + 1; // 指向左右孩子最小的那个值的索引,目前指向左孩子
        while(childIndex < count){
            // 若右孩子存在并且右孩子值比左孩子小，则childIndex指向右孩子
            if(childIndex + 1 < count && heap[childIndex+1] < heap[childIndex]){
                childIndex++;
            }
            if(heap[childIndex] < heap[parent]){
                // 交换元素
                int tmp = heap[parent];
                heap[parent] = heap[childIndex];
                heap[childIndex] = tmp;
                // 索引变换
                parent = childIndex;
                childIndex = parent * 2 + 1;
            }else{
                break;
            }
        }
    }
}
```
#### 从下往上堆化
```java
class Heap{
    public void insert(int value){
        if(count == capacity) {
            System.out.println("满了");
        }
        count++;
        // 确定孩子节点的下标和父节点的小标
        int childIndex = count - 1;
        int parent = count / 2;
        if(childIndex%2 == 0 && parent != 0){
            parent--;
        }
        while(parent>=0 && array[parent] > value){
            array[childIndex] = array[parent];
            childIndex = parent;
            if(childIndex == 0){
                break;
            }
            parent = childIndex/2;
            if(childIndex%2 == 0 && parent != 0){
                parent--;
            }
        }
        array[childIndex] = value;
    }
}
```