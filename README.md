# 数据结构
- [队列](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-1.md)
- [堆](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-7.md)
# 算法
- [两数之和](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-2.md)
- [三数之和](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-3.md)
- [二分查找](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-5.md)
- [插入排序](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-8.md)
- [回溯法](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-15.md)
- [0-1背包](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-16.md)
- [数独](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-14.md)
- [八皇后](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-13.md)
- [全排列](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-11.md)
- [电话号码的数字组合](https://github.com/htlAllen/data-structure-and-alghrithm/blob/master/notes/note-10.md)

题目 | 描述 | 标签 | 其他
------------ | ------------- | ------------- | -------------
[搜索插入位置](./notes/note-17.md) | 给定一个有序数组和一个目标值，返回目标值在有序数组中的下标，若数组中不存在该目标值，则返回插入的位置 | 二分法:low，high,low<=high，mid | 解决
[在排序数组中查找元素的第一个和最后一个位置](./notes/note-18.md) | 给定一个有序数组（有重复）和一个目标值，返回该目标值在数组中第一次和最后一次出现的位置  | 二分法升级版，即还需要多考虑一步当前元素是否是第一个或最后一个,细节见代码 | 解决
[移除元素](./notes/note-19.md) | 给一个数组和目标值，原地删除目标值 | 遍历数组，选择满足条件的元素保存到数组中对应的位置上去 | 解决（拷贝法） *
[删除排序数组中的重复项]((./notes/note-20.md)) | 给定一个数组，原地删除重复项 | 遍历数组，选择满足条件的元素保存到数组中对应的位置上去 | 解决
[移动零](./notes/note-21.md) | 给定一个数组，将其中的零放到数组的末尾去，但不该元数组中元素的相对位置 | 遍历数组，选择满足条件的元素保存到数组中对应的位置上去 | 解决
[合并两个有序数组](./notes/note-21.md) | 给定两个有序数组，然后将其进行合并 | 遍历两个数组，选择满足条件的元素保存到数组中对应的位置上去（细节） | 解决
[寻找两个有序数组的中位数](./notes/note-21.md) | 找到第k大的数 | 找到前k大个元素分别分布在两个有序数组的哪些位置 | 未解决
[接雨水](./notes/note-21.md) |  | 找到当前位置左边所有元素的最大值和右边所有元素的最大值 |未解决
[缺失的第一个正整数](./notes/note-21.md) |  | 1.缺失的正整数的范围是明确的；2.可以利用数组的下标做map的key | 解决