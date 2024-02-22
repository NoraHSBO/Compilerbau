import java.util.List;


public class Parser {
    private List<Token> tokens;
    private int current = 0;

    public Parser(List<Token> tokens) {
        this.tokens = tokens;
    }

    public Node parse() {
        Node node = expression();
        return node;
    }

    //
    private Node expression() {
        Node left = term();
        while (match(TYPE.PLUS, TYPE.MINUS)) {
            Token operator = previous();
            Node right = term();
            left = new BinaryNode(left, operator, right);
        }
        return left;
    }

    private Node term() {
        Node left = factor();
        while (match(TYPE.MULT, TYPE.DIV)) {
            Token operator = previous();
            Node right = factor();
            left = new BinaryNode(left, operator, right);
        }
        return left;
    }

   private Node factor() {
      /*  if (match(TYPE.PLUS, TYPE.MINUS)) {
            Token operator = previous();
            Node right = factor();
            Node node = new UnaryNode(operator, right);
            return node;
        } else if (match(TYPE.INTEGER, TYPE.FLOAT)) {
            Token value = previous();
            Node node = new ValueNode(value);
            return node;
        } else if (match(TYPE.LEFT_PAREN)) {
            Node expression = this.expression();
            consume(TYPE.RIGHT_PAREN, "Expect ')' after expression.");
            return expression;
        }
        throw new RuntimeException("Expect expression, but found " + peek().type + ""); */
       return new BinaryNode();
    }

    private boolean match(TYPE... types) {
        for (TYPE type : types) {
            if (check(type)) {
                advance();
                return true;
            }
        }
        return false;
    }

    private Token consume(TYPE type, String message) {
        if (check(type))
            return advance();
        throw new RuntimeException(message);
    }

    private boolean check(TYPE type) {
        if (isAtEnd())
            return false;
        return peek().zustand == type;
    }

    private Token advance() {
        if (!isAtEnd())
            current++;
        return previous();
    }

    private boolean isAtEnd() {
        return current==tokens.size()-1;
    }

    private Token peek() {
        return tokens.get(current);
    }

    private Token previous() {
        return tokens.get(current - 1);
    }
}