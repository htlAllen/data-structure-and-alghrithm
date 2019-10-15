# 算法之删除排序链表中的重复元素
## 描述
给定一个排序链表，删除所有重复的元素，使得每个元素只出现一次。

实例
>输入: 1->1->2
>
>输出: 1->2
>
>输入: 1->1->2->3->3
>
>输出: 1->2->3
## 分析
1. 链表是有序的

遍历查找，找到不相同的节点就指向它

> 关键： 最后一个节点需要额外考虑
## 思路
## 代码
```java
class Solution {
    public ListNode deleteDuplicates(ListNode head) {
        // 特殊情况考虑
        if (head == null) return null;
        if (head.next == null) return head;
        // 节点初始化
        ListNode dummy = new ListNode(-1);
        dummy.next = head;
        ListNode fast = head.next;
        ListNode slow = head;
        // 最后一个
        while(fast.next != null){
            if(fast.val != slow.val){
                slow.next = fast;
                slow = slow.next;
            }
            fast = fast.next;
        }
        slow.next = slow.val == fast.val? null: fast;
        return dummy.next;
    }
}
```