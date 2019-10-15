# 算法之奇偶链表
## 描述
给定一个单链表，把所有的奇数节点和偶数节点分别排在一起。请注意，这里的奇数节点和偶数节点指的是节点编号的奇偶性，而不是节点的值的奇偶性。

示例
>输入: 1->2->3->4->5->NULL
>
> 输出: 1->3->5->2->4->NULL

>输入: 2->1->3->5->6->4->7->NULL
>
>输出: 2->3->6->7->1->5->4->NULL

## 分析
1. 奇数改变的方式odd = odd.next.next;
2. 因为要不改变相对位置所以首先要找到链表中奇数节点的最后一个节点even
3. odd先指向第一个偶数节点,再更新奇数节点odd = odd.next.next;
4. 分析循环终止条件
## 思路
## 代码
```
class Solution {
    public ListNode oddEvenList(ListNode head) {
        if(head == null) return null;
        ListNode dummy = new ListNode(-1);
        dummy.next = head;    
        ListNode odd = head;
        int count = 0;
        boolean isOdd;
        // 寻找链表中奇数节点的最后一个
        while(odd.next!=null && odd.next.next!=null){
            odd = odd.next.next;
            count++;
        }
        isOdd = odd.next == null ? true : false;

        ListNode even = odd;
        odd = dummy.next;
        for(int i=0; i<n; i++){
            even.next =  odd.next;
            odd.next = odd.next.next;
            even = even.next;
            odd = odd.next;  
        }
        even.next = isOdd? null: odd.next;
        return dummy.next; 
    }
}
```