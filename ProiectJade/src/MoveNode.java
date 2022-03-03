
import java.io.Serializable;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author SpecTeR
 */
public class MoveNode implements Serializable
{
    private int x;
    private int y;


    MoveNode(int x, int y)
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
}
