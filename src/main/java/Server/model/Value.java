package Server.model;

public class Value {
  private String publisher;
  private String sheet;
  private Integer id;
  private String payload;

  public Value(String publisher, String sheet, Integer id, String payload) {
    this.publisher = publisher;
    this.sheet = sheet;
    this.id = id;
    this.payload = payload;
  }
}