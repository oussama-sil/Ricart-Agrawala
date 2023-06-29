import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Random;

public class ThreadSc extends Thread {
    Process parent;
    public ThreadSc(Process parent){
        this.parent=parent;
    }
    public void run(){
        try{

        
        int minSC=1000;
            int maxSC=5000;
            int maxIN=2000;
            int minIN=1000;
            Random random = new Random();
            while(true){
                parent.scSemaphore.acquire();
                parent.Scdemandee=true;
                parent.scSemaphore.release();
                parent.hsnSemaphore.acquire();
                parent.osn=parent.hsn;
                parent.hsnSemaphore.release();
                parent.nbrepattenduesSemaphore.acquire();
                parent.nbrepattendues= parent.repDiffer.length-1;
                parent.nbrepattenduesSemaphore.release();
                /**Envoie de requetes de entrees en section critique**/
                for(int i=0;i<parent.repDiffer.length;i++){
                    if(i!=(parent.pSocket.getLocalPort()-50001)){
                        Socket socket;
                        try {
                            socket = new Socket("127.0.0.1",i+50001);
                            OutputStream outputStream = socket.getOutputStream();
                            PrintWriter writer = new PrintWriter(outputStream, true);
                            String msg="req,"+parent.osn+","+parent.pSocket.getLocalPort();
                            writer.println(msg);
                            socket.close();
                            parent.interfce.addMessage(parent.getID(), i+1, "REQ");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
                parent.nbrepattenduesSemaphore.acquire();
                String statusString="En Attente de "+parent.nbrepattendues+" reponses."+" H = "+parent.getHSN();
                parent.nbrepattenduesSemaphore.release();
                parent.interfce.updateProcessStatus(parent.getID()-1,statusString);
                while(true){
                     parent.nbrepattenduesSemaphore.acquire();
                     if(parent.nbrepattendues==0){
                        parent.nbrepattenduesSemaphore.release();
                        break;
                    }
                    else{parent.nbrepattenduesSemaphore.release();}
                }
                /** Entre dans la section critique**/
                parent.interfce.updateProcessStatus(parent.getID()-1,"En section critique" +" H = "+parent.getHSN() );
                try{
                    Thread.sleep(random.nextInt(maxIN-minIN+1)+minIN);
                }catch (InterruptedException e){System.out.println("Interrupted Exception");}
                /** Sortie de la section critique**/
                parent.scSemaphore.acquire();
                parent.Scdemandee=false;
                parent.scSemaphore.release();
                parent.interfce.updateProcessStatus(parent.getID()-1,"Dehors du section critique"+" H = "+parent.getHSN());
                /**Envoie les réponses différées**/
                for(int i=0;i<parent.repDiffer.length;i++){
                    parent.repDiffSemaphore.acquire();
                    if(parent.repDiffer[i]){
                        /**Envoyer reponse**/
                        try {
                            Socket socket;
                            socket = new Socket("127.0.0.1",i+50001);
                            OutputStream outputStream = socket.getOutputStream();
                            PrintWriter writer = new PrintWriter(outputStream, true);
                            parent.interfce.addMessage(parent.getID(), i+1, "REP DIFF");
                            String msg="rep";
                            writer.println(msg);
                            socket.close();
                            parent.repDiffer[i]=false;
                        } catch (Exception e) {
                            e.printStackTrace();
                        }  
                    }
                     parent.repDiffSemaphore.release();
            
                }
                try{
                    Thread.sleep(random.nextInt(maxSC-minSC+1)+minSC);
                }catch (InterruptedException e){System.out.println("Interrupted Exception");}
            }   

        }catch(Exception e){e.printStackTrace();}
    }
}
