import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;
import jade.wrapper.StaleProxyException;

public class ProiectJade 
{
    public static void main(String[] args) 
    {
        Runtime rt = Runtime.instance();
        Profile p = new ProfileImpl();
        ContainerController cc = rt.createMainContainer(p);
        try 
        {
            AgentController rma = cc.createNewAgent("rma", "jade.tools.rma.rma", new Object[0]);
            rma.start();
        }  
        catch (StaleProxyException e) 
        {
            e.printStackTrace();
        }
        Object referinta = new Object();
        Object argumente[] = new Object[1];
        argumente[0] = referinta;
        
        try 
        {
            AgentController ag = cc.createNewAgent("Labirint", "AgentLabirint", argumente);
            ag.start();
        } 
        catch (StaleProxyException e) 
        {
            e.printStackTrace();
        }        
        
        try 
        {
            AgentController ag = cc.createNewAgent("DFS", "AgentDFS", argumente);
            ag.start();
        } 
        catch (StaleProxyException e) 
        {
            e.printStackTrace();
        }  

        try 
        {
            AgentController ag = cc.createNewAgent("BFS", "AgentBFS", argumente);
            ag.start();
        } 
        catch (StaleProxyException e) 
        {
            e.printStackTrace();
        }  
        
        try 
        {
            AgentController ag = cc.createNewAgent("A*", "AgentAStar", argumente);
            ag.start();
        } 
        catch (StaleProxyException e) 
        {
            e.printStackTrace();
        }         
        
        try 
        {
            AgentController ag = cc.createNewAgent("AMSAgent", "AMSAgent", argumente);
            ag.start();
        } 
        catch (StaleProxyException e) 
        {
            e.printStackTrace();
        }          
        
    }
}
