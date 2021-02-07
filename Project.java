import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
//import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField.AbstractFormatter;
import javax.swing.JFormattedTextField;

//import javafx.scene.text.Font;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.NumberFormat;

import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.jdatepicker.impl.*;
/*import net.sourceforge.jdatepicker.impl.JDatePanelImpl;
import net.sourceforge.jdatepicker.impl.JDatePickerImpl;
import net.sourceforge.jdatepicker.impl.UtilDateModel;*/


class DateLabelFormatter extends AbstractFormatter {
    private static final long serialVersionUID = 1L;
    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);

    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }

    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }

        return "";
    }
}

/*class AppendingObjectOutputStream extends ObjectOutputStream {
      public AppendingObjectOutputStream(OutputStream out) throws IOException            {
        super(out);
      }
      protected void writeStreamHeader() throws IOException {
        reset();
      }
}
*/

class cancel
{
    int[] y = new int[16];
    int count=0;
    cancel(String PNR, String travels,String type, Date date,String seats)
    {
        //File f = new File(""+travels+date.getDate()+date.getMonth()+".txt");
        //System.out.println(date.getDate());
        int count = 0;
        FileReader fr = null;
        try
        {
            fr = new FileReader(""+travels+date.getDate()+date.getMonth()+".txt");
        }
        catch (FileNotFoundException e1)
        {
            e1.printStackTrace();
        }
        int z;
        try
        {
            while ((z = fr.read()) != -1)
            {
                y[count] = Character.getNumericValue((char) z);
                count++;
            }
        }
        catch (IOException e1)
        {
            e1.printStackTrace();
        }
        try
        {
            fr.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        for(int c=0;c<count;c++)
        {
            if(seats.charAt(c)=='1')
            {
                y[c]=0;
            }
        }
        PrintWriter outputStream = null;
        try
        {
          outputStream = new PrintWriter(""+travels+date.getDate()+date.getMonth()+".txt");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error opening the file " + ""+travels+date.getDate()+date.getMonth()+".txt");
            System.exit(0);
        }
        for (int c = 0; c <count; c++)
        {
            outputStream.print(y[c]);
        }
        outputStream.close( );
    }
}

class seater extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    static int seatCounter = 0;
    int[] x = new int[12];
    int[] y = new int[12];
    boolean exist = false;
    String d;
    int booked=0;
    JButton[] buttonArray = new JButton[12];
    
    public seater(String p){
        d=p;
        Project.sel_bus=p;
        getContentPane().setLayout(new GridLayout(3, 1));
        setMinimumSize(new Dimension(500, 500));
        JPanel buttonPanel = new JPanel();
        JPanel buttonPanel1 = new JPanel();
        JPanel aisle = new JPanel();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        File f1 = new File(""+p+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
        if (f1.exists())
        {
            int count = 0;
            FileReader fr = null;
            try
            {
                fr = new FileReader(""+p+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
            }
            catch (FileNotFoundException e1)
            {
                e1.printStackTrace();
            }
            int z;
            try
            {
                while ((z = fr.read()) != -1)
                {
                    y[count] = Character.getNumericValue((char) z);
                    count++;
                }
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            try
            {
                fr.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            x=Arrays.copyOf(y, y.length);
        }
        //for(int k=0;k<12;k++)
        //    System.out.print(y[k]);

        buttonPanel.setLayout(new GridLayout(2, 3));
        for (int i = 0; i < 6; i++)
        {
            if(y[i]==1)
            {
                buttonArray[i] = new JButton("booked");
                buttonArray[i].setBackground(Color.red);
                buttonArray[i].setActionCommand("booked");
                seatCounter++;
            }
            else
            {
                if (i < 3)
                {
                    buttonArray[i] = new JButton("Seat " + (i + 1) + " W");
                    buttonArray[i].setActionCommand("unbooked" + (i + 1) + "W");
                }
                else 
                {
                    buttonArray[i] = new JButton("Seat " + (i + 1) + " A");
                    buttonArray[i].setActionCommand("unbooked" + (i + 1) + "A");
                }
            }
            buttonArray[i].addActionListener(this);
            buttonPanel.add(buttonArray[i]);
        }

        aisle.setLayout(new BorderLayout());
        JButton b2 = new JButton("CONTINUE");
        b2.setPreferredSize(new Dimension(100,50));
        b2.setActionCommand("Add action listener here");
        aisle.add(b2,BorderLayout.CENTER);
        b2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if(booked>0)
                {
                    JFrame J=new JFrame();
                    J.setLayout(null);
                    J.getContentPane().setBackground(Color.LIGHT_GRAY);
                    J.setSize(500,500);
                    JLabel a1,a2,a3,a4,a5,a6;
                    JTextField x1,x3,x2,x4;
                    JButton b1 = new JButton("Continue");
                    
                    a6=new JLabel("Passenger Details");
                    a1=new JLabel("Name :");
                    a2=new JLabel("Age :");
                    a3=new JLabel("Gender");
                    a4=new JLabel("Email :");
                    a5=new JLabel("Contact No. :");

                    x1=new JTextField("");
                    x2=new JTextField("");
                    x3=new JTextField("");
                    x4=new JTextField("");
                    
                    
                    a6.setBounds(60, 20, 150, 20);
                    a1.setBounds(20, 50, 100, 20);
                    a2.setBounds(20, 80, 100, 20);
                    a3.setBounds(20, 110, 100, 20);
                    a4.setBounds(20, 140, 100, 20);
                    a5.setBounds(20, 170, 100, 20);

                    x1.setBounds(170, 50, 100, 20);
                    x2.setBounds(170, 80, 100, 20);
                    x3.setBounds(170, 140, 100, 20);
                    x4.setBounds(170, 170, 100, 20);

                    String[] gender={"Male","Female","Other"};
                    JComboBox x5=new JComboBox(gender);
                    x5.setBounds(170, 110, 100, 20);
                    
                    b1.setBounds(170, 250, 100, 20);
        
                    J.add(a6);
                    J.add(a1);
                    J.add(a2);
                    J.add(a3);
                    J.add(a4);
                    J.add(a5);
                    J.add(x1);
                    J.add(x2);
                    J.add(x3);
                    J.add(x4);
                    J.add(x5);
                    J.add(b1);
                    
                    x2.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent ke) {
                       if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                          x2.setEditable(true);
                        } else {
                          x2.setEditable(false);
                          
                        }
                        }
                     });
                    x4.addKeyListener(new KeyAdapter() {
                        public void keyPressed(KeyEvent ke) {
                           if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                              x4.setEditable(true);
                           } else {
                              x4.setEditable(false);

                           }
                        }
                     });
                   
                    b1.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            
                            String s="";
                            
                            for(int i=0;i<12;i++)
                            {
                                int g=i+1;
                                if(Project.seats[i]==1)
                                {
                                    if(i<3 || i>8)
                                    {
                                        s+=(g+'\0')+"-W ";
                                    }
                                    else
                                    {
                                        s+=(g+'\0')+"-A ";
                                    }
                                }
                            }
                            JFrame J=new JFrame();
                            J.setLayout(null);
                            J.getContentPane().setBackground(Color.green);
                            J.setSize(500,700);

                            JLabel z1,z2,z3,z4,z5,z6,z7,z8,z9,z10,z11,z12;
                            JLabel y1,y2,y3,y4,y5,y6,y7,y8,y9,y10,y11;

                            z9=new JLabel("BOOKING DETAILS");                    
                            z1=new JLabel("PNR");
                            z2=new JLabel("Name");
                            z3=new JLabel("Source");
                            z4=new JLabel("Destination");
                            z5=new JLabel("Journey Date");
                            z6=new JLabel("Boarding Time");
                            z7=new JLabel("Fare");
                            z8=new JLabel("Bus_Name");
                            z10=new JLabel("Bus_type");
                            z11=new JLabel("Seats");
                            z12=new JLabel("Status");

                            Random rd= new Random();
                            int x=rd.nextInt(5000);
                            String PNR="PQR"+(x+'\0');
                            y1=new JLabel(PNR);
                            y2=new JLabel(x1.getText());
                            y3=new JLabel(Project.sel_source);
                            y4=new JLabel(Project.sel_destination);
                            y5=new JLabel(""+Project.sel_date);
                            y6=new JLabel(""+Project.journey_time);
                            y7=new JLabel(""+Project.fare);
                            y8=new JLabel(Project.sel_bus);
                            y9=new JLabel(Project.sel_type);
                            y10=new JLabel(s);
                            y11=new JLabel("Confirmed");

                            z9.setBounds(40,30,150,20);
                            z1.setBounds(20,80,100,20);
                            z2.setBounds(20,120,100,20);
                            z3.setBounds(20,160,100,20);
                            z4.setBounds(20,200,100,20);
                            z5.setBounds(20,240,100,20);
                            z6.setBounds(20,280,100,20);
                            z7.setBounds(20,320,100,20);
                            z8.setBounds(20,360,100,20);
                            z10.setBounds(20,400,100,20);
                            z11.setBounds(20,440,100,20);
                            z12.setBounds(20,480,100,20);

                            y1.setBounds(150,80,100,20);
                            y2.setBounds(150,120,100,20);
                            y3.setBounds(150,160,100,20);
                            y4.setBounds(150,200,100,20);
                            y5.setBounds(150,240,80,20);
                            y6.setBounds(150,280,100,20);
                            y7.setBounds(150,320,100,20);
                            y8.setBounds(150,360,150,20);
                            y9.setBounds(150,400,100,20);
                            y10.setBounds(150,440,150,20);
                            y11.setBounds(150,480,100,20);

                            J.add(z1);
                            J.add(z2);
                            J.add(z3);
                            J.add(z4);
                            J.add(z5);
                            J.add(z6);
                            J.add(z7);
                            J.add(z8);
                            J.add(z9);
                            J.add(y1);
                            J.add(y2);
                            J.add(y3);
                            J.add(y4);
                            J.add(y5);
                            J.add(y6);
                            J.add(y7);
                            J.add(y8);
                            J.add(y9);
                            J.add(y10);
                            J.add(y11);
                            J.add(z10);
                            J.add(z11);
                            J.add(z12);

                            s="";

                            for(int i=0;i<12;i++)
                            {
                                s+=Project.seats[i];
                            }

                            new TicketInfo(PNR,x1.getText(),Project.sel_source,Project.sel_destination,Project.sel_date,Project.journey_time,(Project.fare*booked),"Confirmed",Project.sel_bus,s,Project.sel_type,Project.curr_user);
                            J.setVisible(true);
                            setDefaultCloseOperation(EXIT_ON_CLOSE);
                        }
                    });

                    J.setVisible(true);
                }
            }
        });

        buttonPanel1.setLayout(new GridLayout(2, 3));
        for (int i = 6; i < 12; i++)
        {
            if(y[i]==1)
            {
                buttonArray[i] = new JButton("booked");
                buttonArray[i].setBackground(Color.red);
                buttonArray[i].setActionCommand("booked");
                seatCounter++;
            }
            else
            {
                if (i < 9)
                {
                    buttonArray[i] = new JButton("Seat " + (i + 1) + " A");
                    buttonArray[i].setActionCommand("unbooked" + (i + 1) + "A");
                }
                else
                {
                    buttonArray[i] = new JButton("Seat " + (i + 1) + " W");
                    buttonArray[i].setActionCommand("unbooked" + (i + 1) + "W");
                }
            }
            buttonArray[i].addActionListener(this);
            buttonPanel1.add(buttonArray[i]);
        }
    
        this.add(buttonPanel);
        this.add(aisle);
        this.add(buttonPanel1);
        this.setVisible(true);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public void actionPerformed(ActionEvent e) {
        String confirmSeat = "Are you sure you want to book this seat?";
        String seatBooked = "Unbook seat?  Click YES to unbook, or NO to " + "choose another seat.";
        
        if (seatCounter <12) {
            for(int i = 0; i < 12; i++)
            {
                if (e.getActionCommand().equals("unbooked"+(i + 1)+"W") || e.getActionCommand().equals("unbooked"+(i + 1)+"A"))
                {
                    int choice = JOptionPane.showConfirmDialog(null, confirmSeat,"Confirm Seat Selection",JOptionPane.YES_NO_OPTION);
                    if(choice == JOptionPane.YES_OPTION)
                    {
                        JButton b = (JButton)e.getSource();
                        b.setText("reserved");
                        b.setForeground(Color.red);
                        String l = b.getActionCommand();
                        int p = l.length();
                        char ch = getCharFromString(l, p-2);
                        char u = getCharFromString(l, p-1);
                        if(i>8)
                        {
                            if(u=='W')
                                b.setActionCommand("booked1"+ch+"W");
                            else
                                b.setActionCommand("booked1"+ch+"A");
                        }
                        else
                        {
                            if(u=='W')
                                b.setActionCommand("booked"+ch+"W");
                            else
                                b.setActionCommand("booked"+ch+"A");
                        }
                        seatCounter++;
                        booked++;
                        x[i]=1;
                        Project.seats[i]=1;
                    }
                }
                writeToFile();
                exist=true;
            }
            
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Sorry the bus is booked !!");
        }
        
        for(int i = 0; i < 12; i++)
        {
            boolean check = false;
            if((e.getActionCommand().equals("booked"+(i+1)+"W") || e.getActionCommand().equals("booked"+(i+1)+"A")) && check==false)
            {
                int choice = JOptionPane.showConfirmDialog(null, seatBooked, "Seat " + "Already Booked", JOptionPane.YES_NO_OPTION);
                if(choice == JOptionPane.YES_OPTION)
                {
                    JButton b = (JButton)e.getSource();
                    String l = b.getActionCommand();
                    int p = l.length();
                    char ch = getCharFromString(l, p-2);
                    char u = getCharFromString(l, p-1);
                    if(i>8)
                    {
                        if(u=='W')
                        {   
                            b.setActionCommand("unbooked1"+ch+"W");
                            b.setText("Seat 1"+ch+" W");
                        }
                        else
                        {
                            b.setActionCommand("unbooked1"+ch+"A");
                            b.setText("Seat 1"+ch+" A");
                        }
                    }
                    else
                    {
                        if(u=='W')
                        {   
                            b.setActionCommand("unbooked"+ch+"W");
                            b.setText("Seat "+ch+" W");
                        }
                        else
                        {
                            b.setActionCommand("unbooked"+ch+"A");
                            b.setText("Seat "+ch+" A");
                        }
                    }
                    b.setForeground(Color.BLACK);
                    seatCounter--;
                    booked--;
                    check=true;
                    Project.seats[i]=0;
                    x[i]=0;
                }
            }
            writeToFile();
            exist=true;
        }
        
    }
    public static char getCharFromString(String str, int index) 
    { 
        return (char)str.codePointAt(index); 
    }
    public void writeToFile()
    {
        PrintWriter outputStream = null;
        try
        {
          outputStream = new PrintWriter(""+d+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error opening the file " + ""+d+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
            System.exit(0);
        }
        for (int count = 0; count <12; count++)
        {
            outputStream.print(x[count]);
        }
        outputStream.close( );
    }
    
}

