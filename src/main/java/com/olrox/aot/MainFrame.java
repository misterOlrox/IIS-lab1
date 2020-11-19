package com.olrox.aot;

import com.olrox.aot.lib.ChooseAnswer;
import com.olrox.aot.lib.logic.Attribute;
import com.olrox.aot.lib.logic.RuleSet;
import com.olrox.aot.lib.parser.JsonParser;
import org.apache.commons.lang3.tuple.Pair;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class MainFrame extends JFrame {

    private JPanel rootPanel;
    private JPanel buttonPanel;
    private JLabel attributeLabel;
    private JButton startButton;

    private ArrayList<RuleSet> ruleSets;
    private ArrayList<Attribute> attributes;

    public MainFrame() throws IOException {
        super("Pokemons");
        setContentPane(rootPanel);

        ruleSets = JsonParser.parseRules(
                Files.readString(Paths.get("./src/main/resources/question.json"))
        );
        attributes = JsonParser.parseAttrs(
                Files.readString(Paths.get("./src/main/resources/rules.json"))
        );

        startButton = new JButton("Start");
        buttonPanel.add(startButton);
        attributeLabel.setText("Press 'Start' to start");

        startButton.addActionListener((l) -> {
            startButton.setVisible(false);
            startGame("club");
        });

        setMinimumSize(new Dimension(500, 500));
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setVisible(true);
    }

    public void startGame(String finalTarget) {
        System.out.println("Program started");

        Stack<String> targetStack = new Stack<>();
        HashMap<Attribute, ContextValue> context = new HashMap<>();
        targetStack.push(finalTarget);
        boolean finished = false;

        while (!finished) {
            if (targetStack.isEmpty()) {
                finished = true;
                break;
            }

            String currentTarget = targetStack.peek();
            RuleSet currentRule = ruleSets.stream()
                    .filter(x -> x.getThen().getAttr().equals(currentTarget))
                    .findFirst()
                    .orElse(null);

            if (currentRule != null) {
                String target = targetStack.pop();
                if (target.equals(finalTarget)) {
                    break;
                } else {
                    ArrayList<String> options = attributes.stream()
                            .filter(x -> x.getName().equals(target))
                            .findFirst()
                            .get().getValues();
                    String answer = askQuestion(target, options);
                    contextStack.push(Pair.of(target, answer));
                }
            } else {

            }
        }
    }

    public String askQuestion(String target, ArrayList<String> options) {
        ChooseAnswer chooseAnswer = new ChooseAnswer(target, options);
        chooseAnswer.setVisible(true);
        return chooseAnswer.getAnswer();
    }

    public static void main(String[] args) throws IOException {
        MainFrame mainFrame = new MainFrame();
        mainFrame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
