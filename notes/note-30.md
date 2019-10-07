# 算法之环形链表
## 描述
给定一个链表，判断链表中是否有环。
## 分析
快慢指针
## 思路
快慢指针
## 代码
```java
public class Solution {
    public boolean hasCycle(ListNode head) {
        // 还可以考虑一个节点成环的情况
        if(head == null){
            return false;
        }
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode fast = dummy;
        ListNode slow = dummy;
        // 初始化一下
        fast = fast.next.next;
        slow = slow.next;
        // 正式开始
        while(fast != null && slow != fast){
            slow = slow.next;
            fast = fast.next;
            if(fast == null) break;
            fast = fast.next;
        }
        // return slow == fast? true:false;
        if(slow == fast){
            return true;
        }else{
            return false;
        }
    }
}
```
```java
public class Solution{
    public boolean hasCycle(ListNode head) {
        if (head == null || head.next == null) {
            return false;
        }
        ListNode slow = head;
        ListNode fast = head.next;
        while (slow != fast) {
            if (fast == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
        }
        return true;
    }
}
```
## 总结
1. 快指针走两步的时候可能出现NullPointerException异常，所以判断时要同时判断两个fast和fast.next才能避免NullPointerException异常