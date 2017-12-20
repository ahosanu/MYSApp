package com.mysapp.Employee;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;
import com.mysapp.LoginController;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.util.StringConverter;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class UpdateEmployee implements Initializable{
    Connection connection;
    int id;
    final FileChooser fileChooser = new FileChooser();
    boolean isSubmit = false;
    int isPhoto = 0;
    @FXML
    JFXTextField Emp_name,user_name,Email_id,mobile,salary;
    @FXML
    JFXComboBox<String> type,sex;
    @FXML
    JFXDatePicker BirthDate;
    @FXML
    JFXTextArea Address,Per_Address;
    @FXML
    ImageView UserImage;


    public UpdateEmployee(Connection connection, int id) {
        this.connection = connection;
        this.id = id;
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        sex.getItems().addAll("Male","Female");
        type.getItems().addAll("admin","manager","3 class","4 class");
        salary.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()){
                char c = salary.getText().charAt(oldValue.intValue());
                /** Check if the new character is the number or other's */
                if( c > '9' || c < '0'){
                    /** if it's not number then just setText to previous one */
                    salary.setText(salary.getText().substring(0,salary.getText().length()-1));
                }
            }
        });
        mobile.lengthProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue.intValue() > oldValue.intValue()){
                char c = mobile.getText().charAt(oldValue.intValue());
                /** Check if the new character is the number or other's */
                if( c > '9' || c < '0'){
                    /** if it's not number then just setText to previous one */
                    mobile.setText(mobile.getText().substring(0,mobile.getText().length()-1));
                }
            }
        });

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("SELECT * FROM `user` WHERE user_id='"+id+"'");
            if(rs.next()) {
                BufferedImage bufferedImage = null;
                Image image = null;
                try {
                    bufferedImage = ImageIO.read(new File("./User Image/" + id + "_User_Image.png"));
                    int type = bufferedImage.getType() == 0 ? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();
                    image = SwingFXUtils.toFXImage(resizeImage(bufferedImage, type), null);
                    isPhoto = 1;
                } catch (IOException e) {
                    image = new Image("image/noimg.png");
                }
                UserImage.setImage(image);
                user_name.setText(rs.getString("user_name"));
                Emp_name.setText(rs.getString("full_name"));
                mobile.setText(rs.getString("phone"));
                salary.setText(rs.getString("salary"));
                Address.setText(rs.getString("address"));
                Per_Address.setText(rs.getString("permanent_address"));
                Email_id.setText(rs.getString("email"));
                sex.getSelectionModel().select(rs.getString("sex"));
                type.getSelectionModel().select(rs.getString("type"));

                BirthDate.setConverter(new StringConverter<LocalDate>() {
                    String pattern = "dd/MM/yyyy";
                    DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);


                    @Override public String toString(LocalDate date) {
                        if (date != null) {
                            return dateFormatter.format(date);
                        } else {
                            return "";
                        }
                    }

                    @Override public LocalDate fromString(String string) {
                        if (string != null && !string.isEmpty()) {
                            return LocalDate.parse(string, dateFormatter);
                        } else {
                            return null;
                        }
                    }
                });
                String pattern = "dd/MM/yyyy";
                DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(pattern);
                LocalDate inpdate = LocalDate.parse(rs.getString("dateofbirth"),dateFormatter);
                BirthDate.setValue(inpdate);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }



    }

    @FXML
    private void UploadedImage() throws IOException {
        configureFileChooser(fileChooser);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {

            BufferedImage bufferedImage = ImageIO.read(file);
            int type = bufferedImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

            Image image = SwingFXUtils.toFXImage(resizeImage(bufferedImage, type), null);
            UserImage.setImage(image);
            isPhoto = 1;
        }
    }

    private static void configureFileChooser(final FileChooser fileChooser) {
        fileChooser.setTitle("View Pictures");
        fileChooser.setInitialDirectory(
                new File(System.getProperty("user.home"))
        );
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Images", "*.*"),
                new FileChooser.ExtensionFilter("JPG", "*.jpg"),
                new FileChooser.ExtensionFilter("PNG", "*.png")
        );
    }

    public void saveToFile() {
        String PATH = "./User Image/";
        String fileName = LoginController.All_UserID + "_User_Image.png";
        File directory = new File(PATH);
        if (! directory.exists()){
            directory.mkdir();
        }


        File outputFile = new File(PATH+fileName);
        BufferedImage bImage = SwingFXUtils.fromFXImage(UserImage.getImage(), null);
        try {
            ImageIO.write(bImage, "png", outputFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
        BufferedImage resizedImage = new BufferedImage(500, 500, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 500, 500, null);
        g.dispose();
        return resizedImage;
    }
    @FXML
    private void Submit(Event event){

        if(!user_name.getText().isEmpty() && !Emp_name.getText().isEmpty() && !mobile.getText().isEmpty() && !salary.getText().isEmpty() && !BirthDate.getValue().equals("") && !Address.getText().isEmpty() && !Per_Address.getText().isEmpty() && !mobile.getText().isEmpty()) {
            isSubmit = true;
            if(isPhoto == 1)
                saveToFile();
            ((Node) (event.getSource())).getScene().getWindow().hide();
        }else {
            System.out.println("Empty Field");
        }
    }

    @FXML
    private void  ResetPassword(){

    }
}
