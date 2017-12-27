package com.mysapp.Setting;

import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import com.mysapp.LoginController;
import javafx.beans.property.SimpleStringProperty;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.*;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;

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

/**
 * Created by ahosa on 9/2/2017.
 */
public class Settings implements Initializable{
    Connection connection;

    @FXML
    Text Salary,user_name;
    @FXML
    JFXTextField Mobile,oldPassword,newPassword,rePassword,email,fullname;
    @FXML
    TextArea Address,permanentAddress;
    @FXML
    ImageView UserImage;
    @FXML
    JFXDatePicker birthday;

    final FileChooser fileChooser = new FileChooser();
    SimpleStringProperty getUserID;
    public Settings(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BufferedImage read = null;
        try {
            try {
                read = ImageIO.read(new File("./User Image/" + LoginController.All_UserID + "_User_Image.png"));
                Image image = SwingFXUtils.toFXImage(read, null);
                UserImage.setImage(image);
            }catch (Exception e){
                e.printStackTrace();
            }
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM `user` WHERE user_id='"+LoginController.All_UserID+"'");
            if(resultSet.next()){
                Salary.setText(resultSet.getString("salary"));
                user_name.setText(resultSet.getString("user_name"));
                Mobile.setText(resultSet.getString("phone"));
                DateTimeFormatter dateformate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate inpdate = LocalDate.parse(resultSet.getString("dateofbirth"),dateformate);
                birthday.setValue(inpdate);

                Address.setText(resultSet.getString("address"));
                email.setText(resultSet.getString("email"));
                fullname.setText(resultSet.getString("full_name"));
                permanentAddress.setText(resultSet.getString("permanent_address"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    public  void UpdateInfo() throws SQLException {
        Statement statement = connection.createStatement();
        DateTimeFormatter dateformate = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        statement.executeUpdate("UPDATE `user` SET `email`='"+email.getText()+"',`full_name`='"+fullname.getText()+"',`address`='"+Address.getText()+"',`permanent_address`='"+permanentAddress.getText()+"',`phone`='"+Mobile.getText()+"',`dateofbirth`='"+birthday.getValue().format(dateformate)+"' WHERE user_id='"+LoginController.All_UserID+"'");
        statement.close();
        Notifications notifications = Notifications.create()
                .title("Successful")
                .hideAfter(Duration.seconds(5))
                .position(Pos.TOP_RIGHT);
        notifications.text("Successfully Update.");
        notifications.show();
    }

    @FXML
    private void PasswordUpdate(){
        if(!oldPassword.getText().isEmpty()){
            try {
                Statement statement = connection.createStatement();
                ResultSet rs = statement.executeQuery("SELECT user.password FROM `user` WHERE user_id='"+LoginController.All_UserID+"'");
                if(rs.next()){
                    if(rs.getString("password").equals(oldPassword.getText())){
                        if(newPassword.getText().equals(rePassword.getText()) && newPassword.getText().length() > 3){
                            statement.executeUpdate("UPDATE `user` SET `password`='"+newPassword.getText()+"' WHERE user_id='"+LoginController.All_UserID+"'");
                            Notifications notifications = Notifications.create()
                                    .title("Successful")
                                    .hideAfter(Duration.seconds(5))
                                    .position(Pos.TOP_RIGHT);
                            notifications.text("Password Successfully Change.");
                            notifications.show();
                        }else {
                            Notifications notifications = Notifications.create()
                                    .title("Error")
                                    .hideAfter(Duration.seconds(5))
                                    .position(Pos.TOP_RIGHT);
                            notifications.text("New Password Not match!");
                            notifications.showWarning();
                        }
                    }else{
                        Notifications notifications = Notifications.create()
                                .title("Error")
                                .hideAfter(Duration.seconds(5))
                                .position(Pos.TOP_RIGHT);
                        notifications.text("Old Password is Not match!");
                        notifications.showWarning();
                    }
                }
                rs.close();
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }else{
            Notifications notifications = Notifications.create()
                    .title("Error")
                    .hideAfter(Duration.seconds(5))
                    .position(Pos.TOP_RIGHT);
            notifications.text("Old Password is Empty !");
            notifications.showWarning();
        }
    }

    @FXML
    private void PhotoUpdate(){
        saveToFile();
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

    @FXML
    private void UploadedImage() throws IOException {
        configureFileChooser(fileChooser);

        File file = fileChooser.showOpenDialog(null);
        if (file != null) {

            BufferedImage bufferedImage = ImageIO.read(file);
            int type = bufferedImage.getType() == 0? BufferedImage.TYPE_INT_ARGB : bufferedImage.getType();

            Image image = SwingFXUtils.toFXImage(resizeImage(bufferedImage, type), null);
            UserImage.setImage(image);
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

    private static BufferedImage resizeImage(BufferedImage originalImage, int type){
        BufferedImage resizedImage = new BufferedImage(500, 500, type);
        Graphics2D g = resizedImage.createGraphics();
        g.drawImage(originalImage, 0, 0, 500, 500, null);
        g.dispose();
        return resizedImage;
    }
}
