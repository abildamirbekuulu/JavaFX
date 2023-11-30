
import javafx.scene.control.Label;
import java.lang.*;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;//This is used to set up the layout
import javafx.scene.layout.VBox;//This is used to set up the layout
import javafx.scene.layout.GridPane;//This is used to set up the layout
import javafx.scene.layout.BorderPane;//This is used to set up the layout
import javafx.scene.layout.TilePane;//This is used to set up the layout
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

public class CoursePane extends HBox
{
    //GUI components
    private ArrayList<Course> courseList, delList;
    private VBox checkboxContainer;

    //Step 1.1: declare any necessary instance variables here
    private ArrayList<CheckBox> checkBoxList;
    private Label lbCorrectness, lbNumEnrolled, lbSub, lbCourNum, lbInst;
    private int counter;
    private Button btAdd, btSub;
    private TextField tfCourNum, tfInst;
    private ComboBox<String> cbDate;
    private String classPicked, instructor;
    private int numOfClass, totalCourses;
    //constructor
    public CoursePane()
    {
        //step 1.2: initialize instance variables
        lbCorrectness = new Label("No course entered");
        lbNumEnrolled = new Label("Total course enrolled: ");
        totalCourses = 0;
        
        Label labelLeft = new Label("Add Course(s)");
        labelLeft.setTextFill(Color.BLUE);
        labelLeft.setFont(Font.font(null, 14));

        Label labelRight = new Label("Course(s) Enrolled");
        labelRight.setTextFill(Color.BLUE);
        labelRight.setFont(Font.font(null, 14));
        
        lbSub = new Label("Subject");
        cbDate = new ComboBox<String>();
        cbDate.getItems().addAll("ACC","AME","BME","CHM","CSE","DAT","EEE");
        cbDate.setValue("CSE");
        classPicked = "CSE";
        numOfClass = 205;
        lbCourNum = new Label("Course Num");
        tfCourNum = new TextField();
        lbInst = new Label("Instructor");
        tfInst = new TextField();
        
        btAdd = new Button("Add =>");
        btSub = new Button("Drop <=");
         
        
         
        //set up the layout. Note: CoursePane is a HBox and contains
        //leftPane, centerPane and rightPane. Pick proper layout class
        //and use nested sub-pane if necessary, then add each GUI components inside.

        //step 1.3: create and set up the layout of the leftPane, leftPane contains a top label, the center sub-pane
        //and a label show at the bottom
         BorderPane pane1 = new BorderPane();
         GridPane centerPane = new GridPane();
         centerPane.setPadding(new Insets(10, 10, 10, 10));
         centerPane.setAlignment(Pos.CENTER);
         centerPane.setHgap(10);
         centerPane.setVgap(20);
         centerPane.add(lbSub, 0, 0);
         centerPane.add(lbCourNum, 0, 1);
         centerPane.add(lbInst, 0, 2);
         centerPane.add(cbDate, 1, 0);
         centerPane.add(tfCourNum, 1, 1);
         centerPane.add(tfInst, 1, 2);
         pane1.setTop(labelLeft);
         pane1.setCenter(centerPane);
         pane1.setBottom(lbCorrectness);

        //step 1.4: create and set up the layout of the centerPane which holds the two buttons
		  VBox pane2 = new VBox();
        pane2.setSpacing(10.0);
        pane2.setAlignment(Pos.CENTER);
        pane2.getChildren().addAll(btAdd,btSub);

        //step 1.5: create and set up the layout of the rightPane, rightPane contains a top label,
        //checkboxContainer and a label show at the bottom
        BorderPane pane3 = new BorderPane();
        VBox centerPane3 = new VBox();
        centerPane3.setSpacing(10);
        courseList = new ArrayList<Course>();
        for (int i = 0; i < courseList.size(); i++)
        {
           CheckBox aBox = new CheckBox(courseList.get(i).toString());
           checkBoxList.add(aBox);
           centerPane3.getChildren().add(aBox);
        }
        pane3.setTop(labelRight);
        pane3.setCenter(centerPane3);
        lbNumEnrolled.setText("Total course enrolled: " + totalCourses);
        pane3.setBottom(lbNumEnrolled);

        //CoursePane is a HBox. Add leftPane, centerPane and rightPane inside
        this.setPadding(new Insets(10, 10, 10, 10));
        this.setSpacing(50);
        this.getChildren().addAll(pane1, pane2, pane3);

        //Step 3: Register the GUI component with its handler class
        for (int i = 0; i < courseList.size(); i++)
        {
            CheckBox aBox = new CheckBox(courseList.get(i).toString());
            aBox.setOnAction(new CheckBoxHandler());
        }
        cbDate.setOnAction(new ComboBoxHandler());
        btAdd.setOnAction(new ButtonHandler());
        btSub.setOnAction(new ButtonHandler());

    } //end of constructor

