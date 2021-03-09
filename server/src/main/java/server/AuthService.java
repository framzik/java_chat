package server;

public interface AuthService {

  String getNicknameByLoginAndPassword(String login, String password);

  boolean registration(String login, String password, String nickname);

  boolean updNicName(String nickName, String login);

  void disconnect();
}
