abstract class Node {
}

class UnaryNode extends Node {
    String value;

    UnaryNode(String value) {
        this.value = value;
    }
}

class BinaryNode extends Node {
    Node left;
    Node right;
    Token operator;

    BinaryNode(){

    }

    BinaryNode(Node left, Token operator, Node right) {
        this.left = left;
        this.operator = operator;
        this.right = right;
    }
}
