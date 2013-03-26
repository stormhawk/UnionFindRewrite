/**
 * Created with IntelliJ IDEA.
 * User: Alex 2011
 * Date: 3/24/13
 * Time: 5:45 PM
 * To change this template use File | Settings | File Templates.
 */
public class Percolation {
    private boolean[][] grid;
    private WeightedQuickUnionUF wqf;
    private int gridSize;

    public Percolation(int N)              // create N-by-N grid, with all sites blocked
    {
        gridSize = N;
        grid = new boolean[N][N];
        wqf = new WeightedQuickUnionUF((N*N));
        for(int i = 0; i < N; i++)
        {
            for(int j = 0; j < N; j++)
            {
                grid[i][j] = false;
            }
        }
    }
    public void open(int i, int j)         // open site (row i, column j) if it is not already
    {
        int ai = i-1;
        int aj = j-1;
        grid[ai][aj] = true;
        if (ai-1 >= 0 && isOpen(i - 1, j))  //left
        {
            wqf.union(to2D(i,j),to2D(i-1,j));
        }
        if (ai+1 < gridSize && isOpen(i + 1, j))         //right
        {
            wqf.union(to2D(i,j),to2D(i+1,j));
        }
        if (aj-1 >= 0 && isOpen(i, j-1))     //up
        {
            wqf.union(to2D(i,j),to2D(i,j-1));
        }
        if (aj+1 < gridSize && isOpen(i, j+1))     //down
        {
            wqf.union(to2D(i,j),to2D(i,j+1));
        }
    }
    public boolean isOpen(int i, int j)    // is site (row i, column j) open?
    {
        return grid[i-1][j-1];
    }
    public boolean isFull(int i, int j)    // is site (row i, column j) full?
    {
        if(isOpen(i,j))
        {
            for(int k = 0; k < gridSize; k++)
            {

               if(wqf.connected(to2D(i,j),k)) return true;
            }
        }
        return false;
    }
    public boolean percolates()            // does the system percolate?
    {
        if (gridSize == 1)
        {
            if (isOpen(1,1))
            {
                return true;
            }
            return false;
        }
        if(gridSize == 2)
        {
            if (wqf.connected(0,3)) return true;
            if (wqf.connected(1,2)) return true;
            if (wqf.connected(0,2)) return true;
            if (wqf.connected(1,3)) return true;
            return false;
        }


        for (int i = (gridSize * (gridSize - 1))-1; i < (gridSize * gridSize); i++)
        {
           // System.out.println((gridSize * (gridSize - 1))-1);
            //System.out.println(gridSize * gridSize-1);
            for (int i2 = 0; i2 < gridSize; i2++)
            {
                //System.out.println(i);
                //System.out.println(i2);
                //System.out.print(wqf.connected(i, i2));
                if (wqf.connected(i, i2)) return true;

            }
        }
        return false;
    }
    private int to2D(int i, int j)
    {
        return (i-1)*gridSize+(j-1);
    }

}
