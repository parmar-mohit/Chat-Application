import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerProgram extends JFrame implements ActionListener {
    private JPanel messagePanel;
    private JScrollPane scrollPane;
    private JTextField messageTextField;
    private JButton sendButton;
    private ServerSocket socket;
    private Socket client;
    private int location;
    public ServerProgram(){
        //Initialising Members Variables
        messagePanel = new JPanel();
        scrollPane = new JScrollPane(messagePanel);
        messageTextField = new JTextField();
        sendButton = new JButton("Send");
        location = 0;

        //Editing Member Variables
        messagePanel.setLayout(new GridBagLayout());
        messagePanel.setMinimumSize(new Dimension(600,300));
        messagePanel.setPreferredSize(new Dimension(600,300));
        messagePanel.setVisible(true);
        scrollPane.getViewport().setLayout(new GridBagLayout());
        scrollPane.setMinimumSize(new Dimension(600,300));
        scrollPane.setPreferredSize(new Dimension(600,300));
        messageTextField.setPreferredSize(new Dimension(500,50));
        sendButton.setPreferredSize(new Dimension(100,50));
        sendButton.addActionListener(this);

        //Setting Frame Details
        setTitle("Chat Application : Server");
        setSize(new Dimension(800,500));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        setLayout(new GridBagLayout());

        //Adding Members on Frame
        add(scrollPane, Constraint.setPosition(0,0,2,1));
        add(messageTextField,Constraint.setPosition(0,1));
        add(sendButton,Constraint.setPosition(1,1));

        try {
            System.out.println("Server Program : Creating Socket");
            socket = new ServerSocket(45689);
            System.out.println("Server Program : Socket Created");

            //Accepting Connection
            client = socket.accept();
            System.out.println("Server Program : Connection Accepted");

            //New Thread to Receivee Messages
            new Thread(){
                @Override
                public void run() {
                    while(true){
                        String message = Messages.receiveMessage(client);
                        if( message != null ){
                            System.out.println("Server Program : Message \""+message+"\" Received");
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
            Messages.sendMessage(client,message);
            System.out.println("Server Program : Message \""+message+"\" Sent");
        }
    }
}
