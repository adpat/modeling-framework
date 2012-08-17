package model_example;

public class Context extends model_base.Context {
  /**
   * For this example, the Context does not really need to do anything
   * differently from the model_base.Context
   */
  public Context(int id) {
    super(id);
  }
  
  public static void main(String[] args) {
    System.out.println("Java context subclass");
  }
}
