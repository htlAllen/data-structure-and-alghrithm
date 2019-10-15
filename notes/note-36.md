# 算法之删除排序链表中的重复元素 II
## 描述
给定一个排序链表，删除所有含有重复数字的节点，只保留原始链表中 没有重复出现 的数字。

实例
>输入: 1->2->3->3->4->4->5
>
>输出: 1->2->5
>
>输入: 1->1->1->2->3
>
>输出: 2->3
## 分析
关键就是找个一个指针，使得这个指针所指向的元素是不重复的元素，需要满足两个条件：
1. cur.val != cur.next.val,即当前节点值和当前节点下一节点的值不同
2. 当前节点的值和之前节点的值不重复
3. 特殊情况， 1->1->1->1
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