import javax.swing.JOptionPane;
public class Compiler{
    public static void main(String[] args){
        String input = JOptionPane.showInputDialog("Enter the roman numeral expression");

        Lexer lexer = new Lexer(input);
        lexer.scan(); // Lexer Ergebnis
        
        Converter converter = new Converter(lexer.getResult());
        converter.convert(); // Converter Ergebnis
    }
}