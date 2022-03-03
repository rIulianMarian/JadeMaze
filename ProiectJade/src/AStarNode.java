import java.util.ArrayList;


public class AStarNode 
{
    public int f=0;
    public int g=0;
    public int h=0;
    public int value=0;
    public int x=0;
    public int y=0;
    public AStarNode parent = null;
    public ArrayList<AStarNode> vecini = new ArrayList<AStarNode>();
    AStarNode(int f,int g,int h, int value, int x, int y)
    {
        this.f = f;
        this.g = g;
        this.h = h;
        this.value = value;
        this.x = x;
        this.y = y;
    }
    
    public void addNeighbours(AStarNode[][] newmaze)
    {
        if(x<newmaze.length-1)
        {
            if(newmaze[x+1][y].value != 1)
            {
                vecini.add(newmaze[x+1][y]);
            }
        }
        if(x>0)
        {
            if(newmaze[x-1][y].value != 1)
            {
                vecini.add(newmaze[x-1][y]);
            }
        }
        if(y<newmaze[0].length-1)
        {
            if(newmaze[x][y+1].value != 1)
            {
                vecini.add(newmaze[x][y+1]);
            }
        }
        if(y>0)
        {
            if(newmaze[x][y-1].value != 1)
            {
                vecini.add(newmaze[x][y-1]);
            }
        }
        
        if(x>0 && y>0)
        {
            if(newmaze[x-1][y-1].value != 1)
            {
                vecini.add(newmaze[x-1][y-1]);
            }            
        }
        if(x<newmaze.length-1 && y>0)
        {
            if(newmaze[x+1][y-1].value != 1)
            {
                vecini.add(newmaze[x+1][y-1]);
            }            
        } 
        if(x<newmaze.length-1 && y<newmaze[0].length-1)
        {
            if(newmaze[x+1][y+1].value != 1)
            {
                vecini.add(newmaze[x+1][y+1]);
            }            
        }
        if(x>0 && y<newmaze[0].length-1)
        {
            if(newmaze[x-1][y+1].value != 1)
            {
                vecini.add(newmaze[x-1][y+1]);
            }            
        }         
        
    }    
    
}
