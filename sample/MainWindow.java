package sample;

import java.awt.*;
import java.awt.geom.*;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

import com.mindfusion.diagramming.*;
import com.mindfusion.diagramming.LayeredLayout;
import com.mindfusion.diagramming.FractalLayout;

import com.mindfusion.diagramming.jlayout.Orientation;


/**
 * @description Main class for the sample application.
 *
 * @author MindFusion LLC
 * @version 1.0 $Date: 11/09/2016
 */

public class MainWindow extends JFrame {


    private Diagram diagram;
    private DiagramView diagramView;
    private JScrollPane _scrollPane;
    private ZoomControl zoomer;
    private  ArrayList<String> tables;

    public MainWindow() throws SQLException
    {
        super("Shema en etoile");
        setVisible(true);
        //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //initialize controls and table data
        initialize();

        //create DB tables
        createTables();

        createRelationships();

        arrangeDiagram();


        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
    }

    private void initialize() throws SQLException
    {
        //diagram initialization
        diagram = new Diagram();
        diagram.setAutoResize(AutoResize.AllDirections);

        //initialize a diagramView that will render the diagram.
        diagramView = new DiagramView(diagram);
        diagramView.setVisible(true);

        //provide a zoomer for the diagram
        zoomer = new ZoomControl();
        zoomer.setView(diagramView);
        zoomer.setPreferredSize(new Dimension(70, 50));
        zoomer.setVisible(false);

        //use a scroll pane to host large diagrams
        _scrollPane = new JScrollPane(diagramView);
        _scrollPane.setVisible(true);
        _scrollPane.setAutoscrolls(true);

        getContentPane().setLayout(new BorderLayout());
        this.add(zoomer, BorderLayout.EAST);
        this.add(_scrollPane, BorderLayout.CENTER);

        //diagram settings
        diagram.setTableColumnCount(8);
        diagram.setTableRowHeight(10f);
        diagram.setShadowsStyle(ShadowsStyle.None);
        diagram.setEnableStyledText(true);

        //read data for the tables
        tables = VariablesController.getTables(TablesController.t);

    }


