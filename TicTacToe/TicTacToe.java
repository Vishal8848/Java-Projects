import java.util.Scanner;

class TicTacToe {

    public static boolean isLocationValid(int x, int y) {  return x >= 0 && x <= 2 && y >= 0 && y <= 2; }

    public static void main(String[] args) {

        int grid[][] = new int[3][3];
        
        Scanner read = new Scanner(System.in);

        for(int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                grid[i][j] = 0;

        GridPrinter.printGrid(grid);

        int X, Y;
        boolean flag = false;
        for(int i = 0; i < 9; i++)  {
            
            System.out.println("\nPlay " + (flag ? "O" : "X"));

            System.out.print("\nEnter Coordinates: ");
            X = read.nextInt();
            Y = read.nextInt();

            if(isLocationValid(X, Y) && grid[X][Y] == 0)  {
                grid[X][Y] = flag ? 2 : 1;
                GridPrinter.printGrid(grid);
                flag = !flag;
            }   else  {
                System.out.println("\n- Choose Different Location -"); 
                i--;
            }

        }

        read.close();
    }
}