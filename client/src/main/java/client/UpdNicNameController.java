package client;

import commands.Command;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class UpdNicNameController {

  @FXML
  public TextField nicknameFieldUpd;
  @FXML
  public TextArea textAreaUpd;
  private String nickname;

  private Controller controller;

  public void setController(Controller controller) {
    this.controller = controller;
  }

  public void updateNickName(ActionEvent actionEvent) {
    nickname = nicknameFieldUpd.getText().trim();
    if (nickname.length() == 0) {
      textAreaUpd.appendText("Новый никнейм не может быть пустым!\n");
      return;
    }
    controller.tryUpd(nickname);
  }

  public void setResultTryToUpdNick(String command) {
    if (command.equals(Command.UPD_OK)) {
      textAreaUpd.appendText("Обновление никнейма прошло успешно\n");
      controller.setTitle(nickname);
    }
    if (command.equals(Command.UPD_NO)) {
      textAreaUpd.appendText("Такой никнейм уже занят\n");
    }
  }
}
