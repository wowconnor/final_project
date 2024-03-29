package com.code_source.final_project.view;

import com.code_source.final_project.controller.Controller;
import com.code_source.final_project.model.InternationalStudent;
import com.code_source.final_project.model.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

import java.time.format.DateTimeFormatter;

public class InternationalStudentScene extends Scene {

    private Button buttonBack = new Button("BACK");
    //name
    private final Label nameLabel = new Label("NAME:");
    private final TextField nameText = new TextField();

    //DOB
    private final Label dobLabel = new Label("DATE OF BIRTH:");
    private final DatePicker datePicker = new DatePicker();

    //Gender
    private final Label genderLabel = new Label("GENDER:");
    private final ToggleGroup groupGender = new ToggleGroup();
    private final RadioButton maleRadio = new RadioButton("Male");
    private final RadioButton femaleRadio = new RadioButton("Female");
    private final RadioButton nonBinaryRadio = new RadioButton("Non-Binary");

    //languages
    private final Label languagesLabel = new Label("LANGUAGES:");
    private final CheckBox javaCheckBox=new CheckBox("Java");
    private final CheckBox cppCheckBox=new CheckBox("C++");
    private final CheckBox pythonCheckBox = new CheckBox("Python");

    //education
    private final Label educationLabel = new Label("EDUCATION:");
    private final ListView<String> edulist = new ListView<>();
    private final ObservableList<String> data = FXCollections.observableArrayList();

    //location
    private final Label locationLabel=new Label("LOCATION:");
    private final ChoiceBox<Object> locationChoiceBox = new ChoiceBox<>();

    //country of origin
    private final Label countryLabel = new Label("COUNTRY OF ORIGIN:");
    private final TextField countryText = new TextField();

    //Host Family
    private final Label hostFamilyLabel = new Label("HOST FAMILY?");
    private final ToggleGroup hostFamilyGroup = new ToggleGroup();
    private final RadioButton no = new RadioButton("NO");
    private final RadioButton yes = new RadioButton("YES");

    //Register Button
    private final Button buttonRegister =new Button("Register");

    //Delete Button
    private final Button buttonDelete = new Button("Delete");

    //listview from binary file
    private final Controller controller = Controller.getInstance();
    private final ListView<Student> studentsLV = new ListView<>();
    private final ObservableList<Student> studentsList;
    private Student selectedStudent;