class semisleeper extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    static int seatCounter1 = 0;
    int[] x = new int[16];
    int[] y = new int[16];
    String d;
    boolean exist=false;
    int booked=0;
    JButton[] buttonArray = new JButton[10];
    JButton[] buttonArray1 = new JButton[6];

    public semisleeper(String p)
    {
        d=p;
        Project.sel_bus=p;
        getContentPane().setLayout(new GridLayout(5,1));
        setMinimumSize(new Dimension(500,500));

        JPanel buttonPanel = new JPanel();
        JPanel buttonPanel1 = new JPanel();
        JPanel buttonPanel2 = new JPanel();
        JPanel buttonPanel3 = new JPanel();
        JPanel aisle = new JPanel();

        File f1 = new File(""+p+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
        if (f1.exists())
        {
            int count = 0;
            FileReader fr = null;
            try
            {
                fr = new FileReader(""+p+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
            }
            catch (FileNotFoundException e1)
            {
                e1.printStackTrace();
            }
            int z;
            try
            {
                while ((z = fr.read()) != -1)
                {
                    y[count] = Character.getNumericValue((char) z);
                    count++;
                }
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            try
            {
                fr.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            x=Arrays.copyOf(y, y.length);
        }
        
        buttonPanel2.setLayout(new GridLayout(1,3));
        for (int i=0;i<3;i++)
        {
            if(y[i]==1)
            {
                buttonArray1[i] = new JButton("booked");
                buttonArray1[i].setBackground(Color.red);
                buttonArray1[i].setActionCommand("booked");
                seatCounter1++;
            }
            else
            {
                buttonArray1[i] = new JButton("Seat " + (i+1)+ " U");
                buttonArray1[i].setActionCommand("unbooked"+(i+1)+"U");
            }
            buttonArray1[i].addActionListener(this);
            buttonPanel2.add(buttonArray1[i]);
        }

        buttonPanel3.setLayout(new GridLayout(1,3));
        for (int i=3;i<6;i++)
        {
            if(y[i]==1)
            {
                buttonArray1[i] = new JButton("booked");
                buttonArray1[i].setBackground(Color.red);
                buttonArray1[i].setActionCommand("booked");
                seatCounter1++;
            }
            else
            {
                buttonArray1[i] = new JButton("Seat " + (i+1)+ " U");
                buttonArray1[i].setActionCommand("unbooked"+(i+1)+"U");
            }
            buttonArray1[i].addActionListener(this);
            buttonPanel3.add(buttonArray1[i]);
        }

        buttonPanel.setLayout(new GridLayout(1,5));
        for (int i=0;i<5;i++)
        {
            if(y[i+6]==1)
            {
                buttonArray[i] = new JButton("booked");
                buttonArray[i].setBackground(Color.red);
                buttonArray[i].setActionCommand("booked");
                seatCounter1++;
            }
            else
            {
                buttonArray[i] = new JButton("Seat " + (i+1)+ " L");
                buttonArray[i].setActionCommand("unbooked"+(i+1)+"L");
            }
            buttonArray[i].addActionListener(this);
            buttonPanel.add(buttonArray[i]);
        }

        buttonPanel1.setLayout(new GridLayout(1,5));
        for (int i=5;i<10;i++)
        {
            if(y[i+6]==1)
            {
                buttonArray[i] = new JButton("booked");
                buttonArray[i].setBackground(Color.red);
                buttonArray[i].setActionCommand("booked");
                seatCounter1++;
            }
            else
            {
                buttonArray[i] = new JButton("Seat " + (i+1)+ " L");
                buttonArray[i].setActionCommand("unbooked"+(i+1)+"L");    
            }
            buttonArray[i].addActionListener(this);
            buttonPanel1.add(buttonArray[i]);
        }

        aisle.setLayout(new BorderLayout());
        JButton b2 = new JButton("CONTINUE");
        b2.setPreferredSize(new Dimension(100,50));
        b2.setActionCommand("booked");
        aisle.add(b2,BorderLayout.CENTER);
        b2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if(booked>0)
                {
                    JFrame J=new JFrame();
                    J.setLayout(null);
                    J.getContentPane().setBackground(Color.LIGHT_GRAY);
                    J.setSize(500,500);
                    JLabel a1,a2,a3,a4,a5,a6;
                    JTextField x1,x3,x2,x4;
                    JButton b1 = new JButton("Continue");
                    a1=new JLabel("Name :");
                    a2=new JLabel("Age :");
                    a3=new JLabel("Gender");
                    a4=new JLabel("Email :");
                    a5=new JLabel("Contact No. :");
                    a6=new JLabel("Passenger Details");

                    x1=new JTextField("");
                    x2=new JTextField("");                    
                    x3=new JTextField("");
                    x4=new JFormattedTextField("");                   
                    
                    a6.setBounds(60, 20, 150, 20);
                    a1.setBounds(20, 50, 100, 20);
                    a2.setBounds(20, 80, 100, 20);
                    a3.setBounds(20, 110, 100, 20);
                    a4.setBounds(20, 140, 100, 20);
                    a5.setBounds(20, 170, 100, 20);

                    x1.setBounds(170, 50, 100, 20);
                    x2.setBounds(170, 80, 100, 20);
                    x3.setBounds(170, 140, 100, 20);
                    x4.setBounds(170, 170, 100, 20);

                    String[] gender={"Male","Female","Other"};
                    JComboBox x5=new JComboBox(gender);
                    x5.setBounds(170, 110, 100, 20);
                    
                    b1.setBounds(170, 250, 100, 20);
        
                    J.add(a6);
                    J.add(a1);
                    J.add(a2);
                    J.add(a3);
                    J.add(a4);
                    J.add(a5);
                    J.add(x1);
                    J.add(x2);
                    J.add(x3);
                    J.add(x4);
                    J.add(x5);
                    J.add(b1);
                    
                    x2.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent ke) {
                       if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                          x2.setEditable(true);
                        } else {
                          x2.setEditable(false);
                          
                        }
                        }
                     });
                    x4.addKeyListener(new KeyAdapter() {
                        public void keyPressed(KeyEvent ke) {
                           if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                              x4.setEditable(true);
                           } else {
                              x4.setEditable(false);

                           }
                        }
                     });
                    
                    b1.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            String s="";
                            
                            for(int i=0;i<12;i++)
                            {
                                int g=i+1;
                                if(Project.seats[i]==1)
                                {
                                    if(i<6)
                                    {
                                        s+=(g+'\0')+"-U ";
                                    }
                                    else
                                    {
                                        s+=(g+'\0')+"-L ";
                                    }
                                }
                            }
                            JFrame J=new JFrame();
                            J.setLayout(null);
                            J.getContentPane().setBackground(Color.green);
                            J.setSize(500,700);

                            JLabel z1,z2,z3,z4,z5,z6,z7,z8,z9,z10,z11,z12;
                            JLabel y1,y2,y3,y4,y5,y6,y7,y8,y9,y10,y11;

                            z9=new JLabel("BOOKING DETAILS");                    
                            z1=new JLabel("PNR");
                            z2=new JLabel("Name");
                            z3=new JLabel("Source");
                            z4=new JLabel("Destination");
                            z5=new JLabel("Journey Date");
                            z6=new JLabel("Boarding Time");
                            z7=new JLabel("Fare");
                            z8=new JLabel("Bus_Name");
                            z10=new JLabel("Bus_type");
                            z11=new JLabel("Seats");
                            z12=new JLabel("Status");

                            Random rd= new Random();
                            int x=rd.nextInt(5000);
                            String PNR="PQR"+(x+'\0');
                            y1=new JLabel(PNR);
                            y2=new JLabel(x1.getText());
                            y3=new JLabel(Project.sel_source);
                            y4=new JLabel(Project.sel_destination);
                            y5=new JLabel(""+Project.sel_date);
                            y6=new JLabel(""+Project.journey_time);
                            y7=new JLabel(""+Project.fare);
                            y8=new JLabel(Project.sel_bus);
                            y9=new JLabel(Project.sel_type);
                            y10=new JLabel(s);
                            y11=new JLabel("Confirmed");

                            z9.setBounds(40,30,150,20);
                            z1.setBounds(20,80,100,20);
                            z2.setBounds(20,120,100,20);
                            z3.setBounds(20,160,100,20);
                            z4.setBounds(20,200,100,20);
                            z5.setBounds(20,240,100,20);
                            z6.setBounds(20,280,100,20);
                            z7.setBounds(20,320,100,20);
                            z8.setBounds(20,360,100,20);
                            z10.setBounds(20,400,100,20);
                            z11.setBounds(20,440,100,20);
                            z12.setBounds(20,480,100,20);

                            y1.setBounds(150,80,100,20);
                            y2.setBounds(150,120,100,20);
                            y3.setBounds(150,160,100,20);
                            y4.setBounds(150,200,100,20);
                            y5.setBounds(150,240,80,20);
                            y6.setBounds(150,280,100,20);
                            y7.setBounds(150,320,100,20);
                            y8.setBounds(150,360,150,20);
                            y9.setBounds(150,400,100,20);
                            y10.setBounds(150,440,150,20);
                            y11.setBounds(150,480,100,20);

                            J.add(z1);
                            J.add(z2);
                            J.add(z3);
                            J.add(z4);
                            J.add(z5);
                            J.add(z6);
                            J.add(z7);
                            J.add(z8);
                            J.add(z9);
                            J.add(y1);
                            J.add(y2);
                            J.add(y3);
                            J.add(y4);
                            J.add(y5);
                            J.add(y6);
                            J.add(y7);
                            J.add(y8);
                            J.add(y9);
                            J.add(y10);
                            J.add(y11);
                            J.add(z10);
                            J.add(z11);
                            J.add(z12);

                            s="";

                            for(int i=0;i<16;i++)
                            {
                                s+=Project.seats[i];
                            }

                            new TicketInfo(PNR,x1.getText(),Project.sel_source,Project.sel_destination,Project.sel_date,Project.journey_time,(Project.fare*booked),"Confirmed",Project.sel_bus,s,Project.sel_type,Project.curr_user);
                            J.setVisible(true);
                            setDefaultCloseOperation(EXIT_ON_CLOSE);
                        }
                    });

                    J.setVisible(true);
                }
            }
        });

        this.add(buttonPanel2);
        this.add(buttonPanel);
        this.add(aisle);
        this.add(buttonPanel1);
        this.add(buttonPanel3);
        this.setVisible(true);
    }
 
    public void actionPerformed(ActionEvent e)
    {
        String confirmSeat = "Are you sure you want to book this seat?";
        String seatBooked = "Unbook seat?  Click YES to unbook, or NO to " + "choose another seat.";
        
        if(seatCounter1<16)
        {
            for(int i = 0; i < 6; i++)
            {
                if (e.getActionCommand().equals("unbooked"+(i + 1)+"U"))
                {
                    int choice = JOptionPane.showConfirmDialog(null, confirmSeat,"Confirm Seat Selection",JOptionPane.YES_NO_OPTION);
                    if(choice == JOptionPane.YES_OPTION)
                    {
                        JButton b = (JButton)e.getSource();
                        b.setText("reserved");
                        b.setForeground(Color.red);
                        String l = b.getActionCommand();
                        int p = l.length();
                        char ch = getCharFromString(l, p-2);
                        //char u = getCharFromString(l, p-1);
                        //if(u=='U')
                        //    b.setActionCommand("booked"+ch+"U");
                        //else
                            b.setActionCommand("booked"+ch+"U");
                        seatCounter1++;
                        x[i]=1;
                        Project.seats[i]=1;
                        booked++; 
                    }
                }
                writeToFile();
                exist=true;
            }

            for(int i = 0; i < 10; i++)
            {
                if (e.getActionCommand().equals("unbooked"+(i + 1)+"L"))
                {
                    int choice = JOptionPane.showConfirmDialog(null, confirmSeat,"Confirm Seat Selection",JOptionPane.YES_NO_OPTION);
                    if(choice == JOptionPane.YES_OPTION)
                    {
                        JButton b = (JButton)e.getSource();
                        b.setText("reserved");
                        b.setForeground(Color.red);
                        String l = b.getActionCommand();
                        int p = l.length();
                        char ch = getCharFromString(l, p-2);
                        //char u = getCharFromString(l, p-1);
                        if(i==9)
                            b.setActionCommand("booked1"+ch+"L");
                        else
                            b.setActionCommand("booked"+ch+"L");
                        seatCounter1++;
                        x[i+6]=1;
                        Project.seats[i+6]=1;
                        booked++;
                    }
                }
                writeToFile();
                exist=true;
            }
        }

        for(int i = 0; i < 6; i++)
        {
            boolean check = false;
            if((e.getActionCommand().equals("booked"+(i+1)+"U")) && check==false)
            {
                int choice = JOptionPane.showConfirmDialog(null, seatBooked, "Seat " + "Already Booked", JOptionPane.YES_NO_OPTION);
                if(choice == JOptionPane.YES_OPTION)
                {
                    JButton b = (JButton)e.getSource();
                    String l = b.getActionCommand();
                    int p = l.length();
                    char ch = getCharFromString(l, p-2);
                    //char u = getCharFromString(l, p-1);
                    b.setActionCommand("unbooked1"+ch+"U");
                    b.setText("Seat 1"+ch+" U");
                    b.setForeground(Color.BLACK);
                    check=true;
                    seatCounter1--;
                    x[i]=0;
                    Project.seats[i]=0;
                    booked--;
                }
            }
            writeToFile();
            exist=true;
        }
        for(int i = 0; i < 10; i++)
        {
            boolean check = false;
            if(e.getActionCommand().equals("booked"+(i+1)+"L") && check==false)
            {
                int choice = JOptionPane.showConfirmDialog(null, seatBooked, "Seat " + "Already Booked", JOptionPane.YES_NO_OPTION);
                if(choice == JOptionPane.YES_OPTION)
                {
                    JButton b = (JButton)e.getSource();
                    String l = b.getActionCommand();
                    int p = l.length();
                    char ch = getCharFromString(l, p-2);
                    //char u = getCharFromString(l, p-1);
                    if(i==9)
                    {
                        b.setActionCommand("unbooked1"+ch+"L");
                        b.setText("Seat 1"+ch+" L");
                    }
                    else
                    {
                        b.setActionCommand("unbooked"+ch+"L");
                        b.setText("Seat "+ch+" L");
                    }
                    b.setForeground(Color.BLACK);
                    check=true;
                    seatCounter1--;
                    x[i+6]=0;
                    Project.seats[i+6]=0;
                    booked--;
                }
            }
            writeToFile();
            exist=true;
        }
        
    }
    public static char getCharFromString(String str, int index) 
    { 
        return (char)str.codePointAt(index); 
    }
    public void writeToFile()
    {
        PrintWriter outputStream = null;
        try
        {
          outputStream = new PrintWriter(""+d+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error opening the file " + ""+d+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
            System.exit(0);
        }
        for (int count = 0; count <12; count++)
        {
            outputStream.print(x[count]);
        }
        outputStream.close( );
    }
}

