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

    public static void printWinGrid(int grid[][], boolean player, int stroke, int line)   {

        String symbol = player ? " O " : " X ";
        
        System.out.println("\n------ Winner" + symbol + "------\n\n    -------------");
        for(int i = 0; i < 3; i++)  {
            for(int j = 0; j < 3; j++)  {
                System.out.print(j == 0 ? "    |" : "|");
                
                switch(stroke)    {
                    case 0:
                        System.out.print(i == line ? symbol : "   ");
                        break;
                    case 1:
                        System.out.print(j == line ? symbol : "   ");
                        break;
                    case 2:
                        System.out.print(i == j ? symbol : "   ");
                        break;
                    case 3:
                        System.out.print((i + j) == 2 ? symbol : "   ");
                        break;
                    default:
                        System.out.print("   ");
                }

            }   System.out.println("|\n    -------------");
        }

    }

}