    public InternationalStudentScene()
    {
        super(new GridPane(), 900, 500);
        GridPane gridPane = new GridPane();

        gridPane.setVgap(7);
        gridPane.setHgap(5);
        gridPane.setPadding(new Insets(10, 10, 10, 10));
        gridPane.setAlignment(Pos.BASELINE_LEFT);

        //gender
        maleRadio.setToggleGroup(groupGender);
        femaleRadio.setToggleGroup(groupGender);
        nonBinaryRadio.setToggleGroup(groupGender);

        //HBOX gender
        HBox hBoxGen = new HBox(maleRadio,femaleRadio,nonBinaryRadio);
        hBoxGen.setSpacing(10);
        hBoxGen.setAlignment(Pos.BASELINE_LEFT);

        //languages
        javaCheckBox.setIndeterminate(false);
        cppCheckBox.setIndeterminate(false);
        pythonCheckBox.setIndeterminate(false);

        //host family
        yes.setToggleGroup(hostFamilyGroup);
        no.setToggleGroup(hostFamilyGroup);

        //HBOX languages
        HBox hBoxLang = new HBox(javaCheckBox,cppCheckBox,pythonCheckBox);
        hBoxLang.setSpacing(20);
        hBoxLang.setAlignment(Pos.BASELINE_LEFT);

        //education
        data.addAll("No Formal Education","Current Student", "Associate Degree", "Bachelor Degree", "Master Degree", "PhD.");
        edulist.setItems(data);
        edulist.setPrefSize(100, 100);

        //location
        locationChoiceBox.getItems().addAll("Community College", "State College", "University of California", "Self Taught");
        locationChoiceBox.getSelectionModel().select(0);

        //listview from binary file
        studentsLV.setPrefSize(200, 300);

        //arranging all the nodes in the grid
        gridPane.add(nameLabel, 1, 1);
        gridPane.add(nameText, 2, 1);
        gridPane.add(dobLabel, 1, 2);
        gridPane.add(datePicker, 2, 2);
        gridPane.add(genderLabel,1,3);

        gridPane.add(countryLabel, 1, 7);
        gridPane.add(countryText, 2, 7);

        gridPane.add(hostFamilyLabel, 1, 8);
        gridPane.add(yes, 2, 8);
        gridPane.add(no, 2, 9);

        gridPane.add(hBoxGen, 2, 3);

        gridPane.add(languagesLabel, 1, 4);

        //add lang hbox
        gridPane.add(hBoxLang, 2,4);

        gridPane.add(educationLabel, 1 ,5);
        gridPane.add(edulist, 2, 5);

        gridPane.add(locationLabel, 1, 6);
        gridPane.add(locationChoiceBox, 2, 6);

        //register button
        buttonRegister.setOnAction(e -> addStudent());
        gridPane.add(buttonRegister, 4, 7);

        //delete button
        buttonDelete.setOnAction(e -> removeStudent());
        gridPane.add(buttonDelete, 4, 8);

        //listview from binary file
        studentsLV.setPrefSize(350, 300);
        gridPane.add(studentsLV, 4, 2);

        //NODE STYLE
        buttonRegister.setStyle("-fx-background-color:green; -fx-text-fill:white;");
        buttonDelete.setStyle("-fx-background-color:red; -fx-text-fill:white;");
        nameLabel.setStyle("-fx-font:normal bold 20px 'arial' ");
        dobLabel.setStyle("-fx-font:normal bold 20px 'arial' ");
        genderLabel.setStyle("-fx-font:normal bold 20px 'arial' ");
        languagesLabel.setStyle("-fx-font:normal bold 20px 'arial' ");
        educationLabel.setStyle("-fx-font:normal bold 20px 'arial' ");
        locationLabel.setStyle("-fx-font:normal bold 20px 'arial' ");
        hostFamilyLabel.setStyle("-fx-font:normal bold 20px 'arial' ");
        countryLabel.setStyle("-fx-font:normal bold 20px 'arial' ");

        //setting the background color
        gridPane.setStyle("-fx-background-color: linear-gradient(to right, cyan, blue)");

        studentsList = controller.getAllStudents();
        studentsLV.setItems(studentsList);

        studentsLV.getSelectionModel().selectedItemProperty().addListener((obsVal, oldVal, newVal) -> selectStudent(newVal));


        buttonBack.setOnAction(e -> ViewNavigator.loadScene("Registration Form", new MainScene()));

        gridPane.add(buttonBack, 5, 1);

        this.setRoot(gridPane);

    }
    private void selectStudent(Student newVal) {
        selectedStudent = newVal;
        buttonDelete.setDisable(selectedStudent == null);
    }

    private void removeStudent() {
        studentsList.remove(selectedStudent);
        studentsLV.refresh();
        studentsLV.getSelectionModel().select(-1);
    }
    private void addStudent() {
        try {
            String name = "["+nameText.getText()+"]";
            String DOB = "["+datePicker.getValue().format(DateTimeFormatter.ofPattern("MM/dd/yyyy"))+"]";
            String gender = "";
            if(groupGender.getSelectedToggle().equals(maleRadio))
                gender += "[MALE]";
            if(groupGender.getSelectedToggle().equals(femaleRadio))
                gender += "[FEMALE]";
            if(groupGender.getSelectedToggle().equals(nonBinaryRadio))
                gender += "[NON-BINARY]";
            String country = "["+countryText.getText()+"]";
            boolean host = (yes.isSelected());
            String languages = "";
            if (javaCheckBox.isSelected())
                languages+="[JAVA]";
            if (cppCheckBox.isSelected())
                languages+="[C++]";
            if (pythonCheckBox.isSelected())
                languages+="[Python]";
            String education = edulist.getSelectionModel().getSelectedItems().toString();
            String location = "["+locationChoiceBox.getSelectionModel().getSelectedItem().toString()+"]";
            studentsList.add(0, new InternationalStudent(name, DOB, gender, languages, education, location, country, host));
            studentsLV.refresh();
        }catch(NullPointerException e){}
    }

    private void updateDisplay() {
        studentsLV.refresh();
    }
}