class sleeper extends JFrame implements ActionListener
{
    private static final long serialVersionUID = 1L;
    static int seatCounter2 = 0;
    int[] x = new int[12];
    int[] y = new int[12];
    boolean exist = false;
    String d;
    int booked=0;
    JButton[] buttonArray = new JButton[12];

    public sleeper(String p)
    {
        d=p;
        Project.sel_bus=p;
        getContentPane().setLayout(new GridLayout(3,1));
        setMinimumSize(new Dimension(500,500));
        JPanel buttonPanel = new JPanel();
        JPanel buttonPanel1 = new JPanel();
        JPanel aisle = new JPanel();
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        File f1 = new File(""+p+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
        if (f1.exists())
        {
            int count = 0;
            FileReader fr = null;
            try
            {
                fr = new FileReader(""+p+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
            }
            catch (FileNotFoundException e1)
            {
                e1.printStackTrace();
            }
            int z;
            try
            {
                while ((z = fr.read()) != -1)
                {
                    y[count] = Character.getNumericValue((char) z);
                    count++;
                }
            }
            catch (IOException e1)
            {
                e1.printStackTrace();
            }
            try
            {
                fr.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            x=Arrays.copyOf(y, y.length);
        }

        buttonPanel.setLayout(new GridLayout(2,3));
        for (int i=0;i<6;i++)
        {
            if(y[i]==1)
            {
                buttonArray[i] = new JButton("booked");
                buttonArray[i].setBackground(Color.red);
                buttonArray[i].setActionCommand("booked");
                seatCounter2++;
            }
            else
            {
                if(i<3)
                {
                    buttonArray[i] = new JButton("Seat " + (i+1)+ " U");
                    buttonArray[i].setActionCommand("unbooked"+(i+1)+"U");
                }
                else
                {
                    buttonArray[i] = new JButton("Seat " + (i+1)+ " L");
                    buttonArray[i].setActionCommand("unbooked"+(i+1)+"L");
                }
            }
            buttonArray[i].addActionListener(this);
            buttonPanel.add(buttonArray[i]);
        }

        aisle.setLayout(new BorderLayout());
        JButton b2 = new JButton("CONTINUE");
        b2.setPreferredSize(new Dimension(100,50));
        b2.setActionCommand("Add action listener here");
        aisle.add(b2,BorderLayout.CENTER);
        b2.addActionListener(new ActionListener()
        {
            public void actionPerformed(ActionEvent ae)
            {
                if(booked>0)
                {
                    JFrame J=new JFrame();
                    J.setLayout(null);
                    J.getContentPane().setBackground(Color.LIGHT_GRAY);
                    J.setSize(500,500);
                    JLabel a1,a2,a3,a4,a5,a6;
                    JTextField x1,x3,x2,x4;
                    JButton b1 = new JButton("Continue");
                    a6=new JLabel("Passenger Details");
                    a1=new JLabel("Name :");
                    a2=new JLabel("Age :");
                    a3=new JLabel("Gender");
                    a4=new JLabel("Email :");
                    a5=new JLabel("Contact No. :");

                    x1=new JTextField("");
                    x2=new JTextField("");
                    x3=new JTextField("");
                    x4=new JTextField("");
                    
                    a6.setBounds(60, 20, 150, 20);
                    a1.setBounds(20, 50, 100, 20);
                    a2.setBounds(20, 80, 100, 20);
                    a3.setBounds(20, 110, 100, 20);
                    a4.setBounds(20, 140, 100, 20);
                    a5.setBounds(20, 170, 100, 20);

                    x1.setBounds(170, 50, 100, 20);
                    x2.setBounds(170, 80, 100, 20);
                    x3.setBounds(170, 140, 100, 20);
                    x4.setBounds(170, 170, 100, 20);

                    String[] gender={"Male","Female","Other"};
                    JComboBox x5=new JComboBox(gender);
                    x5.setBounds(170, 110, 100, 20);
                    
                    b1.setBounds(170, 250, 100, 20);
        
                    J.add(a6);
                    J.add(a1);
                    J.add(a2);
                    J.add(a3);
                    J.add(a4);
                    J.add(a5);
                    J.add(x1);
                    J.add(x2);
                    J.add(x3);
                    J.add(x4);
                    J.add(x5);
                    J.add(b1);
                    
                    x2.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent ke) {                      
                       if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                          x2.setEditable(true);
                        } else {
                          x2.setEditable(false);
                          
                        }
                        }
                     });
                    x4.addKeyListener(new KeyAdapter() {
                        public void keyPressed(KeyEvent ke) {
                           if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                              x4.setEditable(true);
                           } else {
                              x4.setEditable(false);

                           }
                        }
                     });
                    
                    b1.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            String s="";
                            
                            for(int i=0;i<12;i++)
                            {
                                int g=i+1;
                                if(Project.seats[i]==1)
                                {
                                    if(i>=0 && i<3)
                                    {
                                        s+=(g+'\0')+"-U ";
                                    }
                                    else if(i>=3 && i<6)
                                    {
                                        s+=(g+'\0')+"-L ";
                                    }
                                    else if(i>=6 && i<9)
                                    {
                                        s+=(g+'\0')+"-U ";
                                    }
                                    else
                                    {
                                        s+=(g+'\0')+"-L ";
                                    }
                                }
                            }
                            JFrame J=new JFrame();
                            J.setLayout(null);
                            J.getContentPane().setBackground(Color.green);
                            J.setSize(500,700);

                            JLabel z1,z2,z3,z4,z5,z6,z7,z8,z9,z10,z11,z12;
                            JLabel y1,y2,y3,y4,y5,y6,y7,y8,y9,y10,y11;

                            z9=new JLabel("BOOKING DETAILS");                    
                            z1=new JLabel("PNR");
                            z2=new JLabel("Name");
                            z3=new JLabel("Source");
                            z4=new JLabel("Destination");
                            z5=new JLabel("Journey Date");
                            z6=new JLabel("Boarding Time");
                            z7=new JLabel("Fare");
                            z8=new JLabel("Bus_Name");
                            z10=new JLabel("Bus_type");
                            z11=new JLabel("Seats");
                            z12=new JLabel("Status");

                            Random rd= new Random();
                            int x=rd.nextInt(5000);
                            String PNR="PQR"+(x+'\0');
                            y1=new JLabel(PNR);
                            y2=new JLabel(x1.getText());
                            y3=new JLabel(Project.sel_source);
                            y4=new JLabel(Project.sel_destination);
                            y5=new JLabel(""+Project.sel_date);
                            y6=new JLabel(""+Project.journey_time);
                            y7=new JLabel(""+Project.fare);
                            y8=new JLabel(Project.sel_bus);
                            y9=new JLabel(Project.sel_type);
                            y10=new JLabel(s);
                            y11=new JLabel("Confirmed");

                            z9.setBounds(40,30,150,20);
                            z1.setBounds(20,80,100,20);
                            z2.setBounds(20,120,100,20);
                            z3.setBounds(20,160,100,20);
                            z4.setBounds(20,200,100,20);
                            z5.setBounds(20,240,100,20);
                            z6.setBounds(20,280,100,20);
                            z7.setBounds(20,320,100,20);
                            z8.setBounds(20,360,100,20);
                            z10.setBounds(20,400,100,20);
                            z11.setBounds(20,440,100,20);
                            z12.setBounds(20,480,100,20);

                            y1.setBounds(150,80,100,20);
                            y2.setBounds(150,120,100,20);
                            y3.setBounds(150,160,100,20);
                            y4.setBounds(150,200,100,20);
                            y5.setBounds(150,240,80,20);
                            y6.setBounds(150,280,100,20);
                            y7.setBounds(150,320,100,20);
                            y8.setBounds(150,360,150,20);
                            y9.setBounds(150,400,100,20);
                            y10.setBounds(150,440,150,20);
                            y11.setBounds(150,480,100,20);

                            J.add(z1);
                            J.add(z2);
                            J.add(z3);
                            J.add(z4);
                            J.add(z5);
                            J.add(z6);
                            J.add(z7);
                            J.add(z8);
                            J.add(z9);
                            J.add(y1);
                            J.add(y2);
                            J.add(y3);
                            J.add(y4);
                            J.add(y5);
                            J.add(y6);
                            J.add(y7);
                            J.add(y8);
                            J.add(y9);
                            J.add(y10);
                            J.add(y11);
                            J.add(z10);
                            J.add(z11);
                            J.add(z12);

                            s="";

                            for(int i=0;i<12;i++)
                            {
                                s+=Project.seats[i];
                            }

                            new TicketInfo(PNR,x1.getText(),Project.sel_source,Project.sel_destination,Project.sel_date,Project.journey_time,(Project.fare*booked),"Confirmed",Project.sel_bus,s,Project.sel_type,Project.curr_user);
                            J.setVisible(true);
                            setDefaultCloseOperation(EXIT_ON_CLOSE);
                        }
                    });

