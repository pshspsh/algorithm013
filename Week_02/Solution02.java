import java.util.*;

public class Solution02 {
    // 剑指 Offer 49. 丑数
    public int nthUglyNumber(int n) {
        if(n <= 1){
            return n;
        }

        int[] dp = new int[n];
        dp[0] = 1;
        int twoMulti = 0, threeMulti = 0, fiveMulti = 0;
        for(int i = 1; i < n; i++){
            int twoResult = dp[twoMulti] * 2, threeResult = dp[threeMulti] * 3, fiveResult = dp[fiveMulti] * 5;
            dp[i] = Math.min(Math.min(twoResult, threeResult), fiveResult);
            if(twoResult == dp[i]){
                twoMulti++;
            }
            if(threeResult == dp[i]){
                threeMulti++;
            }
            if(fiveResult == dp[i]){
                fiveMulti++;
            }
        }
        return dp[n - 1];
    }

    // 347. 前 K 个高频元素
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer, Integer> count = new HashMap<>();
        for (int n: nums) {
            count.put(n, count.getOrDefault(n, 0) + 1);
        }

        PriorityQueue<Integer> heap = new PriorityQueue<Integer>((n1, n2) -> count.get(n1) - count.get(n2));

        for (int n: count.keySet()) {
            heap.add(n);
            if (heap.size() > k)
                heap.poll();
        }

        List<Integer> top_k = new ArrayList<>();
        while (!heap.isEmpty())
            top_k.add(heap.poll());
        return top_k.stream().mapToInt(Integer::valueOf).toArray();
    }

    // 49. 字母异位词分组
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs.length == 0) return new ArrayList<>();
        Map<String, List<String>> ans = new HashMap<>();
        for (String s : strs) {
            char[] ca = s.toCharArray();
            // 对char[]排序
            Arrays.sort(ca);
            String key = String.valueOf(ca);
            if (!ans.containsKey(key)) ans.put(key, new ArrayList<>());
            ans.get(key).add(s);
        }
        return new ArrayList<>(ans.values());
    }

    public class TreeNode {
         int val;
         TreeNode left;
         TreeNode right;
         TreeNode(int x) { val = x; }
    }

    class ColorNode {
        TreeNode node;
        String color;

        ColorNode(TreeNode node, String color){
            this.node = node;
            this.color = color;
        }
    }

    // 94. 二叉树的中序遍历
    public List<Integer> inorderTraversal(TreeNode root) {
        if(root == null) return new ArrayList<Integer>();

        List<Integer> res = new ArrayList<>();
        Stack<ColorNode> stack = new Stack<>();
        stack.push(new ColorNode(root,"WHITE"));

        while(!stack.empty()){
            ColorNode cn = stack.pop();

            if(cn.color.equals("WHITE")){
                if(cn.node.right != null) stack.push(new ColorNode(cn.node.right,"WHITE"));
                stack.push(new ColorNode(cn.node,"GREY"));
                if(cn.node.left != null)stack.push(new ColorNode(cn.node.left,"WHITE"));
            }else{
                res.add(cn.node.val);
            }
        }

        return res;
    }

    // 144. 二叉树的前序遍历
    public List<Integer> preorderTraversal(TreeNode root) {
        if(root == null) return new ArrayList<Integer>();

        List<Integer> res = new ArrayList<>();
        Stack<ColorNode> stack = new Stack<>();
        stack.push(new ColorNode(root,"WHITE"));

        while(!stack.empty()){
            ColorNode cn = stack.pop();

            if(cn.color.equals("WHITE")){
                if(cn.node.right != null) stack.push(new ColorNode(cn.node.right,"WHITE"));
                if(cn.node.left != null)stack.push(new ColorNode(cn.node.left,"WHITE"));
                stack.push(new ColorNode(cn.node,"GREY"));
            }else{
                res.add(cn.node.val);
            }
        }

        return res;
    }

    class Node {
        public int val;
        public List<Node> children;

        public Node() {}

        public Node(int _val) {
            val = _val;
        }

        public Node(int _val, List<Node> _children) {
            val = _val;
            children = _children;
        }
    };

    // 429. N叉树的层序遍历
    private List<List<Integer>> result = new ArrayList<>();

    public List<List<Integer>> levelOrder(Node root) {
        if (root != null) traverseNode(root, 0);
        return result;
    }

    private void traverseNode(Node node, int level) {
        if (result.size() <= level) {
            result.add(new ArrayList<>());
        }
        // level 节点层级
        result.get(level).add(node.val);
        for (Node child : node.children) {
            traverseNode(child, level + 1);
        }
    }
}
