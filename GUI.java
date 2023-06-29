import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.border.Border;
import java.awt.event.*;
import java.util.*;
import java.awt.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.TableCellRenderer;
import java.awt.Component;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
public class GUI extends JFrame{
    private JTable tb_processStatus;
    private JTable tb_processMessages;
    DefaultTableModel processStatusModel ;
    DefaultTableModel processMessagesModel ;
    private int w = 1000;
    private int h = 600;

    public GUI(int nbProcess){
        super("TP SYSR");
        this.setSize(w, h);
        Dimension screenDimension = Toolkit.getDefaultToolkit().getScreenSize(); 
        this.setLocation(
            (screenDimension.width-w)/2,
            (screenDimension.height-h)/2
        );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        Color backgroundColor = Color.WHITE; // Set the desired background color
        this.getContentPane().setBackground(backgroundColor);
        

        // For column content centring 
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(DefaultTableCellRenderer.CENTER);


        // Process status table 
        processStatusModel = new DefaultTableModel();
        processStatusModel.addColumn("Num");
        processStatusModel.addColumn("PID");
        processStatusModel.addColumn("Status");
        tb_processStatus = new JTable(processStatusModel){
            // Disabling cell editing 
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };


        tb_processStatus.setRowHeight(45);
        
        tb_processStatus.getColumnModel().getColumn(0).setMaxWidth(70);
        tb_processStatus.getColumnModel().getColumn(0).setMinWidth(70);
        tb_processStatus.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        tb_processStatus.getColumnModel().getColumn(1).setMaxWidth(110);
        tb_processStatus.getColumnModel().getColumn(1).setMinWidth(110);
        tb_processStatus.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);


        tb_processStatus.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);

        JScrollPane sp_status = new JScrollPane(tb_processStatus);

        JTableHeader header = tb_processStatus.getTableHeader();

        // Create a custom TableCellRenderer for the header cells
        DefaultTableCellRenderer headerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setBackground(new Color(250, 217, 213)); // (250, 217, 213)  (195, 239, 255)
                component.setForeground(Color.BLACK);
                setHorizontalAlignment(CENTER);
                // Set the font of the header text to bold
                setFont(getFont().deriveFont(Font.BOLD));
                Border border = BorderFactory.createLineBorder(Color.BLACK);
                setBorder(border);
                return component;
            }
        };

        // Set the preferred height of the header
        header.setPreferredSize(new Dimension(header.getPreferredSize().width, 50));

        // Apply the custom TableCellRenderer to the header cells
        header.setDefaultRenderer(headerRenderer);



        
        // Process messages table 
        processMessagesModel = new DefaultTableModel();
        processMessagesModel.addColumn("Source");
        processMessagesModel.addColumn("Destination");
        processMessagesModel.addColumn("Message");
        tb_processMessages = new JTable(processMessagesModel){
            // Disabling cell editing 
            public boolean editCellAt(int row, int column, java.util.EventObject e) {
                return false;
            }
        };
        tb_processMessages.setRowHeight(28);


        tb_processMessages.setRowHeight(47);
        
        tb_processMessages.getColumnModel().getColumn(0).setMaxWidth(110);
        tb_processMessages.getColumnModel().getColumn(0).setMinWidth(110);
        tb_processMessages.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);

        tb_processMessages.getColumnModel().getColumn(1).setMaxWidth(110);
        tb_processMessages.getColumnModel().getColumn(1).setMinWidth(110);
        tb_processMessages.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);


        tb_processMessages.getColumnModel().getColumn(2).setCellRenderer(centerRenderer);


        JScrollPane sp_messages = new JScrollPane(tb_processMessages);
        sp_messages.setAutoscrolls(true);

           // Create a SmartScroller and attach it to the JScrollPane
            SmartScroller scroller = new SmartScroller(sp_messages, SmartScroller.VERTICAL, SmartScroller.END);
            // scroller.setScrollBackward(true); // Enable scrolling to the top
            // scroller.setScrollForward(true); // Enable scrolling to the bottom

        JTableHeader header_msg = tb_processMessages.getTableHeader();
        // Create a custom TableCellRenderer for the header cells
        DefaultTableCellRenderer headerRenderer_msg = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component component = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                component.setBackground(new Color(195, 239, 255)); // (250, 217, 213)  (195, 239, 255)
                component.setForeground(Color.BLACK);
                setHorizontalAlignment(CENTER);
                // Set the font of the header text to bold
                setFont(getFont().deriveFont(Font.BOLD));
                Border border = BorderFactory.createLineBorder(Color.BLACK);
                setBorder(border);
                return component;
            }
        };
        // Set the preferred height of the header
        header_msg.setPreferredSize(new Dimension(header_msg.getPreferredSize().width, 50));
        // Apply the custom TableCellRenderer to the header cells
        header_msg.setDefaultRenderer(headerRenderer_msg);




        // Titles
        JLabel title_status = new JLabel();
        title_status.setText("Status des processus");
        
        JLabel title_messages = new JLabel();
        title_messages.setText("Messages echanges ");
        //Layout 

        GroupLayout layout = new GroupLayout(this.getContentPane());
        this.getContentPane().setLayout(layout);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        layout.setHorizontalGroup(
            layout.createSequentialGroup()
                .addGroup(
                    layout.createParallelGroup()
                    .addComponent(title_status)
                    .addComponent(sp_status)
                )
                .addGroup(
                    layout.createParallelGroup()
                        .addComponent(title_messages)
                        .addComponent(sp_messages)    
                )
        );
        layout.setVerticalGroup(
            layout.createParallelGroup()
                .addGroup(
                    layout.createSequentialGroup()
                    .addComponent(title_status)
                    .addComponent(sp_status)
                )
                .addGroup(
                    layout.createSequentialGroup()
                        .addComponent(title_messages)
                        .addComponent(sp_messages)    
                )

        );
        

        this.setVisible(true);
    }

    private void closeWindow(){
        this.dispose();
    }
    
    private void stopWindow(){
        synchronized(this){
            try{this.wait();}catch(Exception ex){ex.printStackTrace();} 
        }
    } 

    public synchronized void addMessage(int source,int destination,String message){
            Object grp[]= {source,destination,message};
            processMessagesModel.addRow(grp); 
            //? Scroll to the last row
            //! Remove the 2 lines below if you want to scroll manually 
            // Rectangle rect = tb_processMessages.getCellRect(processMessagesModel.getRowCount() - 1, 0, true);
            // tb_processMessages.scrollRectToVisible(rect);
    }

    public void addProcess(int id,int pid,String status){
        Object grp[]= {id,pid,status};
        processStatusModel.addRow(grp); 
    }

    public synchronized void updateProcessStatus(int id,String newStatus){
        processStatusModel.setValueAt(newStatus, id, 2);
    }

    


    // public void refresh(){
    //     while(tableModel.getRowCount() > 0){
    //         tableModel.removeRow(0);
    //     }
    //     buildStudentTable(tableModel);
    // }
    // private ListOfStudentsW getMe(){return this;}
} 
