public class Solution07 {
    // 208. 实现 Trie (前缀树)
    class Trie {
        Trie[] data;
        boolean isEnd;

        /** Initialize your data structure here. */
        public Trie() {
            data = new Trie[26];
            isEnd = false;
        }

        /** Inserts a word into the trie. */
        public void insert(String word) {
            if(word == null || word.length() == 0) return;
            Trie cur = this;
            for(int i = 0; i < word.length(); i++){
                int index = word.charAt(i) - 'a';
                if(cur.data[index] == null) cur.data[index] = new Trie();
                cur = cur.data[index];
            }
            cur.isEnd = true;
        }

        private Trie findPrefix(String prefix){
            if(prefix == null || prefix.length() == 0) return null;
            Trie cur = this;
            for(int i = 0; i < prefix.length(); i++){
                if(cur.data[prefix.charAt(i) - 'a'] == null) return null;
                cur = cur.data[prefix.charAt(i) - 'a'];
            }
            return cur;
        }

        /** Returns if the word is in the trie. */
        public boolean search(String word) {
            Trie node = findPrefix(word);
            return node != null && node.isEnd;
        }

        /** Returns if there is any word in the trie that starts with the given prefix. */
        public boolean startsWith(String prefix) {
            Trie node = findPrefix(prefix);
            return node != null;
        }
    }

    /**
    * Your Trie object will be instantiated and called as such:
    * Trie obj = new Trie();
    * obj.insert(word);
    * boolean param_2 = obj.search(word);
    * boolean param_3 = obj.startsWith(prefix);
    */

    // 547. 朋友圈
    private int count = 0;
    private int[] parent;
    public int findCircleNum(int[][] M) {
        if(M == null || M.length == 0){
            return 0;
        }
        init(M.length);
        for(int i = 0; i < M.length; i++){
            for(int j = i + 1; j < M[i].length; j++){
                if(M[i][j] == 1){
                    union(i, j);
                }
            }
        }
        return count;
    }

    private void init(int count){
        this.count = count;
        parent = new int[count];
        for(int i = 0; i < count; i++){
            parent[i] = i;
        }
    }

    private int find(int parentIndex){
        while(parent[parentIndex] != parentIndex){
            parent[parentIndex] = parent[parent[parentIndex]];
            parentIndex = parent[parentIndex];
        }
        return parentIndex;
    }

    private void union(int p, int q){
        p = find(p);
        q = find(q);
        if(p == q) return;
        parent[p] = parent[q];
        count--;
    }
}
