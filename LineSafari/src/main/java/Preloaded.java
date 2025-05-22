/**
 * @author Ivan Ivanov
 **/
public class Preloaded {
    public static char[][] makeGrid(String[] strings) {
        int height = strings.length;
        int width = strings[0].length();
        char[][] grid = new char[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                grid[i][j] = strings[i].toCharArray()[j];
            }
        }
        return grid;
    }

    public static void showGrid(char[][] grid) {
        for(char[] line: grid){
            for (char symbol: line){
                System.out.print(symbol);
            }
            System.out.println();
        }
        System.out.println();
    }
}
