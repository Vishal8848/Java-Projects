class StrokeEvaluator {

    public boolean checkHorizontalStroke(int grid[][]) {

        int i, j;
        boolean win = true;

        for(i = 0; i < 3; i++)  {
            win = true;
            for(j = 1; j < 3; j++)  {
                if(grid[i][j] == 0 || grid[i][0] != grid[i][j])    {
                    win = false;
                    break;
                }
            }
            if(win) break;
        }

        if(win) GridPrinter.printWinGrid(grid, grid[i][0] == 2, 0, i);

        return win;
    }

    public boolean checkVerticalStroke(int grid[][])   {

        int i, j;
        boolean win = true;

        for(i = 0; i < 3; i++)  {
            win = true;
            for(j = 1; j < 3; j++)  {
                if(grid[j][i] == 0 || grid[0][i] != grid[j][i])    {
                    win = false;
                    break;
                }
            }
            if(win) break;
        }

        if(win) GridPrinter.printWinGrid(grid, grid[0][i] == 2, 1, i);

        return win;

    }

    public boolean checkDiagonalStroke(int grid[][]) {

        boolean D1 = (grid[1][1] != 0) && (grid[1][1] == grid[0][0]) && (grid[1][1] == grid[2][2]);

        boolean D2 = (grid[1][1] != 0) && (grid[1][1] == grid[0][2]) && (grid[1][1] == grid[2][0]);

        if(D1)  GridPrinter.printWinGrid(grid, grid[1][1] == 2, 2, 0);

        if(D2)  GridPrinter.printWinGrid(grid, grid[1][1] == 2, 3, 0);

        return D1 || D2;

    }

}