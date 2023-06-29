import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class handler extends Thread {
    Process parent;
    Socket clientSocket;
    public handler(Process process,Socket clientSocket){
        this.parent=process;
        this.clientSocket=clientSocket;
    }
    
    public void run() {
        try {
            InputStream inputStream = clientSocket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String message;
            while ((message = reader.readLine()) != null) {
                if(message.equals("rep")){
                    parent.nbrepattenduesSemaphore.acquire();
                    parent.nbrepattendues--;
                    if(parent.nbrepattendues!=0){
                        String statusString="En Attente de "+parent.nbrepattendues+" reponses."+" H = "+parent.getHSN();
                        parent.interfce.updateProcessStatus(parent.getID()-1,statusString);
                    }
                    parent.nbrepattenduesSemaphore.release();
                    
                }
                else{
                    String[] req = message.split(",");
                    long k=Long.parseLong(req[1]);
                    int j=Integer.parseInt(req[2]);
                    parent.hsnSemaphore.acquire();
                    parent.hsn=Math.max(parent.hsn,k)+1;
                    parent.hsnSemaphore.release();
                    parent.osnSemaphore.acquire();
                    parent.scSemaphore.acquire();
                    long osn=parent.osn;
                    boolean priorite = (parent.Scdemandee && ((k>osn) || (k==osn && parent.getID()+50000<j)));
                    parent.scSemaphore.release(); 
                    parent.osnSemaphore.release();
                     
                    if(priorite){
                        parent.repDiffSemaphore.acquire();
                        parent.repDiffer[j-50001]=true;
                        parent.repDiffSemaphore.release();
                    }
                    else{
                        Socket socket;
                        socket = new Socket("127.0.0.1",j);
                        OutputStream outputStream = socket.getOutputStream();
                        PrintWriter writer = new PrintWriter(outputStream, true);
                        String msg="rep";
                        writer.println(msg);
                        socket.close();
                        parent.interfce.addMessage(parent.getID(), j-50000, "REP");
                    }
                }
            }
            // Close the client socket and server socket
            clientSocket.close();
        }catch (Exception e) {
            e.printStackTrace();
        }  
    }
    
}
