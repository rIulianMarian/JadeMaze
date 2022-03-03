public class DFSNode 
{
    private int x;
    private int y;


    DFSNode(int x, int y)
    {
        this.x = x;
        this.y = y;
    }


    int getX()
    {
        return this.x;
    }

    int getY()
    {
        return this.y;
    }

    @Override
    public int hashCode()
    {
        return this.getX()+this.getY()+31;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false; 
        DFSNode tmp = (DFSNode) obj; 
        return tmp.getX() == this.getX() && this.getY() == tmp.getY();
    }

    @Override 
    public String toString()
    {
        return "x: " + this.getX() + " y: " + this.getY();
    }
}