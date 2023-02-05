package core;

import models.Expression;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class ExpressionistImpl implements Expressionist {
    private final Map<String, Expression> expressionById;

    public ExpressionistImpl() {
        this.expressionById = new TreeMap<>();
    }

    @Override
    public void addExpression(Expression expression) {
        if (this.expressionById.size() > 0) {
           throw new IllegalArgumentException();
        }

        this.expressionById.put(expression.getId(), expression);
    }

    @Override
    public void addExpression(Expression expression, String parentId) {
        Expression currentParentExpr = this.expressionById.get(parentId);

        if (currentParentExpr == null) {
            throw new IllegalArgumentException();
        }

        if (currentParentExpr.getLeftChild() == null) {
            currentParentExpr.setLeftChild(expression);
        } else if (currentParentExpr.getRightChild() == null){
            currentParentExpr.setRightChild(expression);
        } else {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public boolean contains(Expression expression) {
        return this.expressionById.containsValue(expression);
    }

    @Override
    public int size() {
        return this.expressionById.size();
    }

    @Override
    public Expression getExpression(String expressionId) {
        Expression result = this.expressionById.get(expressionId);

        if (result == null) {
            throw new IllegalArgumentException();
        }

        return result;
    }

    @Override
    public void removeExpression(String expressionId) {
        Expression result = this.expressionById.get(expressionId);

        if (result == null) {
            throw new IllegalArgumentException();
        }

        if (result.getLeftChild() != null) {
            this.removeExpression(result.getLeftChild().getId());
        }

        if (result.getRightChild() != null) {
            this.removeExpression(result.getRightChild().getId());
        }

        this.expressionById.remove(result.getId());

        for (Map.Entry<String, Expression> entry : this.expressionById.entrySet()) {
            Expression leftChild = entry.getValue().getLeftChild();
            Expression rightChild = entry.getValue().getRightChild();

            if (leftChild == null && rightChild != null) {
                leftChild = rightChild;
                rightChild = null;
            }

        }
    }

    @Override
    public String evaluate() {
        return null;
    }
}