                    J.setVisible(true);
                }
            }
        });

        buttonPanel1.setLayout(new GridLayout(2,3));
        for (int i=6;i<12;i++)
        {
            if(y[i]==1)
            {
                buttonArray[i] = new JButton("booked");
                buttonArray[i].setBackground(Color.red);
                buttonArray[i].setActionCommand("booked");
                seatCounter2++;
            }
            else
            {
                if(i<9)
                {
                    buttonArray[i] = new JButton("Seat " + (i+1)+ " U");
                    buttonArray[i].setActionCommand("unbooked"+(i+1)+"U");
                }    
                else
                {
                    buttonArray[i] = new JButton("Seat " + (i+1)+ " L");
                    buttonArray[i].setActionCommand("unbooked"+(i+1)+"L");
                }
            }
            buttonArray[i].addActionListener(this);
            buttonPanel1.add(buttonArray[i]);
        }

        this.add(buttonPanel);
        this.add(aisle);
        this.add(buttonPanel1);
        this.setVisible(true);
    }
 
    public void actionPerformed(ActionEvent e)
    {
        String confirmSeat = "Are you sure you want to book this seat?";
        String seatBooked = "Unbook seat?  Click YES to unbook, or NO to " + "choose another seat.";
        
        if(seatCounter2<12)
        {
            for(int i = 0; i < 12; i++)
            {
                if (e.getActionCommand().equals("unbooked"+(i + 1)+"U") || e.getActionCommand().equals("unbooked"+(i + 1)+"L"))
                {
                    int choice = JOptionPane.showConfirmDialog(null, confirmSeat,"Confirm Seat Selection",JOptionPane.YES_NO_OPTION);
                    if(choice == JOptionPane.YES_OPTION)
                    {
                        JButton b = (JButton)e.getSource();
                        b.setText("reserved");
                        b.setForeground(Color.red);
                        String l = b.getActionCommand();
                        int p = l.length();
                        char ch = getCharFromString(l, p-2);
                        char u = getCharFromString(l, p-1);
                        if(i>8)
                        {
                            if(u=='U')
                                b.setActionCommand("booked1"+ch+"U");
                            else
                                b.setActionCommand("booked1"+ch+"L");
                        }
                        else
                        {
                            if(u=='U')
                                b.setActionCommand("booked"+ch+"U");
                            else
                                b.setActionCommand("booked"+ch+"L");
                        }
                        seatCounter2++;
                        booked++;
                        x[i]=1;
                        Project.seats[i]=1;
                    }
                }
                writeToFile();
                exist=true;
            }
        }
               

        for(int i = 0; i < 12; i++)
        {
            boolean check = false;
            if((e.getActionCommand().equals("booked"+(i+1)+"U") || e.getActionCommand().equals("booked"+(i+1)+"L")) && check==false)
            {
                int choice = JOptionPane.showConfirmDialog(null, seatBooked, "Seat " + "Already Booked", JOptionPane.YES_NO_OPTION);
                if(choice == JOptionPane.YES_OPTION)
                {
                    JButton b = (JButton)e.getSource();
                    String l = b.getActionCommand();
                    int p = l.length();
                    char ch = getCharFromString(l, p-2);
                    char u = getCharFromString(l, p-1);
                    if(i>8)
                    {
                        if(u=='W')
                        {   
                            b.setActionCommand("unbooked1"+ch+"U");
                            b.setText("Seat 1"+ch+" U");
                        }
                        else
                        {
                            b.setActionCommand("unbooked1"+ch+"L");
                            b.setText("Seat 1"+ch+" L");
                        }
                    }
                    else
                    {
                        if(u=='U')
                        {   
                            b.setActionCommand("unbooked"+ch+"U");
                            b.setText("Seat "+ch+" U");
                        }
                        else
                        {
                            b.setActionCommand("unbooked"+ch+"L");
                            b.setText("Seat "+ch+" L");
                        }
                    }
                    b.setForeground(Color.BLACK);
                    seatCounter2--;
                    booked--;
                    x[i]=0;
                    Project.seats[i]=0;
                    check=true;
                }
            }
            writeToFile();
            exist=true;
        }
        
    }
    public static char getCharFromString(String str, int index) 
    { 
        return (char)str.codePointAt(index); 
    }
    public void writeToFile()
    {
        PrintWriter outputStream = null;
        try
        {
          outputStream = new PrintWriter(""+d+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
        }
        catch(FileNotFoundException e)
        {
            System.out.println("Error opening the file " + ""+d+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
            System.exit(0);
        }
        for (int count = 0; count <12; count++)
        {
            outputStream.print(x[count]);
        }
        outputStream.close( );
    }
}

class UserInfo implements Serializable{
    public static int count=0;
    long mo;
    int age;
    String name,email,gender,username,pass;

    UserInfo(long mo, int age, String name, String email, String gender, String username, String pass)
    {
        this.mo=mo;
        this.age=age;
        this.email=email;
        this.gender=gender;
        this.name=name;
        this.username=username;
        this.pass=pass;
        try{
            FileOutputStream F1=new FileOutputStream(""+this.username+".txt");
            ObjectOutputStream O1=new ObjectOutputStream(F1);
            O1.writeObject(this);
            O1.close();
            F1.close();
            count++;
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
}
 
class BusInfo implements Serializable
{
    public static int bcount=0;
    String TravelsName,Source,Destination,Via,Type;
    int Capacity,DepartureTime;
    double Fare;
    public BusInfo(String TravelsName, int DepartureTime,String Source, String Destination, String Via, String Type, int Capacity, double Fare)
    {
        this.TravelsName=TravelsName;
        this.DepartureTime=DepartureTime;
        this.Source=Source;
        this.Destination=Destination;
        this.Via=Via;
        this.Capacity=Capacity;
        this.Fare=Fare;
        this.Type=Type;  
    }
}

class TicketInfo implements Serializable{
    public static int tcount=0;
    String PNR,Name,Source,Destination,Status,username,travels,seats,type;
    Date JourneyDate;
    double Fare;
    int BoardingTime;
    TicketInfo(String PNR, String Name,String Source,String Destination,Date JourneyDate,int BoardingTime,double Fare,String Status,String travels,String seats,String type,String username)
    {
        this.BoardingTime=BoardingTime;
        this.Destination=Destination;
        this.Fare=Fare;
        this.JourneyDate=JourneyDate;
        this.Name=Name;
        this.PNR=PNR;
        this.Source=Source;
        this.Status=Status;
        this.username=username;
        this.travels=travels;
        this.seats=seats;
        this.type=type;
        try{
            ObjectOutputStream O1=null;
            FileOutputStream F1 = new FileOutputStream(""+this.PNR+".txt");
            O1 = new ObjectOutputStream(F1);
            O1.writeObject(this);
            O1.close();
            F1.close();
            tcount++;
            System.out.println(tcount);
        }
        catch(IOException e)
        {
            System.out.println(e);
        }
    }
}

public class Project extends JFrame implements ActionListener {
    public static String curr_user,curr_pass,sel_source,sel_destination,sel_time,sel_type,sel_bus;
    public static int journey_time;
    public static double fare;
    public static Date sel_date;
    public static int[] seats=new int[16];

    JLabel l1,l2,l3,l4;
    JTextField tf1;
    JPasswordField pass;
    JButton b1,b2;
    Project()
    {
        this.setLayout(null);
        this.getContentPane().setBackground(Color.cyan);
        this.setSize(500,500);
        l1=new JLabel("LOG IN/SIGN IN");
        l2=new JLabel("User Name:");
        l3=new JLabel("Password:");
        l4=new JLabel("For new user register here:");
        b1=new JButton("Log In");
        b2=new JButton("Register");
        tf1=new JTextField("");
        pass=new JPasswordField("");
        l1.setBounds(130, 5, 100, 40);
        l2.setBounds(20, 50, 100, 20);
        l3.setBounds(20, 80, 100, 20);
        l4.setBounds(90, 140, 200, 20);
        b1.setBounds(130, 110, 80, 20);
        b2.setBounds(120, 170, 100, 20);
        tf1.setBounds(170, 50, 100, 20);
        pass.setBounds(170, 80, 100, 20);
        this.add(l1);
        this.add(l2);
        this.add(l3);
        this.add(l4);
        this.add(b1);
        this.add(b2);
        this.add(tf1);
        this.add(pass);
        
        b1.addActionListener(this);
        b2.addActionListener(new ActionListener()       //REGISTER
        {
            public void actionPerformed(ActionEvent ae)
            {
                JFrame J=new JFrame();
                J.setLayout(null);
                J.getContentPane().setBackground(Color.LIGHT_GRAY);
                J.setSize(500,500);
                NumberFormat numeric = NumberFormat.getNumberInstance();
                
                JLabel a1,a2,a3,a4,a5,a6,a7,a8,a9;
                JTextField x1,x3,x5,x6,x7,x2,x8;
                
                a9=new JLabel("REGISTER");
                a1=new JLabel("Name :");
                a2=new JLabel("Contact No. :");
                a3=new JLabel("Email :");
                a4=new JLabel("Gender :");
                a8=new JLabel("Age");
                a5=new JLabel("User Name:");
                a6=new JLabel("Password:");
                a7=new JLabel("Confirm Password:");
                x1=new JTextField("");
                x2=new JTextField("");
                x3=new JTextField("");
                String[] BusType={"Male","Female","Other"};
                JComboBox x4=new JComboBox(BusType);
                x5=new JTextField("");
                x6=new JTextField("");
                x7=new JTextField("");
                x8=new JTextField("");
                JButton br1=new JButton("Register");
                a9.setBounds(130, 5, 100, 40);
                a1.setBounds(20, 50, 100, 20);
                a2.setBounds(20, 80, 100, 20);
                a3.setBounds(20, 110, 100, 20);
                a4.setBounds(20, 140, 100, 20);
                a8.setBounds(20, 170, 100, 20);
                a5.setBounds(20, 200, 100, 20);
                a6.setBounds(20, 230, 100, 20);
                a7.setBounds(20, 260, 100, 20);
                x1.setBounds(170, 50, 100, 20);
                x2.setBounds(170, 80, 100, 20);
                x3.setBounds(170, 110, 100, 20);
                x4.setBounds(170, 140, 100, 20);
                x8.setBounds(170, 170, 100, 20);
                x5.setBounds(170, 200, 100, 20);
                x6.setBounds(170, 230, 100, 20);
                x7.setBounds(170, 260, 100, 20);
                br1.setBounds(130, 290, 100, 20);
                J.add(a1);
                J.add(a2);
                J.add(a3);
                J.add(a4);
                J.add(a5);
                J.add(a6);
                J.add(a7);
                J.add(a8);
                J.add(a9);
                J.add(x1);
                J.add(x2);
                J.add(x3);
                J.add(x4);
                J.add(x5);
                J.add(x6);
                J.add(x7);
                J.add(x8);
                J.add(br1);
                x2.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent ke) {
                       if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                          x2.setEditable(true);
                       } else {
                          x2.setEditable(false);
                          
                       }
                    }
                 });
                x8.addKeyListener(new KeyAdapter() {
                    public void keyPressed(KeyEvent ke) {
                       if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9') {
                          x8.setEditable(true);
                       } else {
                          x8.setEditable(false);
                          
                       }
                    }
                 });
                br1.addActionListener(new ActionListener()
                {
                   public void actionPerformed(ActionEvent ae) 
                   {
                       String P,CP;
                       P=x6.getText();
                       CP=x7.getText();
                       if(!P.equals(CP))
                        {
                            JOptionPane.showMessageDialog(J, "The Password does not match!!");
                        }
                       else
                       { 
                           new UserInfo(Long.parseLong(x2.getText()),Integer.parseInt(x8.getText()),x1.getText(),x3.getText(),""+x4.getSelectedItem(),x5.getText(),x6.getText());
                           JOptionPane.showMessageDialog(J, "Registeration Successful!!");
                           J.dispose();
                       }
                   }
                });
                
                J.setVisible(true);
                setDefaultCloseOperation(EXIT_ON_CLOSE);
            }
        });
        this.setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
    }
    public void actionPerformed(ActionEvent ae)         //Log-In
    {
        boolean match=false;
        
        try
        {
            File f = new File(""+tf1.getText()+".txt");
            if(f.exists())
            {
                FileInputStream F2=new FileInputStream(""+tf1.getText()+".txt");
                ObjectInputStream O2=new ObjectInputStream(F2);
                UserInfo OR=(UserInfo)O2.readObject();
                if(tf1.getText().equals(OR.username) && pass.getText().equals(OR.pass))
                {
                    Project.curr_user=tf1.getText();
                    Project.curr_pass=pass.getText();
                    match=true;
                }
                O2.close();
                F2.close();
            }
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        
        if(match)
        {
            JOptionPane.showMessageDialog(this, "You Are Logged In Successfully!!");
            
            JFrame J=new JFrame();
            J.setLayout(null);
            J.getContentPane().setBackground(Color.green);
            J.setSize(500,500);
            
            JMenuBar mb = new JMenuBar();
            JLabel u = new JLabel("Welcome to SSS Bookings");  
            JMenu x = new JMenu("Menu"); 
            JMenuItem m1,m2,m3,m4,m6;
            
            m1 = new JMenuItem("Personal Info"); 
            m2 = new JMenuItem("Search Buses"); 
            m3 = new JMenuItem("Get Ticket Details"); 
            m4 = new JMenuItem("Cancel Ticket"); 
            m6 = new JMenuItem("Contact Us");
            
            u.setBounds(50, 100, 400, 100);
            u.setFont(new Font("Verdana",Font.PLAIN,25));

            x.add(m1); 
            x.add(m2); 
            x.add(m3);
            x.add(m4);
            x.add(m6);

            J.add(u);
            
            m1.addActionListener(new ActionListener()       //PERSONAL-INFO
            {
                public void actionPerformed(ActionEvent ae)
                {
                    JFrame J=new JFrame();
                    J.setLayout(null);
                    J.getContentPane().setBackground(Color.LIGHT_GRAY);
                    J.setSize(500,500);
                    JLabel a1,a2,a3,a4,a5,a6,a7,a8,b1,b2,b3,b4,b5,b6,b7;
                    try
                    {
                        File f = new File(""+Project.curr_user+".txt");
                        if(f.exists())
                        {
                            FileInputStream F2=new FileInputStream(""+Project.curr_user+".txt");
                            ObjectInputStream O2=new ObjectInputStream(F2);
                            UserInfo OR=(UserInfo)O2.readObject();
                            a8=new JLabel("Personal Information");
                            a1=new JLabel("Name :");
                            a2=new JLabel("Contact No. :");
                            a3=new JLabel("Email :");
                            a4=new JLabel("Gender:");
                            a5=new JLabel("Age");
                            a6=new JLabel("User Name:");
                            a7=new JLabel("Password:");
                            b1=new JLabel(OR.name);
                            b2=new JLabel(""+OR.mo);
                            b3=new JLabel(OR.email);
                            b4=new JLabel(OR.gender);
                            b5=new JLabel(""+OR.age);
                            b6=new JLabel(OR.username);
                            b7=new JLabel(OR.pass);

                            a8.setBounds(130, 5, 200, 40);
                            a1.setBounds(20, 50, 100, 20);
                            a2.setBounds(20, 80, 100, 20);
                            a3.setBounds(20, 110, 100, 20);
                            a4.setBounds(20, 140, 100, 20);
                            a5.setBounds(20, 170, 100, 20);
                            a6.setBounds(20, 200, 100, 20);
                            a7.setBounds(20, 230, 100, 20);
                            b1.setBounds(170, 50, 100, 20);
                            b2.setBounds(170, 80, 100, 20);
                            b3.setBounds(170, 110, 100, 20);
                            b4.setBounds(170, 140, 100, 20);
                            b5.setBounds(170, 170, 100, 20);
                            b6.setBounds(170, 200, 100, 20);
                            b7.setBounds(170, 230, 100, 20);

                            J.add(a1);
                            J.add(a2);
                            J.add(a3);
                            J.add(a4);
                            J.add(a5);
                            J.add(a6);
                            J.add(a7);
                            J.add(a8);
                            J.add(b1);
                            J.add(b2);
                            J.add(b3);
                            J.add(b4);
                            J.add(b5);
                            J.add(b6);
                            J.add(b7);
                            J.setVisible(true);
                            setDefaultCloseOperation(EXIT_ON_CLOSE);
                            O2.close();
                            F2.close();
                        }
                    }
                    catch(Exception e)
                    {
                        System.out.println(e);
                    }
                }
            });
            m2.addActionListener(new ActionListener()       //select bus
            {
                public void actionPerformed(ActionEvent ae)
                {
                    JFrame J=new JFrame();
                    J.setLayout(null);
                    J.getContentPane().setBackground(Color.LIGHT_GRAY);
                    J.setSize(500,500);
                    
                    JLabel l1,l2,l3,l4,l5;
                    JTextField t1,t2;
                    JButton b1;
                    
                    l1=new JLabel("Source:");
                    l2=new JLabel("Destination:");
                    l3=new JLabel("Journey Date:");
                    l4=new JLabel("Journey Time:");
                    l5=new JLabel("Bus Type");
                    t1=new JTextField("");
                    t2=new JTextField("");
                    b1=new JButton("Search");
                    
                    l1.setBounds(50,50,130,20);
                    l2.setBounds(50,100,130,20);
                    l3.setBounds(50,200,130,20);
                    l4.setBounds(200,200,130,20);
                    l5.setBounds(50,150,130,20);
                    b1.setBounds(130, 290, 130, 20);
                    J.add(l1);
                    J.add(l2);
                    J.add(l3);
                    J.add(l4);
                    J.add(l5);
                    J.add(b1);
                    
                    UtilDateModel model = new UtilDateModel();
                    Properties p = new Properties();
                    p.put("text.today", "Today");
                    p.put("text.month", "Month");
                    p.put("text.year", "Year");
                    JDatePanelImpl datePanel = new JDatePanelImpl(model,p);
                    JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());;
                    
                    datePicker.setBounds(50, 230, 130, 20);
                    J.add(datePicker);
                    datePicker.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent ae)
                        {
                            Project.sel_date=(Date) datePicker.getModel().getValue();
                        }
                    });
                         
                    Project.sel_source=t1.getText();
                    Project.sel_destination=t2.getText();
                    String[] time={"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23","24"};
                    JComboBox c1=new JComboBox(time);
                    c1.setBounds(200,230,130,20);
                    J.add(c1);
                    c1.addItemListener(new ItemListener()
                    {
                        public void itemStateChanged(ItemEvent e)
                        {
                                Project.sel_time=""+c1.getSelectedItem();                           
                        }
                    });
                    
                    String[] BusType={"Semi-Sleeper","Volvo","Sleeper","Seater"};
                    JComboBox c2=new JComboBox(BusType);
                    c2.setBounds(200,150,130,20);
                    J.add(c2);
                    c2.addItemListener(new ItemListener()
                    {
                        public void itemStateChanged(ItemEvent e)
                        {
                            if(e.getSource()==c2)
                            {
                                Project.sel_type=""+c2.getSelectedItem();
                            }
                        }
                    });
                    
                    String[] Cities={"Ahmedabad","Gandhinagar","Vadodara","Surat","Rajkot","Bharuch","Nadiad","Mumbai"};
                    JComboBox c3=new JComboBox(Cities);
                    c3.setBounds(200,50,130,20);
                    c3.setEditable(true);
                    J.add(c3);
                    c2.addItemListener(new ItemListener()
                    {
                        public void itemStateChanged(ItemEvent e)
                        {
                            if(e.getSource()==c2)
                            {
                                Project.sel_source=""+c3.getSelectedItem();
                            }
                        }
                    });
                    
                    JComboBox c4=new JComboBox(Cities);
                    c4.setBounds(200,100,130,20);
                    c4.setEditable(true);
                    J.add(c4);
                    c2.addItemListener(new ItemListener()
                    {
                        public void itemStateChanged(ItemEvent e)
                        {
                            if(e.getSource()==c2)
                            {
                                Project.sel_destination=""+c4.getSelectedItem();
                            }
                        }
                    });
                    b1.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent e)
                        {
                            boolean match=false;
                            JFrame J=new JFrame();
                            J.setLayout(null);
                            J.getContentPane().setBackground(Color.LIGHT_GRAY);
                            J.setSize(750,500);
                            JLabel l1,l2,l3,l4,l5;
                            l1=new JLabel("Travels Name");
                            l2=new JLabel("Boarding Time");
                            l3=new JLabel("Bus Type");
                            l4=new JLabel("Seats Available");
                            l5=new JLabel("Fare");
                            l1.setBounds(20,20,100,20);
                            l2.setBounds(140,20,100,20);
                            l3.setBounds(260,20,100,20);
                            l4.setBounds(380,20,100,20);
                            l5.setBounds(500,20,100,20);
                            
                            J.add(l1);
                            J.add(l2);
                            J.add(l3);
                            J.add(l4);
                            J.add(l5);
                            
                            int i=0,x=0;
                            try{
                            boolean dataavailable=true;
                            FileInputStream F2=new FileInputStream("BusInfo.txt");
                            ObjectInputStream O2=new ObjectInputStream(F2);
                            while(dataavailable)
                            {
                                try{
                                BusInfo BR=(BusInfo)O2.readObject();
                                //System.out.println(BR.Source+"   "+Project.sel_source+"    "+BR.Destination+"      "+Project.sel_destination+"      "+BR.Via+"     "+Project.sel_destination+"   "+BR.Type+"       "+Project.sel_type);
                                if(BR.Source.equals(Project.sel_source) && BR.Type.equals(Project.sel_type))
                                {
                                    if(BR.Destination.equals(Project.sel_destination) || BR.Via.equals(Project.sel_destination))
                                    {
                                    //System.out.println(BR.DepartureTime+"      "+Integer.parseInt(Project.sel_time));
                                        if(BR.DepartureTime>=Integer.parseInt(Project.sel_time))
                                        {
                                            int availability=0;
                                            int [] y =new int[16];
                                            File f = new File(""+BR.TravelsName+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
                                            if(f.exists())
                                            {
                                                if(BR.Type.equals("Sleeper") || BR.Type.equals("Seater") || BR.Type.equals("Volvo"))
                                                    availability=12;
                                                else
                                                    availability=16;

                                                int count = 0;
                                                FileReader fr = null;
                                                try
                                                {
                                                    fr = new FileReader(""+BR.TravelsName+Project.sel_date.getDate()+Project.sel_date.getMonth()+".txt");
                                                }
                                                catch (FileNotFoundException e1)
                                                {
                                                    e1.printStackTrace();
                                                }
                                                int z;
                                                try
                                                {
                                                    while ((z = fr.read()) != -1)
                                                    {
                                                        y[count] = Character.getNumericValue((char) z);
                                                        count++;
                                                    }
                                                }
                                                catch (IOException e1)
                                                {
                                                    e1.printStackTrace();
                                                }
                                                try
                                                {
                                                    fr.close();
                                                }
                                                catch (IOException ioe)
                                                {
                                                    ioe.printStackTrace();
                                                }
                                                for(int r=0;r<count;r++)
                                                {
                                                    if(y[r]==1)
                                                        availability--;
                                                }
                                            } 
                                            else
                                            {
                                                if(BR.Type.equals("Sleeper") || BR.Type.equals("Seater") || BR.Type.equals("Volvo"))
                                                    availability=12;
                                                else
                                                    availability=16;
                                            }
                                            match=true;
                                            JLabel x1,x2,x3,x4,x5;
                                            JButton b1[]=new JButton[10];
                                            //System.out.println(x+"+++++++Values    "+BR.TravelsName+"   "+""+BR.DepartureTime+"     "+ BR.Type+ ""+BR.Fare);
                                            x1=new JLabel(BR.TravelsName);
                                            x2=new JLabel(""+BR.DepartureTime);
                                            x3=new JLabel(BR.Type);
                                            x4=new JLabel(""+availability);
                                            x5=new JLabel(""+BR.Fare);
                                            b1[x]=new JButton("Select");

                                            x1.setBounds(20,20+(x+2)*20,100,20);
                                            x2.setBounds(140,20+(x+2)*20,100,20);
                                            x3.setBounds(260,20+(x+2)*20,100,20);
                                            x4.setBounds(380,20+(x+2)*20,100,20);
                                            x5.setBounds(500,20+(x+2)*20,100,20);
                                            b1[x].setBounds(620,20+(x+2)*20,100,20);

                                            J.add(x1);
                                            J.add(x2);
                                            J.add(x3);
                                            J.add(x4);
                                            J.add(x5);
                                            J.add(b1[x]);

                                            b1[x].addActionListener(new ActionListener()
                                            {
                                                public void actionPerformed(ActionEvent e)
                                                {
                                                    Project.journey_time=BR.DepartureTime;
                                                    Project.fare=BR.Fare;
                                                    if("Sleeper".equals(Project.sel_type))
                                                    {           
                                                        new sleeper(BR.TravelsName);
                                                    }
                                                    else if("Semi-Sleeper".equals(Project.sel_type))
                                                    {
                                                        new semisleeper(BR.TravelsName);
                                                    }
                                                    else if("Seater".equals(Project.sel_type))
                                                    {
                                                        new seater(BR.TravelsName);
                                                    }
                                                    else if("Volvo".equals(Project.sel_type))
                                                    {
                                                        new seater(BR.TravelsName);
                                                    }
                                                }
                                            });
                                            x++;
                                        }                                   
                                    }                                
                                }
                                i++;
                                }
                                catch(EOFException eofe)
                                {
                                    dataavailable=false;
                                }                           
                            }
                            if(match==false)
                            {
                                JLabel y1=new JLabel("No Buses Available!!");
                                y1.setBounds(250,60,200,20);
                                J.add(y1);
                            }
                            O2.close();
                            F2.close();
                            }
                            
                            catch(IOException ie)
                            {
                                System.out.println(e);
                            } catch (ClassNotFoundException ex) {
                                Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            J.setVisible(true);
                            setDefaultCloseOperation(EXIT_ON_CLOSE);
                        }
                    });
                    J.setVisible(true);
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
            });
            m3.addActionListener(new ActionListener()       //Ticket_info
            {
                public void actionPerformed(ActionEvent ae)
                {
                    JFrame J=new JFrame();
                    J.setLayout(null);
                    J.getContentPane().setBackground(Color.LIGHT_GRAY);
                    J.setSize(1050,500);
                    
                    JLabel l1=new JLabel("Enter PNR No.:");
                    JTextField t1=new JTextField("");
                    JButton b1=new JButton("Search");
                    
                    l1.setBounds(450,40,150,20);
                    t1.setBounds(450,80,150,20);
                    b1.setBounds(475,120,100,20);
                    
                    J.add(l1);
                    J.add(t1);
                    J.add(b1);
                    
                    b1.addActionListener(new ActionListener()
                    {
                        public void actionPerformed(ActionEvent ae)
                        {
                            boolean match=false;
                            JFrame J=new JFrame();
                            J.setLayout(null);
                            J.getContentPane().setBackground(Color.green);
                            J.setSize(500,700);

                            JLabel z1,z2,z3,z4,z5,z6,z7,z8,z9,z10,z11,z12;
                            JLabel y1,y2,y3,y4,y5,y6,y7,y8,y9,y10,y11;

                            z9=new JLabel("BOOKING DETAILS");                    
                            z1=new JLabel("PNR");
                            z2=new JLabel("Name");
                            z3=new JLabel("Source");
                            z4=new JLabel("Destination");
                            z5=new JLabel("Journey Date");
                            z6=new JLabel("Boarding Time");
                            z7=new JLabel("Fare");
                            z8=new JLabel("Bus_Name");
                            z10=new JLabel("Bus_type");
                            z11=new JLabel("Seats");
                            z12=new JLabel("Status");

                            try
                            {
                                File f = new File(""+t1.getText()+".txt");
                                if(f.exists())
                                {
                                    FileInputStream F2=new FileInputStream(""+t1.getText()+".txt");
                                    ObjectInputStream O2=new ObjectInputStream(F2);
                                    String s="";
                                    TicketInfo TR=(TicketInfo)O2.readObject();
                                    if(TR.type.equals("Seater") || TR.type.equals("Volvo"))
                                    {
                                        for(int e=0;e<12;e++)
                                        {
                                            int g=e+1;
                                            if(TR.seats.charAt(e)=='1')
                                            {
                                                if(e<3 || e>8)
                                                {
                                                    s+=(g+'\0')+"-W ";
                                                }
                                                else
                                                {
                                                    s+=(g+'\0')+"-A ";
                                                }
                                            }
                                        }
                                    }
                                    if(TR.type.equals("Sleeper"))
                                    {
                                        for(int e=0;e<12;e++)
                                        {
                                            int g=e+1;
                                            if(TR.seats.charAt(e)=='1')
                                            {
                                                if(e<3 || e>8)
                                                {
                                                    s+=(g+'\0')+"-U ";
                                                }
                                                else
                                                {
                                                    s+=(g+'\0')+"-L ";
                                                }
                                            }
                                        }
                                    }
                                    if(TR.type.equals("Semi-Sleeper"))
                                    {
                                        for(int e=0;e<16;e++)
                                        {
                                            int g=e+1;
                                            if(TR.seats.charAt(e)=='1')
                                            {
                                                if(e<6)
                                                {
                                                    s+=(g+'\0')+"-U ";
                                                }
                                                else
                                                {
                                                    s+=(g+'\0')+"-L ";
                                                }
                                            }
                                        }
                                    }
                                    match=true;
                                    y1=new JLabel(TR.PNR);
                                    y2=new JLabel(TR.Name);
                                    y3=new JLabel(TR.Source);
                                    y4=new JLabel(TR.Destination);
                                    y5=new JLabel(""+TR.JourneyDate);
                                    y6=new JLabel(""+TR.BoardingTime);
                                    y7=new JLabel(""+TR.Fare);
                                    y8=new JLabel(TR.travels);
                                    y9=new JLabel(TR.type);
                                    y10=new JLabel(s);
                                    y11=new JLabel("Confirmed");

                                    z9.setBounds(40,30,150,20);
                                    z1.setBounds(20,80,100,20);
                                    z2.setBounds(20,120,100,20);
                                    z3.setBounds(20,160,100,20);
                                    z4.setBounds(20,200,100,20);
                                    z5.setBounds(20,240,100,20);
                                    z6.setBounds(20,280,100,20);
                                    z7.setBounds(20,320,100,20);
                                    z8.setBounds(20,360,100,20);
                                    z10.setBounds(20,400,100,20);
                                    z11.setBounds(20,440,100,20);
                                    z12.setBounds(20,480,100,20);

                                    y1.setBounds(150,80,100,20);
                                    y2.setBounds(150,120,100,20);
                                    y3.setBounds(150,160,100,20);
                                    y4.setBounds(150,200,100,20);
                                    y5.setBounds(150,240,80,20);
                                    y6.setBounds(150,280,100,20);
                                    y7.setBounds(150,320,100,20);
                                    y8.setBounds(150,360,150,20);
                                    y9.setBounds(150,400,100,20);
                                    y10.setBounds(150,440,200,20);
                                    y11.setBounds(150,480,100,20);

                                    J.add(z1);
                                    J.add(z2);
                                    J.add(z3);
                                    J.add(z4);
                                    J.add(z5);
                                    J.add(z6);
                                    J.add(z7);
                                    J.add(z8);
                                    J.add(z9);
                                    J.add(y1);
                                    J.add(y2);
                                    J.add(y3);
                                    J.add(y4);
                                    J.add(y5);
                                    J.add(y6);
                                    J.add(y7);
                                    J.add(y8);
                                    J.add(y9);
                                    J.add(y10);
                                    J.add(y11);
                                    J.add(z10);
                                    J.add(z11);
                                    J.add(z12);       
                                    J.setVisible(true);
                                    setDefaultCloseOperation(EXIT_ON_CLOSE);   
                                    O2.close();
                                    F2.close();     
                                }
                                
                                /*
                                if(!match)
                                {
                                    //System.out.println("Hello");
                                    JLabel v1=new JLabel("No Ticket Found With Entered PNR Number!!");
                                    v1.setBounds(150,160,250,20);
                                    J.add(v1);
                                }*/
                                else
                                {
                                    JOptionPane.showMessageDialog(J, "Sorry!! No Ticket Found With Entered PNR Number");
                                }
                            }
                            catch(IOException e)
                            {
                                System.out.println(e);
                            }
                            catch (ClassNotFoundException ex)
                            {
                                Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
                            }                            
                        }
                    });
                    J.setVisible(true);
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
            });
            m4.addActionListener(new ActionListener()       //TICKET-CANCELLATION
            {
                public void actionPerformed(ActionEvent ae)
                {
                    JFrame J=new JFrame();
                    J.setLayout(null);
                    J.getContentPane().setBackground(Color.LIGHT_GRAY);
                    J.setSize(500,500);
                    
                    JLabel l1=new JLabel("Enter PNR No.:");
                    JTextField t1=new JTextField("");
                    JButton b1=new JButton("Search");
                    
                    l1.setBounds(170,40,150,20);
                    t1.setBounds(170,80,150,20);
                    b1.setBounds(200,120,100,20);
                    
                    J.add(l1);
                    J.add(t1);
                    J.add(b1);
                    
                    b1.addActionListener(new ActionListener()
                    {
                       public void actionPerformed(ActionEvent ae)
                       {
                            boolean match=false;
                            boolean x=false;
                           try
                           {
                                File f = new File(""+t1.getText()+".txt");
                                if(f.exists())
                                {
                                    FileInputStream F2=new FileInputStream(""+t1.getText()+".txt");
                                    ObjectInputStream O2=new ObjectInputStream(F2);
                                    TicketInfo TR=(TicketInfo)O2.readObject();
                                    if(TR.username.equals(Project.curr_user))
                                    {
                                        if(x==false)
                                        {
                                            match=true;
                                            TR.Status="Cancelled";
                                            JOptionPane.showMessageDialog(J, "Ticket Cancellation Successful!!");
                                            new cancel(TR.seats,TR.travels,TR.type,TR.JourneyDate,TR.seats);
                                            x=true;
                                        }
                                        else
                                        {
                                            JOptionPane.showMessageDialog(J, "ticket already deleted With Entered PNR Number!!");        
                                        }
                                    }
                                    /*if(!match)
                                    {
                                        JOptionPane.showMessageDialog(J, "No Ticket Found With Entered PNR Number!!");
                                    }*/
                                    O2.close();
                                    F2.close();
                                    f.delete();
                                }
                                else
                                {
                                    JOptionPane.showMessageDialog(J, "No Ticket Found With Entered PNR Number!!");
                                }
                            }
                           catch(EOFException e)
                            {
                                System.out.println(e);
                            }
                            catch (ClassNotFoundException ex)
                            {
                                Logger.getLogger(Project.class.getName()).log(Level.SEVERE, null, ex);
                            }
                            catch(IOException e)
                            {
                                System.out.println(e);
                            }
                           
                       }
                    });
                    J.setVisible(true);
                    setDefaultCloseOperation(EXIT_ON_CLOSE);
                }
            });
            
            m6.addActionListener(new ActionListener()
            {
                public void actionPerformed(ActionEvent ae)
                {
                    JOptionPane.showMessageDialog(J, "Reach Us At: \n Email: busbookings@gmail.org.in \n Contact No: +91 9990945672");
                }
            });
            mb.add(x); 
            J.setJMenuBar(mb);  
            
            J.setVisible(true);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
        }
        else
             JOptionPane.showMessageDialog(this, "The Username or Password You Entered Is/Are Incorrect");
        }
    public static void main(String[] args){
        BusInfo a1=new BusInfo("Neeta Travels",7,"Ahmedabad","Surat","Vadodara","Sleeper",12,350.00);
        BusInfo a2=new BusInfo("Mahasagar Travels",6,"Surat","Ahmedabad","Vadodara","Semi-Sleeper",16,350.00);
        BusInfo a3=new BusInfo("Gujarat Travels",7,"Ahmedabad","Vadodara","Nadiad","Seater",12,250.00);
        BusInfo a4=new BusInfo("Kabra Travels",8,"Gandhinagar","Rajkot","Ahmedabad","Sleeper",12,450.00);
        BusInfo a5=new BusInfo("Pavaan Travels",13,"Ahmedabad","Mumbai","Surat","Sleeper",12,950.00);
        BusInfo a6=new BusInfo("Paulo Travels",14,"Surat","Vadodara","Bharuch","Seater",12,300.00);
        BusInfo a7=new BusInfo("Khurana Travels",14,"Vadodara","Mumbai","Surat","Semi-Sleeper",16,750.00);
        BusInfo a8=new BusInfo("Himmat Travels",20,"Rajkot","Ahmedabad","Gandhinagar","Sleeper",12,450.00);
        BusInfo a9=new BusInfo("Parshwanath Travels",21,"Mumbai","Ahmedabad","Vadodara","Semi-Sleeper",16,950.00);
        BusInfo a10=new BusInfo("Mahaveer Travels",22,"Nadiad","Surat","Vadodara","Seater",12,300.00);
        try
        {
            FileOutputStream F1=new FileOutputStream("BusInfo.txt");
            ObjectOutputStream O1=new ObjectOutputStream(F1);
            O1.writeObject(a1);
            O1.writeObject(a2);
            O1.writeObject(a3);
            O1.writeObject(a4);
            O1.writeObject(a5);
            O1.writeObject(a6);
            O1.writeObject(a7);
            O1.writeObject(a8);
            O1.writeObject(a9);
            O1.writeObject(a10);
            O1.close();
            F1.close();
        }
        catch(Exception e)
        {
            System.out.println(e);
                    
        }
        new Project();
    }
}