    //step 2.1: Whenever a new Course is added or one or several courses are dropped/removed, this method will
    //1) clear the original checkboxContainer;
    //2) create a CheckBox for each Course object inside the courseList, and also add it inside the checkboxContainer;
    //3) register the CheckBox with the CheckBoxHandler.
    public void updateCheckBoxContainer()
    {
        //checkBoxList.clear();
        for (int i = 0; i < courseList.size(); i++)
        {
           CheckBox aBox = new CheckBox(courseList.get(i).toString());
           checkBoxList.add(aBox);
        }

        for (int i = 0; i < checkBoxList.size(); i++)
        {
            CheckBox aBox = new CheckBox(courseList.get(i).toString());
            aBox.setOnAction(new CheckBoxHandler());
        }

    }

    //Step 2.2: Create a ButtonHandler class
    private class ButtonHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            if(tfCourNum.getText().isEmpty() || tfInst.getText().isEmpty()){
               lbCorrectness = new Label("At least one field is empty. Fill all fields");
               lbCorrectness.setTextFill(Color.RED);
            }
            else{
               try {
                 Object source = e.getSource();
					   if (source == btAdd)//everything is entered correctly and the "Add =>" button is pressed
              	    {
                     Boolean dup = false;
                     int x = Integer.parseInt(tfCourNum.getText());
                	   //need to check whether the course already exist inside the courseList or not
                	   for(int i = 0; i < courseList.size(); i++){
                        if(courseList.get(i).getSubject().equals(classPicked)){
                           dup = true;
                           break;
                        } 
                     }
                     if (dup == false)//it's a new course
                	   {
						      Course y = new Course(classPicked, x, tfInst.getText().trim());
                        courseList.add(y);
                        lbCorrectness = new Label("Course added successfully");
                        totalCourses++;
                        lbNumEnrolled.setText("Total course enrolled: " + totalCourses);
                        updateCheckBoxContainer();
					      }
					      else //a duplicated one
					      {
						      lbCorrectness = new Label("Duplicated course - Not added");
                        lbCorrectness.setTextFill(Color.RED);
					      }
                    //Clear all the text fields and prepare for the next entry;
                    tfCourNum.clear();
                    tfInst.clear();
                   }

                  else 
                  if ( source == btSub)//when "Drop Course" button is pushed.)
                  {
                     for(int i = 0;i < delList.size(); i++){
                         courseList.remove(courseList.indexOf(delList.get(i)));
                     }
                     totalCourses -= delList.size();
                     lbNumEnrolled.setText("Total course enrolled: " + totalCourses);
                     delList.clear();
                     updateCheckBoxContainer();
                  }
                 } //end of try
   
               catch(NumberFormatException ex)
               {
                   lbCorrectness = new Label("Error! Course number must be an integer");
                   lbCorrectness.setTextFill(Color.RED);
               }

             }
        } //end of handle() method
    } //end of ButtonHandler class

    ////Step 2.3: A ComboBoxHandler
    private class ComboBoxHandler implements EventHandler<ActionEvent>
    {
       public void handle(ActionEvent e){
         classPicked = cbDate.getValue();
       }

    }//end of ComboBoxHandler

    //Step 2.4: A CheckBoxHandler
    private class CheckBoxHandler implements EventHandler<ActionEvent>
    {
        public void handle(ActionEvent e)
        {
            Course x;
            for (int i = 0; i < checkBoxList.size(); i++)
            {
               if(checkBoxList.get(i).isSelected()){
                  x = courseList.get(i);
                  delList.add(x);
               }
            }
        }
    }//end of CheckBoxHandler 

} //end of CoursePane class
