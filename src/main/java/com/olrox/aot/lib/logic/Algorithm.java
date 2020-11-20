package com.olrox.aot.lib.logic;

import com.olrox.aot.lib.ChooseAnswer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;


class Algorithm {
    private StartAction startAction;
    private KnowledgeBase base;
    private Stack<TargetValue> targets = new Stack<>();
    private HashMap<Attribute, ContextValue> context = new HashMap<>();
    private boolean isFinished;

    Algorithm(StartAction startAction, KnowledgeBase base) {
        this.startAction = startAction;
        this.base = base;
    }

    private String nextQuestion(Attribute target) {
        String[] choices = new String[target.possibleValues.size()];
        target.possibleValues.toArray(choices);

        ChooseAnswer chooseAnswerDialog = new ChooseAnswer(target.question, Arrays.asList(choices));
        chooseAnswerDialog.setVisible(true);
        String input = chooseAnswerDialog.getAnswer();

        if (input == null) {
            startAction.writeLine("User canceled data input");
        }

        return input;
    }

    void startAlgo(Attribute target) {
        targets.clear();
        context.clear();
        targets.push(new TargetValue(target, null));
        isFinished = false;
        while (!isFinished) {
            if (targets.empty()) {
                isFinished = true;
                break;
            }
            Attribute current = targets.peek().attribute;
            Rule toAnalize = base.findNextRule(current);
            if (toAnalize != null) {// can find rule
                AnalyzeRule(toAnalize);
            } else {
                if (current.question != null) {
                    String res = nextQuestion(current);
                    if (res == null) {
                        return;
                    }
                    if (!targets.empty()) {
                        toAnalize = targets.pop().rule;
                    }
                    context.put(current, new ContextValue(res, null));
                    startAction.writeLine("Ответ: [" + current + " = " + res + "]\n");
                    if (toAnalize != null) {
                        AnalyzeRule(toAnalize);
                    }
                } else {
                    isFinished = true;
                }
            }
        }
        String result = getTargetValue(target);
        if (result != null) {
            startAction.writeLine("Ответ: [" + target + " = " + result + "]\n");
        } else {
            startAction.writeLine("Нельзя найти ответ!");
        }
    }

    private String getTargetValue(Attribute target) {
        if (!context.containsKey(target)) {
            return null;
        }
        return context.get(target).value;
    }

    private Boolean AnalyzeRule(Rule rule) {
        boolean res = true;
        for (Map.Entry<Attribute, String> entry : rule.conditions.entrySet()) {
            Boolean isRight = checkAttribute(entry.getKey(), entry.getValue());
            if (isRight == null) {
                targets.push(new TargetValue(entry.getKey(), rule));
                startAction.writeLine("Правило №" + rule + " НЕИЗВЕСТНО! [" + entry.getKey() + "]");
                return null;
            } else if (!isRight) {
                startAction.writeLine("Правило №" + rule + " ЛОЖНО! [" + entry.getKey() + " != " + entry.getValue() + "]");
                res = false;
                rule.isAnalyzed = true;
                break;
            }
        }
        if (res) {
            context.put(rule.targetAttribute, new ContextValue(rule.targetValue, rule));
            startAction.writeLine("Правило №" + rule + " ИСТИННО! [" + rule.targetAttribute + " = " + rule.targetValue + "]");
            rule.isAnalyzed = true;
            if (targets.empty()) {
                isFinished = true;
            } else {
                targets.pop();
            }
        }
        rule.isCorrect = res;
        return res;
    }

    private Boolean checkAttribute(Attribute att, String val) {
        if (!context.containsKey(att)) {
            return null;
        } else {
            return context.get(att).value.equals(val);
        }
    }

    private class ContextValue {
        String value;
        Rule rule;

        ContextValue(String value, Rule rule) {
            this.value = value;
            this.rule = rule;
        }
    }

    private class TargetValue {
        Attribute attribute;
        Rule rule;

        TargetValue(Attribute attribute, Rule rule) {
            this.attribute = attribute;
            this.rule = rule;
        }
    }

}
