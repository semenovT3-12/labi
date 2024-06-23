package lab3;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.Optional;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

public class Controller {
    private String dbUser = "root";
    private String dbPassword = "кщще";
    @FXML
    public Label id;
    @FXML
    public Label name;
    @FXML
    public Label width;
    @FXML
    public Label lenth;
    @FXML
    public Label region;
    @FXML
    public TextField idtext;
    @FXML
    public TextField nametext;
    @FXML
    public TextField widthtext;
    @FXML
    public TextField lenthtext;
    @FXML
    public ComboBox regioncombo;
    @FXML
    public Label photo;
    @FXML
    public Button insertbutton;
    @FXML
    public Button updatebutton;
    @FXML
    public Button clearbutton;
    @FXML
    public Button printbutton;
    @FXML
    public Button deletebutton;
    @FXML
    public ImageView ImageView;
    @FXML
    public TableView BDTable;
    @FXML
    public TableColumn<Model, Integer> idColumn;
    @FXML
    public TableColumn<Model, Double> longitudeColumn;
    @FXML
    public TableColumn<Model, Double> latitudeColumn;
    @FXML
    public TableColumn<Model,String> nameColumn;
    @FXML
    public TableColumn<Model,String> regionColumn;
    @FXML
    public TableColumn<Model,String> PictureColumn;
    @FXML
    public Button uploadButton;
    @FXML
    public Button cancelButton;
    @FXML
    private String photoPath;

    public Controller() {
    }

