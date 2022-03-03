import jade.core.Agent;
import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.UnreadableException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class AgentAStar  extends Agent
{
    public void setup()
    {
        addBehaviour(new AsteaptaRaspuns());
    } 
    private class AsteaptaRaspuns extends CyclicBehaviour
    {
        private AStarNode[][] newmaze;
        private ArrayList<AStarNode> openSet = new ArrayList<AStarNode>();
        private ArrayList<AStarNode> closeSet = new ArrayList<AStarNode>();
        private ArrayList<MoveNode> ListaFinala = new ArrayList<MoveNode>();
        private ArrayList<MoveNode> BackupListaFinala = new ArrayList<MoveNode>();
        private int[] Start = new int[2];
        private int[] End = new int[2];  
        private int[][] maze;
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
                    solve(this.maze);
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
        private void solve(int[][] maze)
        {
            newmaze = new AStarNode[maze.length][maze[0].length];
            for(int i = 0; i<maze.length;i++)
            {
                for(int j=0;j<maze[0].length;j++)
                {
                    newmaze[i][j] = new AStarNode(0,0,0,maze[i][j],i,j);
                    if(maze[i][j]==2)
                    {
                        Start[0] = i;
                        Start[1] = j;
                    }
                }
            }

            for(int i =0; i<maze.length;i++)
            {
                for(int j=0;j<maze[0].length;j++)
                {
                    newmaze[i][j].addNeighbours(newmaze);
                }
            }

            openSet.add(newmaze[Start[0]][Start[1]]);

            while(!openSet.isEmpty())
            {
                int lowestIndex = 0;
                for(int i =0; i < openSet.size();i++)
                {
                    if(openSet.get(i).f < openSet.get(lowestIndex).f)
                    {
                        lowestIndex = i;
                    }
                }

                if(maze[openSet.get(lowestIndex).x][openSet.get(lowestIndex).y] == 3)
                {
                    if(ListaFinala.size() > 0)
                    {
                        ArrayList<MoveNode> ListaSecundara = new ArrayList<MoveNode>();
                        AStarNode curNode = openSet.get(lowestIndex);
                        ListaSecundara.add(new MoveNode(curNode.x,curNode.y));
                        while(curNode.parent!=null)
                        {
                            curNode = curNode.parent;
                            ListaSecundara.add(new MoveNode(curNode.x,curNode.y));
                        }
                        if(ListaSecundara.size() < ListaFinala.size())
                        {
                            ListaFinala=ListaSecundara;
                        }
                    }
                    else
                    {
                        AStarNode curNode = openSet.get(lowestIndex);
                        ListaFinala.add(new MoveNode(curNode.x,curNode.y));
                        while(curNode.parent!=null)
                        {
                            curNode = curNode.parent;
                            ListaFinala.add(new MoveNode(curNode.x,curNode.y));
                        }
                    }
                }
                closeSet.add(openSet.get(lowestIndex));
                ArrayList<AStarNode> vecini = openSet.get(lowestIndex).vecini;

                int TempG;
                for(int i = 0; i<vecini.size();i++)
                {
                    if(closeSet.contains(vecini.get(i)))
                    {
                        continue;
                    }
                    TempG = openSet.get(lowestIndex).g+1;
                    if(openSet.contains(vecini.get(i)))
                    {
                        if(TempG < vecini.get(i).g)
                        {
                            vecini.get(i).g = TempG;
                            vecini.get(i).parent = openSet.get(lowestIndex);
                        }
                    }
                    else
                    {
                        vecini.get(i).g = TempG;
                        vecini.get(i).parent = openSet.get(lowestIndex);
                        openSet.add(vecini.get(i));
                    } 
                }

                openSet.remove(openSet.get(lowestIndex));
            }                    
        }
        public ArrayList<MoveNode> returnNoduri()
        {
            BackupListaFinala.removeAll(BackupListaFinala);
            for(int i = 0; i<ListaFinala.size();i++)
            {
                BackupListaFinala.add(new MoveNode(ListaFinala.get(i).getY(),ListaFinala.get(i).getX()));
            }
            ListaFinala.removeAll(ListaFinala);
            return BackupListaFinala;
        }        
    }    
}
