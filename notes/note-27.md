# 算法之删除链表中倒数第N个节点
## 描述
给定一个链表，删除链表的倒数第 n 个节点，并且返回链表的头结点。

实例
>给定一个链表: 1->2->3->4->5, 和 n = 2. 
>
>当删除了倒数第二个节点后，链表变为 1->2->3->5.
## 分析
快慢指针
## 思路
快慢指针
## 代码
```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode first = head;
        ListNode last = head;
        ListNode tmp;
        int count=0;
        while(count != n){
            last = last.next;
            count++;
        }
        // 删除第一个节点
        if(last == null){
            tmp = head.next;
            head = null;
            return tmp;
        }
        // 删除导师第n个节点
        while(last.next != null){
            first = first.next;
            last = last.next;
        }
        tmp = first.next;
        first.next = first.next.next;
        tmp = null;
        return head;
    }
}
```
```java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1);
        ListNode fast = dummy;
        ListNode slow = dummy;
        dummy.next = head;

        for(int i=0; i<n; i++){
            fast = fast.next;
        }

        while(fast.next != null){
            slow = slow.next;
            fast = fast.next;
        }
        // 将要删除的设置为null
        ListNode tmp = slow.next;
        slow.next = slow.next.next;
        tmp = null;
        return dummy.next;
    }
}
```
## 总结
1. 链表的常见算法--快慢指针
2. 手动设置一个头节点（dummy）将大大简化代码编写的难度
3. 初始化的默认值