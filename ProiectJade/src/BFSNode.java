/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SpecTeR
 */
public class BFSNode 
{
    int x, y, dist;
    BFSNode parent = null;
    BFSNode(int x, int y, int dist, BFSNode parent) {
        this.x = x;
        this.y = y;
        this.dist = dist;
        this.parent = parent;
    }   
    
    int getX()
    {
        return this.x;
    }
    
    BFSNode getParent()
    {
        return parent;
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
        BFSNode tmp = (BFSNode) obj; 
        return tmp.getX() == this.getX() && this.getY() == tmp.getY();
    }    
    
    
}
