import com.tm.hr.pl.model.*;
import com.tm.hr.bl.exceptions.*;
import java.awt.*;
import javax.swing.*;
class DesignationModelAdd extends JFrame
{
private JTable table;
private JScrollPane jsp;
private Container container;
private DesignationModel dm;
public DesignationModelAdd()
{
dm=new DesignationModel();
table=new JTable(dm);
jsp=new JScrollPane(table, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS,ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);
container=getContentPane();
container.setLayout(new BorderLayout());
container.add(jsp);
setDefaultCloseOperation(EXIT_ON_CLOSE);
setLocation(100,200);
setSize(300,250);
setVisible(tr	
}
}
class psp
{
public static void main(String args[])
{
DesignationModelAdd dma=new DesignationModelAdd();
}
}