    private void createTables()
    {
        for( String tableName: tables)
        {
            try
            {
                ArrayList<DBColumn> tableData = TablesController.getColumInfo(tableName);
System.out.println(tableData.size());
tableData.remove(tableData.size()-1);
                Dimension tableSize = new Dimension(50, 30);
                TableNode _table = diagram.getFactory().createTableNode(10, 10, 50, tableData.size() * 8, 4, tableData.size());
                _table.setCaption("<b>" + tableName + "</b>");
                _table.setId(tableName);

                //customize the tables
                _table.setCaptionFormat(new TextFormat(Align.Center, Align.Center));
                _table.setCaptionHeight(7f);
                _table.setRowHeight(10f);
                _table.setAllowResizeColumns(true);
                _table.getColumns().get(0).setWidth(22f);
                _table.setShape(SimpleShape.Rectangle);
               
                _table.setBrush(new SolidBrush(new Color((int)255, (int)255, (int)255)));
                int rowIndex = 0;

                for(DBColumn column: tableData)
                {
                    _table.getCell(1, rowIndex).setText("<b>" + column.name + "</b>");
                    _table.getCell(2, rowIndex).setText(column.type);
                    _table.getCell(3, rowIndex).setText(column.size);
                    Font font = new Font("Verdana", Font.ITALIC, 3);
                    _table.getCell(1, rowIndex).setFont(font);
                    _table.getCell(2, rowIndex).setFont(font);
                    _table.getCell(3, rowIndex).setFont(font);

                 
                   
                    //if the column is a primary key - set an image. If not, leave it empty.
                    if(column.isPrimaryKey())
                    {
                        try {

        		            File pathToFile = new File("/home/lotfi/test_/entropot/src/resources/key.png");
                            Image image = ImageIO.read(pathToFile);
                            _table.getCell(0,rowIndex).setImage(image);


                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                    rowIndex++;

                }

                //adjust the size of the tables and columns.
                _table.resizeToFitText(true);
                Rectangle2D.Float t_size = _table.getBounds();
                _table.getColumns().get(0).setWidth(7);
                _table.resize(t_size.width + 7, t_size.height);
                _table.resizeToFitImage();


            }catch(Exception s)
            {
                System.err.println(s.getMessage());
            }
        }
    }
String getId(String table) throws SQLException {
	String databaseURL = "jdbc:postgresql://localhost:5432/"+TablesController.t;
    String username = "postgres";
    String password = "linux12";
    Connection connection = DriverManager.getConnection(databaseURL, username, password);
    System.out.println("connected");
    Statement statement = connection.createStatement();
   
String r="";
  
    	 String sql="SELECT column_name FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = '"+table+"';";
    	    ResultSet result = statement.executeQuery(sql);
    	    
result.next();
    		r=result.getString(1);
    	
    
   
    connection.close();
    return r;
}
ArrayList<DBRelation> getRelationships(String bdd) throws SQLException{
	String databaseURL = "jdbc:postgresql://localhost:5432/"+TablesController.t;
    String username = "postgres";
    String password = "linux12";
    Connection connection = DriverManager.getConnection(databaseURL, username, password);
    System.out.println("connected");
    Statement statement = connection.createStatement();
    String sql="select kcu.table_schema || '.' ||kcu.table_name as foreign_table,\n" + 
    		"       '>-' as rel,\n" + 
    		"       rel_tco.table_schema || '.' || rel_tco.table_name as primary_table,\n" + 
    		"       string_agg(kcu.column_name, ', ') as fk_columns,\n" + 
    		"       kcu.constraint_name\n" + 
    		"from information_schema.table_constraints tco\n" + 
    		"join information_schema.key_column_usage kcu\n" + 
    		"          on tco.constraint_schema = kcu.constraint_schema\n" + 
    		"          and tco.constraint_name = kcu.constraint_name\n" + 
    		"join information_schema.referential_constraints rco\n" + 
    		"          on tco.constraint_schema = rco.constraint_schema\n" + 
    		"          and tco.constraint_name = rco.constraint_name\n" + 
    		"join information_schema.table_constraints rel_tco\n" + 
    		"          on rco.unique_constraint_schema = rel_tco.constraint_schema\n" + 
    		"          and rco.unique_constraint_name = rel_tco.constraint_name\n" + 
    		"where tco.constraint_type = 'FOREIGN KEY'\n" + 
    		"group by kcu.table_schema,\n" + 
    		"         kcu.table_name,\n" + 
    		"         rel_tco.table_name,\n" + 
    		"         rel_tco.table_schema,\n" + 
    		"         kcu.constraint_name\n" + 
    		"order by kcu.table_schema,\n" + 
    		"         kcu.table_name;";
    ResultSet result = statement.executeQuery(sql);
ArrayList<DBRelation>   t=new ArrayList<>();
System.out.println("adadada");

while(result.next()) {
	String c=result.getString(1);
	String y=result.getString(3);
	System.out.println(c+y);
	t.add(new DBRelation(getId(c.substring(c.indexOf(".")+1,c.length())),c.substring(c.indexOf(".")+1,c.length()),result.getString(4),y.substring(y.indexOf(".")+1,y.length())));
}

return t;
    
}
    private void createRelationships() throws SQLException
    {
        ArrayList<DBRelation> relations = getRelationships(TablesController.t);
	

		for(DBRelation relation: relations)
		{
		    TableNode source = (TableNode)diagram.findNodeById(relation.pk_table);
		    TableNode destination = (TableNode)diagram.findNodeById(relation.fk_table);

		    if(source != null && destination != null)
		    {
		        int pk_index = -1;
		        int fk_index =  -1;

		        int rowCount = source.getRowCount();

		        for(int i = 0; i < rowCount; i++)
		        {
		            Cell cell = source.getCell(1, i);

		            if(cell.getText().equals("<b>" + relation.pk_key + "</b>"))
		            {
		                pk_index = i;
		                break;
		            }
		        }

		        rowCount = destination.getRows().size();

		        for(int i = 0; i < rowCount; i++) {
		            Cell cell = destination.getCell(1, i);
		            if(cell.getText().equals("<b>" + relation.fk_key + "</b>")) {
		                fk_index = i;
		                break;
		            }
		        }

		        DiagramLink link = diagram.getFactory().createDiagramLink(source, pk_index, destination, fk_index);
		        link.setBaseShape(ArrowHeads.RevWithLine);
		        link.setBaseShapeSize(3f);
		        link.setHeadShapeSize(3f);
		        link.setShape(LinkShape.Cascading);

		        //if the relation is not on a primary key column, assign a fk image to it.
		        try {

		            File pathToFile = new File("/home/lotfi/test_/entropot/src/resources/key.png");
		            Image image = ImageIO.read(pathToFile);
		            if(fk_index > -1 && destination.getCell(0,fk_index).getImage() == null)
		                destination.getCell(0,fk_index).setImage(image);

		        } catch (IOException ex) {
		            ex.printStackTrace();
		        }
		    }
		}
    }

    private void arrangeDiagram()
    {
        //use LayeredLayout with some initial customization
    	CircularLayout layout = new CircularLayout();
    	layout.setRadius(100f);
        layout.arrange(diagram);

        //adjust link position
        for (DiagramLink link: diagram.getLinks()) {

            if (link.getOrigin().getBounds().getX() < link.getDestination().getBounds().getX())
                link.setEndPoint(new Point2D.Float(link.getDestination().getBounds().x, link.getEndPoint().y));
        }
        //re-route all links
        diagram.setLinkRouter(new GridRouter());
        diagram.routeAllLinks();

        //resize the diagram after the layout to fit all items
        diagram.resizeToFitItems(20);
        diagramView.scrollTo(diagram.getBounds().width/2, 0);

        //customize the links
        diagram.setLinkCrossings(LinkCrossings.Arcs);
        diagram.setRoundedLinks(true);
        diagram.setRoundedLinksRadius(3);
        //redraw the control
        diagram.repaint();
    }
    /**
     * Run the application.
     *
     * @param args
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                MainWindow window = null;
                try {
                    window = new MainWindow();
                    window.setVisible(true);
                }
                catch (Exception exp) {
                    exp.printStackTrace();
                }
            }
        });
    }
}