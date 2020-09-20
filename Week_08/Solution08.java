import java.util.HashMap;
import java.util.Map;

public class Solution08 {

    // 146. LRU缓存机制
    class LRUCache {

        // 通过包装key、value成节点放入双向链表中，并将节点放到M散列表直接存取
        // 双向链表
        class DLinkedNode {
            int key;
            int value;
            DLinkedNode prev;
            DLinkedNode next;
            public DLinkedNode() {}
            public DLinkedNode(int _key, int _value) {key = _key; value = _value;}
        }

        // hashMap
        private Map<Integer, DLinkedNode> cache = new HashMap<Integer, DLinkedNode>();
        // actual nodes size
        private int size;
        // contain capacity
        private int capacity;
        // 头节点 尾节点
        private DLinkedNode head, tail;

        // 初始化
        public LRUCache(int capacity) {
            this.size = 0;
            this.capacity = capacity;
            // 使用伪头部和伪尾部节点
            head = new DLinkedNode();
            tail = new DLinkedNode();
            head.next = tail;
            tail.prev = head;
        }

        public int get(int key) {
            DLinkedNode node = cache.get(key);
            if (node == null) {
                return -1;
            }
            // 如果 key 存在，先通过哈希表定位，再移到头部
            moveToHead(node);
            return node.value;
        }

        public void put(int key, int value) {
            DLinkedNode node = cache.get(key);
            if (node == null) {
                // 如果 key 不存在，创建一个新的节点
                DLinkedNode newNode = new DLinkedNode(key, value);
                // 添加进哈希表
                cache.put(key, newNode);
                // 添加至双向链表的头部
                addToHead(newNode);
                ++size;
                if (size > capacity) {
                    // 如果超出容量，删除双向链表的尾部节点
                    DLinkedNode tail = removeTail();
                    // 删除哈希表中对应的项
                    cache.remove(tail.key);
                    --size;
                }
            }
            else {
                // 如果 key 存在，先通过哈希表定位，再修改 value，并移到头部
                node.value = value;
                moveToHead(node);
            }
        }

        private void addToHead(DLinkedNode node) {
            node.prev = head;
            node.next = head.next;
            head.next.prev = node;
            head.next = node;
        }

        private void removeNode(DLinkedNode node) {
            node.prev.next = node.next;
            node.next.prev = node.prev;
        }

        private void moveToHead(DLinkedNode node) {
            removeNode(node);
            addToHead(node);
        }

        private DLinkedNode removeTail() {
            DLinkedNode res = tail.prev;
            removeNode(res);
            return res;
        }
    }

    // 1244. 力扣排行榜
    class Leaderboard {
        public Leaderboard() {

        }

        private int[][] array = new int[12000][];
        int index = 0;
        public void addScore(int playerId, int score) {
            int[] arrT = new int[2];
            arrT[0] = playerId;
            arrT[1] = score;
            int pot = search(playerId);
            if(pot == -1)
                pot = index;
            insert(arrT,pot);
        }

        private void insert(int[] arrT,int pot) {
            int i = pot;
            if(pot != index){
                arrT[1] = array[pot][1]+ arrT[1];
            }
            if(index != 0){
                for (i = pot; i > 0 && arrT[1] > array[i-1][1]; i--) {
                    array[i] = array[i-1];
                }
            }
            array[i] = arrT;
            if(pot == index){
                index++;
            }
        }

        public int top(int K) {
            int tempInd = K > index?index:K;
            int sum = 0;
            for(int i= 0;i < tempInd;i++){
                sum += array[i][1];
            }

            return sum;
        }

        public void reset(int playerId) {
            int ret = search(playerId);
            int[] orign = array[ret];
            orign[1] = 0;
            for(int i = ret+1;i<index;i++){
                array[i-1] = array[i];
            }
            array[index-1] = orign;
        }
        public int search(int playerId){
            for(int i = 0; i < index;i++){
                if(array[i][0] == playerId) return i;
            }
            return -1;
        }
    }

}
