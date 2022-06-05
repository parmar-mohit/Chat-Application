import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;

public class ClientProgram extends JFrame implements ActionListener {
    private JPanel messagePanel;
    private JScrollPane scrollPane;
    private JTextField messageTextField;
    private JButton sendButton;
    private Socket socket;
    private int location;
    public ClientProgram(){
        //Initialising Members Variables
        location = 0;
        messagePanel = new JPanel();
        scrollPane = new JScrollPane(messagePanel);
        messageTextField = new JTextField();
        sendButton = new JButton("Send");

        //Editing Member Variables
        messagePanel.setLayout(new GridBagLayout());
        messagePanel.setMinimumSize(new Dimension(600,300));
        messagePanel.setPreferredSize(new Dimension(600,300));
        messagePanel.setVisible(true);
        scrollPane.setMinimumSize(new Dimension(600,300));
        scrollPane.setPreferredSize(new Dimension(600,300));
        scrollPane.getViewport().setLayout(new GridBagLayout());
        messageTextField.setPreferredSize(new Dimension(500,50));
        sendButton.setPreferredSize(new Dimension(100,50));
        sendButton.addActionListener(this);

        //Setting Frame Details
        setTitle("Chat Application : Client");
        setSize(new Dimension(800,500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new GridBagLayout());

        //Adding Members on Frame
        add(scrollPane, Constraint.setPosition(0,0,2,1));
        add(messageTextField,Constraint.setPosition(0,1));
        add(sendButton,Constraint.setPosition(1,1));

        try{
            System.out.println("Client Program : Connecting to  Socket");
            socket = new Socket("localhost",45689);
            System.out.println("Client Program : Socket Connected");

            //New Thread to Receive Messages
            new Thread(){
                @Override
                public void run() {
                    while(true){
                        String message = Messages.receiveMessage(socket);
                        if( message != null) {
                            System.out.println("Client Program : Message \""+message+"\" Received");
                            JLabel messageLabel = new JLabel(message);
                            messageLabel.setBackground(new Color(135, 230, 237));
                            messageLabel.setPreferredSize(new Dimension(300,30));
                            messagePanel.add(messageLabel,Constraint.setPosition(0,location,Constraint.LEFT));
                            location++;
                            revalidate();
                            repaint();
                        }
                    }
                }
            }.start();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String message = messageTextField.getText();
        messageTextField.setText("");
        if( !message.equals("") ){
            JLabel messageLabel = new JLabel(message);
            messageLabel.setBackground(new Color(101, 194, 126));
            messageLabel.setPreferredSize(new Dimension(300,30));
            messagePanel.add(messageLabel,Constraint.setPosition(1,location,Constraint.RIGHT));
            location++;
            revalidate();
            repaint();
            Messages.sendMessage(socket,message);
            System.out.println("Client Program : Message \""+message+"\" Sent");
        }
    }
}
