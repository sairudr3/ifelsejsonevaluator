package com.ifstatement.exam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Charsets;
import com.google.common.io.Files;

import java.io.File;
import java.util.Optional;

import static java.util.Objects.isNull;
public class IfStatementEvaluator {

    public static void main(String[] args) throws Exception {
        String fileContent = Files.asCharSource(new File("inputJson.json"), Charsets.UTF_8).read();
        var a = "abc";
        var b = 20;
        new IfStatementEvaluator().evaluateIfElseJson(a,b,fileContent);

    }

    public boolean evaluateIfElseJson(String varA, int varB, String input) throws JsonProcessingException {
        IfStatement ifStatement = new ObjectMapper().readValue(input, IfStatement.class);
        boolean res = false;
        while (!res) {
            if (isNull(ifStatement.getType())) {
                break;
            }

            //ensure all the conditions are satisfying otherwise, move to else block.
            Optional<Conditions> first = ifStatement.getConditions().stream()
                    .filter(e -> !IfStatementEvaluator.evaluateCondition(e,varA,varB))
                    .findFirst();

            //any one condition failed then move to else block
            res = first.isEmpty();
            if (!first.isEmpty()) {
                ifStatement = ifStatement.elseStatement;
            } else {
                break;
            }
        }
        System.out.println("Result: " + res);
        return res;
    }

    private static boolean evaluateCondition(Conditions conditions, String varA, int varB) {
        return evaluateIf(conditions.getConditionType(),
                getVariableValue(conditions.left,varA,varB), conditions.getRight());
    }

    private static Object getVariableValue(String left,String varA, int varB) {
        return getValue(left,varA,varB);
    }


    private static boolean evaluateIf(String conditionType, Object l, Object r) {
        try {
            if (l instanceof Number)
                r = Integer.parseInt(r.toString());
        } catch (Exception e) {
            System.out.println("Ignore different data type");
        }
        boolean b1 = !(l.getClass() == r.getClass());
        if (b1) return false;

        switch (conditionType) {
            case "==":
                return l.equals(r);
            case ">":
                return l instanceof Number
                        && Integer.valueOf(l.toString()) > Integer.valueOf(r.toString());
            case "<":
                return l instanceof Number
                        && Integer.valueOf(l.toString()) < Integer.valueOf(r.toString());
            default:
                throw new IllegalArgumentException("Invalid condition type: " + conditionType);
        }
    }


    private static Object getValue(String variable, String varA, int varB) {
        switch (variable) {
            case "a":
                return varA;
            case "b":
                return varB;
            default:
                throw new IllegalArgumentException("Invalid variable: " + variable);
        }
    }
}
