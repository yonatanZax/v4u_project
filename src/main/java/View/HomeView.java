package View;

import impl.org.controlsfx.skin.DecorationPane;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import javafx.scene.layout.Pane;
import org.controlsfx.control.StatusBar;

import java.util.Observable;

public class HomeView extends Observable {


    // Static names
    public static final String HOMEVIEW_AGRS_LOGIN = "Login";
    public static final String HOMEVIEW_AGRS_MESSAGECENER = "MessageCenter";
    public static final String HOMEVIEW_AGRS_GOBACK = "GoBack";

    private Parent defaultParent;
    private boolean firstScene = true;


    @FXML
    Button login_btn;

    @FXML
    ImageView message_iv;

    @FXML
    Label login_status_lbl;

    @FXML
    StatusBar status_bar;

    @FXML
    DecorationPane decorationPane;


    @FXML
    public void initialize() {
        setLoginStatusLabel(null);
    }

    public void setSub_scene(Parent sub_scene) {
        if (firstScene){
            defaultParent = sub_scene;
            firstScene = false;
        }
        this.decorationPane.getChildren().clear();
        this.decorationPane.getChildren().add(sub_scene);
        this.decorationPane.setPrefSize(decorationPane.getMaxWidth(), decorationPane.getMaxHeight());

    }

    /**
     * changes the login status in the home view
     * if userName is null: it means we are doing logout
     * if userName isn't null: it means we successfully logged in
     * @param newLabel the name of the new user or null it it's logout
     */
    public void setLoginStatusLabel(String newLabel){
        if (newLabel == null){
            login_btn.setText("Login");
            login_status_lbl.setText("Not Signed");
            message_iv.setDisable(true);
            if(!firstScene)
                setSub_scene(defaultParent);
        }
        else {
            login_btn.setText("Logout");
            login_status_lbl.setText("Signed As: " + newLabel);
            message_iv.setDisable(false);
        }
    }

    @FXML
    public void logInOnAction(){
        setChanged();
        notifyObservers(HOMEVIEW_AGRS_LOGIN);
    }

    @FXML
    public void messageOnAction(){
        setChanged();
        notifyObservers(HOMEVIEW_AGRS_MESSAGECENER);
    }

}
