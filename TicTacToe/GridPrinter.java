class GridPrinter   {

    public static void printGrid(int grid[][]) {

        System.out.println("\nTic Tac Toe\n\n-------------");
        for(int i = 0; i < 3; i++)  {
            for(int j = 0; j < 3; j++)  {
                System.out.print("|");
                System.out.print(grid[i][j] == 1 ? " X " : grid[i][j] == 2 ? " O " : "   ");
            }   System.out.println("|\n-------------");
        }

    }

    public static void printWinGrid(int grid[][], boolean player, int line, int level)   {

        String symbol = player ? " O " : " X ";
        System.out.println("\nTic Tac Toe\n\n-------------");
        for(int i = 0; i < 3; i++)  {
            for(int j = 0; j < 3; j++)  {
                System.out.println("|");
                switch(line)    {
                    case 0:
                        System.out.println(i == level ? symbol : "   ");
                        break;
                    case 1:
                        System.out.println(j == level ? symbol : "   ");
                        break;
                    case 2:
                        System.out.println(i == j ? symbol : "   ");
                        break;
                    case 3:
                        System.out.println((i + j) == 2 ? symbol : "   ");
                        break;
                    default:
                        System.out.println("   ");
                }
            }
        }
        
    }

}