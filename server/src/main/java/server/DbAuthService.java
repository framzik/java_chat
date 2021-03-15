package server;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class DbAuthService implements AuthService {

  private Connection connection;
  private Statement statement;

  public DbAuthService() {
    try {
      connect();
    } catch (ClassNotFoundException | SQLException e) {
      e.printStackTrace();
    }
  }

  @Override
  public String getNicknameByLoginAndPassword(String login, String password) {
    String nickname = null;
    try {
      PreparedStatement psFindLogin = connection
          .prepareStatement("SELECT nickname FROM users where login = ? AND password =?; ");
      psFindLogin.setString(1, login);
      psFindLogin.setString(2, password);
      nickname = psFindLogin.executeQuery().getString("nickname");
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    return nickname;
  }

  @Override
  public boolean registration(String login, String password, String nickname) {
    boolean ok = false;
    try {
      PreparedStatement psInsert = connection
          .prepareStatement("INSERT INTO users (login,password,nickname) values (?,?,?);");
      psInsert.setString(1, login);
      psInsert.setString(2, password);
      psInsert.setString(3, nickname);
      if (psInsert.executeUpdate() != 0) {
        ok = true;
      }
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    return ok;
  }

  @Override
  public boolean updNicName(String newNickName, String login) {
    boolean ok = false;
    try {
      PreparedStatement psUpdNicName = connection
          .prepareStatement("UPDATE users SET nickname = ? WHERE login = ?;");
      psUpdNicName.setString(1, newNickName);
      psUpdNicName.setString(2, login);
      if (psUpdNicName.executeUpdate() != 0) {
        ok = true;
      }
    } catch (SQLException throwable) {
      throwable.printStackTrace();
    }
    return ok;
  }


  private void connect() throws ClassNotFoundException, SQLException {
    Class.forName("org.sqlite.JDBC");
    connection = DriverManager.getConnection("jdbc:sqlite:java3.db");
    statement = connection.createStatement();
  }

  @Override
  public void disconnect() {
    try {
      statement.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    try {
      connection.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