    @FXML
    public void initialize() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab", this.dbUser, this.dbPassword);
            Statement st = con.createStatement();
            String query = "SELECT name FROM regions";
            ResultSet resultSet = st.executeQuery(query);
            ObservableList<String> regionNames = FXCollections.observableArrayList();
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<>("nameSight"));
            latitudeColumn.setCellValueFactory(new PropertyValueFactory<>("latitude"));
            longitudeColumn.setCellValueFactory(new PropertyValueFactory<>("longitude"));
            regionColumn.setCellValueFactory(new PropertyValueFactory<>("region"));
            PictureColumn.setCellValueFactory(new PropertyValueFactory<>("picture"));
            while(resultSet.next()) {
                String regionName = resultSet.getString("name");
                regionNames.add(regionName);
            }

            this.regioncombo.getItems().addAll(regionNames);
            st.close();
            con.close();
        } catch (SQLException | ClassNotFoundException var7) {
            var7.printStackTrace();
            throw new RuntimeException(var7);
        }
    }

    @FXML
    public void uploadPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Оберіть фотографію");
        fileChooser.setInitialDirectory(new File("C:/Users/max/javaphoto"));
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter[]{new FileChooser.ExtensionFilter("Зображення", new String[]{"*.jpg", "*.png", "*.jpeg"})});
        File selectedFile = fileChooser.showOpenDialog(this.uploadButton.getScene().getWindow());
        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            this.ImageView.setImage(image);
            this.photoPath = selectedFile.getAbsolutePath();
            this.uploadButton.setVisible(false);
            this.uploadButton.setDisable(true);
            this.cancelButton.setDisable(false);
            this.cancelButton.setVisible(true);
        }

    }

    @FXML
    public void cancelPhotoSelection() {
        this.ImageView.setImage((Image)null);
        this.photoPath = "";
        this.uploadButton.setDisable(false);
        this.cancelButton.setDisable(true);
        this.uploadButton.setVisible(true);
        this.cancelButton.setVisible(false);
    }

    @FXML
    private void handlePrintButton() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab", this.dbUser, this.dbPassword);
            Statement st = con.createStatement();
            String query = "SELECT id, name, latitude, longitude, region, photo_path FROM sights";
            ResultSet resultSet = st.executeQuery(query);
            ObservableList<Main> dataList = FXCollections.observableArrayList();

            while(resultSet.next()) {
                Integer id = resultSet.getInt("id");
                String nameSight = resultSet.getString("name");
                double latitude = resultSet.getDouble("latitude");
                double longitude = resultSet.getDouble("longitude");
                String region = resultSet.getString("region");
                String picture = resultSet.getString("photo_path");
                System.out.println("" + id + ", " + nameSight + ", " + latitude + ", " + longitude + ", " + region + ", " + picture);
                Model model = new Model(id, nameSight, latitude, longitude, region, picture);
                dataList.add(model);
            }

            System.out.println("Кількість стовпців у BDTable: " + this.BDTable.getColumns().size());
            this.BDTable.getItems().clear();
            System.out.println("Кількість елементів у dataList: " + dataList.size());
            this.BDTable.setItems(dataList);
            if (dataList.isEmpty()) {
                System.out.println("Список dataList порожній.");
            } else {
                System.out.println("Список dataList містить дані.");
            }

            st.close();
            con.close();
            System.out.println("Кількість елементів у dataList: " + dataList.size());
        } catch (SQLException | ClassNotFoundException var15) {
            throw new RuntimeException(var15);
        }
    }

    @FXML
    private void handleClearTable() {
        this.BDTable.getItems().clear();
        ObservableList<Main> emptyList = FXCollections.observableArrayList();
        this.BDTable.setItems(emptyList);
    }

    @FXML
    private void handleInsertButton() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab", this.dbUser, this.dbPassword);
            String query = "INSERT INTO sights (name, latitude, longitude, region, photo_path) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            String name = this.nametext.getText();
            double latitude = Double.parseDouble(this.widthtext.getText());
            double longitude = Double.parseDouble(this.lenthtext.getText());
            String region = (String)this.regioncombo.getValue();
            String photoPath = this.photoPath;
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, latitude);
            preparedStatement.setDouble(3, longitude);
            preparedStatement.setString(4, region);
            preparedStatement.setString(5, photoPath);
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Дані успішно вставлено в базу даних!");
            }

            preparedStatement.close();
            con.close();
        } catch (SQLException | ClassNotFoundException var12) {
            var12.printStackTrace();
            throw new RuntimeException(var12);
        }

        this.handlePrintButton();
    }

    @FXML
    private void handleDeleteButton() {
        try {
            ObservableList<Model> selectedModels = this.BDTable.getSelectionModel().getSelectedItems();
            Alert confirmAlert;
            if (selectedModels.isEmpty()) {
                confirmAlert = new Alert(AlertType.WARNING);
                confirmAlert.setTitle("Warning");
                confirmAlert.setHeaderText("No rows selected");
                confirmAlert.setContentText("Please select rows to delete.");
                confirmAlert.showAndWait();
                return;
            }

            confirmAlert = new Alert(AlertType.CONFIRMATION);
            confirmAlert.setTitle("Confirmation");
            confirmAlert.setHeaderText("Delete Confirmation");
            confirmAlert.setContentText("Ви впевнені, що бажаєте видалити обрані рядки?");
            Optional<ButtonType> result = confirmAlert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab", this.dbUser, this.dbPassword);
                String query = "DELETE FROM sights WHERE id=?";
                PreparedStatement preparedStatement = con.prepareStatement(query);
                Iterator var7 = selectedModels.iterator();

                while(var7.hasNext()) {
                    Model selectedModel = (Model)var7.next();
                    preparedStatement.setInt(1, selectedModel.getId());
                    preparedStatement.executeUpdate();
                }

                this.BDTable.getItems().removeAll(selectedModels);
                preparedStatement.close();
                con.close();
            }
        } catch (SQLException | ClassNotFoundException var9) {
            var9.printStackTrace();
            throw new RuntimeException(var9);
        }

        this.handleTableSelection();
    }

    @FXML
    private void handleTableSelection() {
        if (this.BDTable.getSelectionModel().getSelectedItem() != null) {
            this.BDTable.getSelectionModel().clearSelection();
        }

    }

    @FXML
    private void handleClearFieldsButton() {
        this.idtext.clear();
        this.nametext.clear();
        this.widthtext.clear();
        this.lenthtext.clear();
        this.regioncombo.getSelectionModel().clearSelection();
        this.ImageView.setImage((Image)null);
        this.cancelPhotoSelection();
    }

    @FXML
    private void handleTableSelectionFillFields() {
        ObservableList<Model> selectedModels = (ObservableList)this.BDTable.getSelectionModel().getSelectedItem();
        Model selectedModel = (Model)this.BDTable.getSelectionModel().getSelectedItem();
        if (selectedModel != null) {
            int id = selectedModel.getId();
            String name = selectedModel.getNameSight();
            double latitude = selectedModel.getLatitude();
            double longitude = selectedModel.getLongitude();
            String region = selectedModel.getRegion();
            String picture = selectedModel.getPicture();
            this.idtext.setText(String.valueOf(id));
            this.nametext.setText(name);
            this.widthtext.setText(String.valueOf(latitude));
            this.lenthtext.setText(String.valueOf(longitude));
            this.regioncombo.setValue(region);
            if (picture != null && !picture.isEmpty()) {
                File imageFile = new File(picture);
                if (imageFile.exists()) {
                    Image image = new Image(imageFile.toURI().toString());
                    this.photoPath = imageFile.getAbsolutePath();
                    this.uploadButton.setVisible(false);
                    this.uploadButton.setDisable(true);
                    this.cancelButton.setDisable(false);
                    this.cancelButton.setVisible(true);
                    this.ImageView.setImage(image);
                } else {
                    System.out.println("Файл зображення не знайдено: " + picture);
                    this.ImageView.setImage((Image)null);
                    this.photoPath = "";
                    this.uploadButton.setDisable(false);
                    this.cancelButton.setDisable(true);
                    this.uploadButton.setVisible(true);
                    this.cancelButton.setVisible(false);
                }
            } else {
                System.out.println("Шлях до зображення не вказано або порожній.");
                this.ImageView.setImage((Image)null);
                this.photoPath = "";
                this.uploadButton.setDisable(false);
                this.cancelButton.setDisable(true);
                this.uploadButton.setVisible(true);
                this.cancelButton.setVisible(false);
            }
        }

    }

    @FXML
    private void handleUpdateButton() {
        try {
            Model selectedModel = (Model)this.BDTable.getSelectionModel().getSelectedItem();
            if (selectedModel == null) {
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("No row selected");
                alert.setContentText("Please select a row to update.");
                alert.showAndWait();
                return;
            }

            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/lab", this.dbUser, this.dbPassword);
            String query = "UPDATE sights SET name=?, latitude=?, longitude=?, region=?, photo_path=? WHERE id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);
            String name = this.nametext.getText();
            double latitude = Double.parseDouble(this.widthtext.getText());
            double longitude = Double.parseDouble(this.lenthtext.getText());
            String region = (String)this.regioncombo.getValue();
            String photoPath = this.photoPath;
            int id = selectedModel.getId();
            preparedStatement.setString(1, name);
            preparedStatement.setDouble(2, latitude);
            preparedStatement.setDouble(3, longitude);
            preparedStatement.setString(4, region);
            preparedStatement.setString(5, photoPath);
            preparedStatement.setInt(6, id);
            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Дані успішно оновлено в базі даних!");
            }

            preparedStatement.close();
            con.close();
        } catch (SQLException | ClassNotFoundException var14) {
            var14.printStackTrace();
            throw new RuntimeException(var14);
        }

        this.handlePrintButton();
    }
}
