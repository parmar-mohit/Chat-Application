import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.Socket;

public class Messages {
    public static void sendMessage(Socket s,String message){
        try {
            DataOutputStream dout = new DataOutputStream(s.getOutputStream());
            dout.writeUTF(message);
            dout.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static String receiveMessage(Socket s){
        try {
            DataInputStream din = new DataInputStream(s.getInputStream());
            String message = din.readUTF();
            return message;
        }catch(Exception e){
            e.printStackTrace();
        }

        return null;
    }
}
