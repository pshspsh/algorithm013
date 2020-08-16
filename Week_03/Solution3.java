import java.util.*;

public class Solution3 {
    // 47. 全排列 II
    public List<List<Integer>> permuteUnique(int[] nums) {
        int len = nums.length;
        List<List<Integer>> res = new ArrayList<>();
        if (len == 0) {
            return res;
        }

        // 排序（升序或者降序都可以），排序是剪枝的前提
        Arrays.sort(nums);

        boolean[] used = new boolean[len];
        Deque<Integer> path = new ArrayDeque<>(len);
        dfs(nums, len, 0, used, path, res);
        return res;
    }

    private void dfs(int[] nums, int len, int depth, boolean[] used, Deque<Integer> path, List<List<Integer>> res) {
        if (depth == len) {
            res.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i < len; ++i) {
            if (used[i]) {
                continue;
            }

            // 剪枝条件：i > 0 是为了保证 nums[i - 1] 有意义
            // 写 !used[i - 1] 剪去相同元素的重复分支
            // if((i > 0 && nums[i] == nums[i - 1]) != (i > 0 && nums[i] == nums[i - 1] && !used[i - 1])
            // || (i > 0 && nums[i] == nums[i - 1]) != (i > 0 && nums[i] == nums[i - 1] && used[i - 1])){
            //     System.out.println("!used[i - 1]: " + (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]));
            //     System.out.println("used[i - 1]: " + (i > 0 && nums[i] == nums[i - 1] && used[i - 1]));
            //     System.out.println("nothing: " + (i > 0 && nums[i] == nums[i - 1]) + "\n");
            // }
            if (i > 0 && nums[i] == nums[i - 1] && !used[i - 1]) {
                continue;
            }

            path.addLast(nums[i]);
            used[i] = true;

            dfs(nums, len, depth + 1, used, path, res);
            // 回溯部分的代码，和 dfs 之前的代码是对称的
            used[i] = false;
            path.removeLast();
        }
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode(int x) { val = x; }
    }

    // 236. 二叉树的最近公共祖先
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // terminator
        if(root == null || root == p || root == q){
            return root;
        }

        // process
        TreeNode left = lowestCommonAncestor(root.left, p, q);
        TreeNode right = lowestCommonAncestor(root.right, p, q);

        if(left == null) return right;
        if(right == null) return left;
        return root;
    }

    // 105. 从前序与中序遍历序列构造二叉树
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        if(preorder.length == 0){
            return null;
        }
        Map<Integer, Integer> map = new HashMap<Integer, Integer>();
        for(int i = 0; i < inorder.length; i++){
            map.put(inorder[i], i);
        }
        // 递归构造
        return generateTree(0, 0, inorder.length - 1, map, preorder);
    }

    private TreeNode generateTree(int preOrderRootIndex, int leftInOrderIndex, int rightInOrderIndex, Map<Integer, Integer> map, int[] preorder){
        // terminator
        if(leftInOrderIndex > rightInOrderIndex){
            return null;
        }

        int rootInOrderIndex = map.get(preorder[preOrderRootIndex]);
        TreeNode root = new TreeNode(preorder[preOrderRootIndex]);

        // drill down
        TreeNode left = generateTree(preOrderRootIndex + 1, leftInOrderIndex, rootInOrderIndex - 1, map, preorder);
        TreeNode right = generateTree(preOrderRootIndex + (rootInOrderIndex - leftInOrderIndex) + 1, rootInOrderIndex + 1, rightInOrderIndex, map, preorder);

        root.left = left;
        root.right = right;

        return root;
    }

    // 77. 组合
    private List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> combine(int n, int k) {
        if (n <= 0 || k <= 0 || n < k) {
            return result;
        }
        findCombinations(n, k, 1, new Stack<>());
        return result;
    }

    private void findCombinations(int n, int k, int index, Stack<Integer> p) {
        if (p.size() == k) {
            result.add(new ArrayList<>(p));
            return;
        }
        for (int i = index; i <= n - (k - p.size()) + 1; i++) {
            p.push(i);
            findCombinations(n, k, i + 1, p);
            p.pop();
        }
    }

    // 46. 全排列
    List<List<Integer>> res = new LinkedList<>();

    /* 主函数，输入一组不重复的数字，返回它们的全排列 */
    List<List<Integer>> permute(int[] nums) {
        // 记录「路径」
        LinkedList<Integer> track = new LinkedList<>();
        backtrack(nums, track);
        return res;
    }


    void backtrack(int[] nums, LinkedList<Integer> track) {
        // 触发结束条件
        if (track.size() == nums.length) {
            res.add(new LinkedList(track));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            // 排除不合法的选择
            if (track.contains(nums[i]))
                continue;
            // 做选择
            track.add(nums[i]);
            // 进入下一层决策树
            backtrack(nums, track);
            // 取消选择
            track.removeLast();
        }
    }
}
