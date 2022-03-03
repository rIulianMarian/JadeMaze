
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Queue;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AgentBFS extends Agent
{
    private boolean NuSePoateIesi = false;
    public void setup()
    {
        addBehaviour(new AsteaptaRaspuns());
    } 

    private class AsteaptaRaspuns extends CyclicBehaviour
    {
        private int[][] maze;
        private int sizeX; //M
        private int sizeY; //N
        private final int row[] = { -1, 0, 0, 1 };
        private final int col[] = { 0, -1, 1, 0 };    
        private ArrayList<BFSNode> ListaNoduri = new ArrayList<BFSNode>();
        private ArrayList<MoveNode> ReturnNodes = new ArrayList<MoveNode>();        
        public AsteaptaRaspuns()
        {
            
        }          
        public void action()
        {
            ACLMessage message = myAgent.receive();
            if(message != null)
            {
                LabirintSendMessage lsm;
                try 
                {
                    lsm = (LabirintSendMessage) message.getContentObject();
                    this.maze = lsm.getLabirint();
                    this.sizeY = maze[0].length;
                    this.sizeX = maze.length;  
                    solve(lsm.getStartX(), lsm.getStartY());
                } 
                catch (UnreadableException ex) 
                {
                    Logger.getLogger(AgentDFS.class.getName()).log(Level.SEVERE, null, ex);
                }
                ACLMessage answer = new ACLMessage();
                answer.addReceiver(message.getSender());
                try 
                {
                    answer.setContentObject(returnNoduri());
                    myAgent.send(answer);
                } 
                catch (IOException ex) 
                {
                    Logger.getLogger(AgentDFS.class.getName()).log(Level.SEVERE, null, ex);
                }       
            }
            else
            {
                block();
            }            
        }

        private boolean isValid(int mat[][], boolean visited[][], int row, int col)
        {
            return (row >= 0) && (row < sizeX) && (col >= 0) && (col < sizeY) && (mat[row][col] == 0 || mat[row][col] == 3) && !visited[row][col];
        }   

        public void solve(int j, int i)
        {
            boolean[][] visited = new boolean[sizeX][sizeY];
            Queue<BFSNode> q = new ArrayDeque<>();
            visited[i][j] = true;
            q.add(new BFSNode(i, j, 0, null));
            int min_dist = Integer.MAX_VALUE;
            while (!q.isEmpty())
            {
                BFSNode node = q.poll();
                i = node.x;
                j = node.y;
                int dist = node.dist;
                if (maze[i][j] == 3)
                {
                    min_dist = dist;
                    ListaNoduri.add(node);
                    fillList(node);
                    break;
                }
                for (int k = 0; k < 4; k++)
                {
                    if (isValid(maze, visited, i + row[k], j + col[k]))
                    {
                        visited[i + row[k]][j + col[k]] = true;
                        q.add(new BFSNode(i + row[k], j + col[k], dist + 1, node));
                    }
                }
            }

            if (!(min_dist != Integer.MAX_VALUE)) 
            {
                System.out.println("Nu se poate iesi din labirint.");
                NuSePoateIesi = true;
            }
        }    

        public void fillList(BFSNode node) 
        {
            if(!NuSePoateIesi)
            {
                if(node.getParent() != null)
                {
                    ListaNoduri.add(node);
                    fillList(node.getParent());
                }
            }
        } 

        public ArrayList<MoveNode> returnNoduri()
        {
            ReturnNodes.removeAll(ReturnNodes);
            for(int i = 0;i<ListaNoduri.size();i++)
            {
                ReturnNodes.add(new MoveNode(ListaNoduri.get(i).getY(),ListaNoduri.get(i).getX()));
            }
            //System.out.println("BFS steps: "+ListaNoduri.size());
            ListaNoduri.removeAll(ListaNoduri);
            return ReturnNodes;
        }    
        
    }
    
}
