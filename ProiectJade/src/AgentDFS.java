
import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AgentDFS extends Agent
{    
    public void setup()
    {
        addBehaviour(new AsteaptaRaspuns());
    } 
    
    private class AsteaptaRaspuns extends CyclicBehaviour
    {
        private int[][] maze;
        private DFSNode[][] prev;

        private int sizeX;
        private int sizeY;

        private DFSNode lastNode;
        private ArrayList<DFSNode> ListaNoduri = new ArrayList<DFSNode>();
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
                    this.sizeY = maze.length;
                    this.sizeX = maze[0].length;  
                    prev = new DFSNode[sizeY][sizeX];
                    solve(new DFSNode(lsm.getStartX(), lsm.getStartY()));
                    fillPath(); 
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
        
        private boolean inBoundsX(int number){
            return number >= 0 && number < sizeX;
        }

        private boolean inBoundsY(int number){
            return number >= 0 && number < sizeY;
        }      
        
        public void solve(DFSNode start)
        {
            Stack<DFSNode> stack = new Stack<>();
            HashSet<DFSNode> visited = new HashSet<>();

            stack.push(start);

            while(!stack.isEmpty()) 
            {
                DFSNode tmp = stack.pop();
                visited.add(tmp);

                if (maze[tmp.getY()][tmp.getX()] == 3) 
                {
                    lastNode = tmp;
                    break;
                }

                for(DFSNode node : this.getAdjacentEdges(tmp)) 
                {
                    if (!visited.contains(node)) 
                    {
                        stack.push(node);
                        prev[node.getY()][node.getX()] = tmp;
                    }
                }
            }
        }

        public void fillPath() 
        {
            if (lastNode == null) 
            {
                System.out.println("Nu se poate iesi din labirint.");
            } 
            else 
            {
                ListaNoduri.add(lastNode);
                for (;;) 
                {
                    lastNode = prev[lastNode.getY()][lastNode.getX()];

                    if (lastNode == null) 
                    {
                        break;
                    }
                    //maze[lastNode.getY()][lastNode.getX()] = 4;
                    ListaNoduri.add(lastNode);
                }
            }
        }  
        
        public ArrayList<MoveNode> returnNoduri()
        {
            ReturnNodes.removeAll(ReturnNodes);
            for(int i = 0;i<ListaNoduri.size();i++)
            {
                ReturnNodes.add(new MoveNode(ListaNoduri.get(i).getX(),ListaNoduri.get(i).getY()));
            }        
            ListaNoduri.removeAll(ListaNoduri);
            return ReturnNodes;
        }

        private List<DFSNode> getAdjacentEdges(DFSNode tmp) 
        {
            List<DFSNode> neighbours = new ArrayList<DFSNode>();
            if(this.inBoundsX(tmp.getX()+1))
            {
                if(this.maze[tmp.getY()][tmp.getX()+1] != 1)
                {
                    neighbours.add(new DFSNode(tmp.getX()+1, tmp.getY()));
                }
            }
            if(this.inBoundsX(tmp.getX()-1))
            {
                if(this.maze[tmp.getY()][tmp.getX()-1] != 1)
                {
                    neighbours.add(new DFSNode(tmp.getX()-1, tmp.getY()));
                }
            }
            if(this.inBoundsY(tmp.getY()+1))
            {
                if(this.maze[tmp.getY()+1][tmp.getX()] != 1)
                {
                    neighbours.add(new DFSNode(tmp.getX(), tmp.getY()+1));
                }
            }
            if(this.inBoundsY(tmp.getY()-1))
            {
                if(this.maze[tmp.getY()-1][tmp.getX()] != 1)
                {
                    neighbours.add(new DFSNode(tmp.getX(), tmp.getY()-1));
                }
            }
            return neighbours;
        }         
        
        
    }
}
