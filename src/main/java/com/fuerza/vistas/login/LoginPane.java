package com.fuerza.vistas.login;

import com.fuerza.vistas.FxController;
import com.fuerza.vistas.MainApp.OperacionesSistema;
import com.fuerza.vistas.StageManager;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import net.rgielen.fxweaver.core.FxmlView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Controller;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
@FxmlView
public class LoginPane implements Initializable, FxController {

    @FXML
    private Button btnLogin;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    @FXML
    private Label lblLogin;

    @Lazy
    @Autowired
    private StageManager stageManager;

    @Override
    public void initialize(URL location, ResourceBundle resources)
    //se establece un usuario y contraseña
    {
        btnLogin.setOnAction(event -> {
            if (username.getText().equals("admin") && password.getText().equals("admin")) {
                stageManager.rebuildStage(OperacionesSistema.class);
            } else {
                lblLogin.setText("Usuario o contraseña incorrectos");
            }

        });

        password.setOnKeyPressed(event -> {
            if (event.getCode().equals(javafx.scene.input.KeyCode.ENTER)) {
                btnLogin.fire();
            }

        });
    }
}
