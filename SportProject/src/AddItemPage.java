import com.sun.prism.shader.Solid_TextureSecondPassLCD_AlphaTest_Loader;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import com.sun.xml.internal.ws.api.client.SelectOptimalEncodingFeature;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AddItemPage extends WindowMenu {
    JTextField[] arr;
    String[] menu;
    String[] types;
    Label errorlabel;
    Label label;
    Color or = new Color(246, 184, 61);
    private DataBase dataBase;
    private String query;
    protected String nameInDB;
    public AddItemPage(String nameT, String nameDB, DataBase dataBase) {
        super(nameT);
        this.nameInDB=nameDB;
        this.dataBase=dataBase;
        this.query="select * from "+this.nameInDB +";";
    }

    @Override
    public void show() throws SQLException {
        super.show();

        //кількість полів
        String query = "select * from "+this.nameInDB +";";
        ResultSet rs =  dataBase.select(query);
        int colCount = rs.getMetaData().getColumnCount();
        //параметри для розміщення об*єктів
        int width = frame.getWidth()/colCount-20;
        int height = 40;
        int x = 30;
        int y=250;

        //label for error message
        errorlabel=new Label("");
        errorlabel.setSize(400, height);
        errorlabel.setLocation(x, y+width);
        frame.add(errorlabel);

        arr =new JTextField[colCount];
        menu = new String[colCount];
        types = new String[colCount];
        for (int i = 0; i<colCount; i++) {
            types[i]=rs.getMetaData().getColumnTypeName(i+1);
            menu[i]=rs.getMetaData().getColumnLabel(i+1);
            label = new Label(rs.getMetaData().getColumnLabel(i+1));
            label.setSize(width, height);
            label.setLocation(x, y-height);
            frame.add(label);
            arr[i] = new JTextField();
            arr[i].setSize(width, height);
            arr[i].setLocation(x, y);
            frame.add(arr[i]);
            x+=width+10;
        }
        addButtons();
        JFrame.setDefaultLookAndFeelDecorated(true);
    }

    public void addButtons(){
        JButton saveItem = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'>ЗБЕРЕГТИ</div></html>");
        saveItem.setSize(190, 30);
        saveItem.setLocation( 400, 600);
        saveItem.setBackground(or);
        saveItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ShowResPage main = new ShowResPage(nameOfTable, nameInDB, dataBase);
                try {
                    int size = menu.length-1;
                    String q ="INSERT INTO "+ nameInDB+"(";
                    for (int i=0; i<size; i++){
                        q+=menu[i]+", ";
                    }
                    q+=menu[size]+")";
                    q+= "\n VALUES (";
                    String line;
                    for (int i=0; i<size; i++){
                        if(types[i]=="INT") {
                            if(arr[i].getText().matches("[-+]?\\d+"))
                            {
                                q += arr[i].getText() + ", ";
                            }else {errorlabel.setText("field "+menu[i]+"must be a number!");
                            arr[i].setText(null);
                            }
                        }else {
                            line=arr[i].getText();
                            q+="'"+arr[i].getText()+"', ";}
                    }
                    if(types[size]=="INT")if(arr[size].getText().matches("[-+]?\\d+"))
                    {
                        q += arr[size].getText() + ", ";
                    }else {errorlabel.setText("field "+menu[size]+"must be a number!");
                        arr[size].setText(null);
                    }
                    else {q+="'"+arr[size].getText()+"');";}
                    System.out.println(q);
                    boolean all =true;
                    for(int i=0; i<arr.length; i++){
                        if(arr[i].getText()==null || arr[i].getText().isEmpty())all=false;
                    }
                    if(all) {
                        dataBase.add(q);
                        main.show();
                        frame.dispose();
                    }
                    else{
                        errorlabel.setText("Some fields is empty");
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        frame.add(saveItem);

        JButton cancelItem = new JButton("<html> <style type='text/css'>.plus{color: white;} </style><div class='plus'>ВІДМІНИТИ</div></html>");
        cancelItem.setSize(190, 30);
        cancelItem.setLocation( 400+saveItem.getWidth()+20, 600);
        cancelItem.setBackground(or);
        cancelItem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ShowResPage main = new ShowResPage(nameOfTable, nameInDB, dataBase);
                try {
                    main.show();
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
                frame.dispose();
            }
        });
        frame.add(cancelItem);
    }
}
