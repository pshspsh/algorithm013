import java.util.HashMap;
import java.util.Map;

public class Solution {

    // 26. Remove Duplicates from Sorted Array
    public int removeDuplicates(int[] nums) {
        int activeIndex = 0;
        for(int i = 0; i < nums.length; i++){
            nums[activeIndex++] = nums[i];
            while(i < nums.length && nums[activeIndex - 1] == nums[i]){
                i++;
            }
            i--;
        }
        return activeIndex;
    }

    /**
      Definition for singly-linked list.
     */
    public class ListNode {
        int val;
        ListNode next;
        ListNode() {}
        ListNode(int val) { this.val = val; }
        ListNode(int val, ListNode next) { this.val = val; this.next = next; }
    }

    // 21. Merge Two Sorted Lists
    public ListNode mergeTwoLists(ListNode l1, ListNode l2) {
        // 哨兵，简化初始化过程
        ListNode sentinel = new ListNode(-1);

        ListNode prev = sentinel;
        // 迭代
        while (l1 != null && l2 != null) {
            if (l1.val <= l2.val) {
                prev.next = l1;
                l1 = l1.next;
            } else {
                prev.next = l2;
                l2 = l2.next;
            }
            prev = prev.next;
        }

        // 合并后 l1 和 l2 最多只有一个还未被合并完，我们直接将链表末尾指向未合并完的链表即可
        prev.next = l1 == null ? l2 : l1;

        return sentinel.next;
    }

    // 88. Merge Sorted Array
    public void merge(int[] nums1, int m, int[] nums2, int n) {
        int i;
        for(i = nums1.length - 1; i >= 0 && m > 0 && n > 0; i--){
            nums1[i] = nums1[m - 1]>nums2[n - 1]?nums1[--m]:nums2[--n];
        }
        while(n>0){
            nums1[i--] = nums2[--n];
        }
    }

    // 1. Two Sum
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> valueMapIndex = new HashMap<>();
        int[] result = new int[2];
        for(int i = 0; i < nums.length; i++){
            if(valueMapIndex.containsKey(target - nums[i])){
                int temp = valueMapIndex.get(target - nums[i]);
                result[0] = Math.min(i, temp);
                result[1] = Math.max(i, temp);
                return result;
            }
            valueMapIndex.put(nums[i], i);
        }
        return result;
    }

    // 283. move Zeros
    public void moveZeroes(int[] nums) {
        // int zeroIndex = 0;
        // for(int i = 0; i < nums.length; i ++){
        //     if(nums[i] != 0){
        //         nums[zeroIndex] = nums[i];
        //         if(i != zeroIndex){
        //             nums[i] = 0;
        //         }
        //         zeroIndex++;
        //     }
        // }

        // move zero to front
        int zeroIndex = nums.length - 1;
        for(int i = nums.length - 1; i >= 0; i--){
            if(nums[i] != 0){
                nums[zeroIndex] = nums[i];
                if(i != zeroIndex){
                    nums[i] = 0;
                }
                zeroIndex--;
            }
        }
    }

    // 66. Plus One
    public int[] plusOne(int[] digits) {
        for(int i = digits.length - 1; i >= 0; i--){
            digits[i] = (digits[i] + 1) % 10;
            if(digits[i] != 0) return digits;
        }
        digits = new int[digits.length + 1];
        digits[0] = 1;
        return digits;
    }

    // 42. Trapping Rain Water
    public int trap(int[] height) {
        if(height.length==0){
            return 0;
        }
        int n = height.length;
        int leftMax = height[0], rightMax = height[n-1];
        int left = 0, right = n -1;
        int ans = 0;
        while(left <= right){
            leftMax = Math.max(leftMax, height[left]);
            rightMax = Math.max(rightMax, height[right]);
            if(leftMax < rightMax){
                ans += leftMax - height[left++];
            } else {
                ans += rightMax - height[right--];
            }
        }
        return ans;
    }
}

