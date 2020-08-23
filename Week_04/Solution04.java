import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Solution04 {

    // 860. 柠檬水找零
    public boolean lemonadeChange(int[] bills) {

        if(bills.length == 0){
            return true;
        }

        int five = 0, ten = 0;
        for(int i = 0; i < bills.length; i++){
            if(bills[i] == 5){
                five++;
            } else if(bills[i] == 10){
                five--;
                ten++;
                if(five < 0){
                    return false;
                }
            } else if(bills[i] == 20){
                if(ten > 0 && five > 0){
                    ten--;
                    five--;
                    continue;
                }
                if(five > 3){
                    five = five - 3;
                    continue;
                }
                return false;
            }
        }
        return true;
    }

    // 122. 买卖股票的最佳时机 II
    public int maxProfit(int[] prices) {
        int result = 0;
        for(int i = 1; i < prices.length; i++){
            if(prices[i] - prices[i-1] > 0){
                result += prices[i] - prices[i-1];
            }
        }
        return result;
    }

    // 455. 分发饼干
    public int findContentChildren(int[] g, int[] s) {
        int maxCount = 0;
        int childrenIndex = 0;
        Arrays.sort(g);
        Arrays.sort(s);
        for(int i = 0; i < s.length; i++){
            if(childrenIndex > g.length - 1 || s[i] < g[childrenIndex]){
                continue;
            }
            maxCount++;
            childrenIndex++;
        }
        return maxCount;
    }

    // 874. 模拟行走机器人
    public int robotSim(int[] commands, int[][] obstacles) {
        int[] dx = new int[]{0, 1, 0, -1};
        int[] dy = new int[]{1, 0, -1, 0};
        int x = 0, y = 0, di = 0;

        // 障碍物合二为一， obstacles (x, y) as (x+30000) * (2^16) + (y+30000)
        Set<Long> obstacleSet = new HashSet();
        for (int[] obstacle: obstacles) {
            // 转为正数
            long ox = (long) obstacle[0] + 30000;
            long oy = (long) obstacle[1] + 30000;
            // 左移16位防重叠
            obstacleSet.add((ox << 16) + oy);
        }

        int ans = 0;
        for (int cmd: commands) {
            if (cmd == -2)  //left
                di = (di + 3) % 4;
            else if (cmd == -1)  //right
                di = (di + 1) % 4;
            else {
                for (int k = 0; k < cmd; ++k) {
                    int nx = x + dx[di];
                    int ny = y + dy[di];
                    long code = (((long) nx + 30000) << 16) + ((long) ny + 30000);
                    // 校验是否碰到障碍物
                    if (!obstacleSet.contains(code)) {
                        x = nx;
                        y = ny;
                        ans = Math.max(ans, x*x + y*y);
                    } else {
                        break;
                    }
                }
            }
        }

        return ans;
    }

    // 127. 单词接龙
    public int ladderLength(String beginWord, String endWord, List<String> wordList) {
        Set<String> beginSet = new HashSet<>();
        Set<String> endSet = new HashSet<>();
        beginSet.add(beginWord);
        endSet.add(endWord);
        Set<String> dict = new HashSet<>(wordList);
        if(!dict.contains(endWord)) return 0;
        return search(beginSet, endSet, dict, 1);
    }

    // 双向BFS
    private int search(Set<String> beginSet, Set<String> endSet, Set<String> dict, int cnt){
        if(beginSet.isEmpty() || endSet.isEmpty()) return 0;
        cnt++;
        dict.removeAll(beginSet);
        Set<String> nextSet = new HashSet<>();
        for(String str : beginSet){
            char[] chs = str.toCharArray();
            for(int i = 0; i < chs.length; i++){
                char c = chs[i];
                for(char j = 'a'; j <= 'z'; j++){
                    chs[i] = j;
                    String tmp = new String(chs);
                    if(!dict.contains(tmp)) continue;
                    if(endSet.contains(tmp)) return cnt;
                    nextSet.add(tmp);
                }
                chs[i] = c;
            }
        }
        return nextSet.size() > endSet.size() ? search(endSet, nextSet, dict, cnt) : search(nextSet, endSet, dict, cnt);
    }

    // 200. 岛屿数量
    public int numIslands(char[][] grid) {
        int count = 0;
        for(int i = 0; i < grid.length; i++){
            for(int j = 0; j < grid[i].length; j++){
                if(grid[i][j] == '1'){
                    count++;
                    traversal(i, j, grid.length, grid[i].length, grid);
                }
            }
        }
        return count;
    }

    private void traversal(int row, int line, int rowLength, int lineLength, char[][] grid){
        if(row < 0 || row > rowLength - 1 || line < 0 || line > lineLength - 1){
            return;
        }
        if(grid[row][line] == '0'){
            return;
        }

        grid[row][line] = '0';
        traversal(row + 1, line, rowLength, lineLength, grid);
        traversal(row, line + 1, rowLength, lineLength, grid);
        traversal(row - 1, line, rowLength, lineLength, grid);
        traversal(row, line - 1, rowLength, lineLength, grid);
    }

    // 33. 搜索旋转排序数组
    public int search(int[] nums, int target) {
        if (nums == null || nums.length == 0) {
            return -1;
        }
        int start = 0;
        int end = nums.length - 1;
        int mid;
        while (start <= end) {
            mid = start + (end - start) / 2;
            if (nums[mid] == target) {
                return mid;
            }
            // 前半部分有序,注意此处用小于等于
            if (nums[start] <= nums[mid]) {
                // target在前半部分
                if (target >= nums[start] && target < nums[mid]) {
                    end = mid - 1;
                } else {
                    start = mid + 1;
                }
            } else {
                if (target <= nums[end] && target > nums[mid]) {
                    start = mid + 1;
                } else {
                    end = mid - 1;
                }
            }

        }
        return -1;

    }
}
