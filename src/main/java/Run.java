import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.io.File;

public class Run {

    public static String pathToRules = "rules.txt";
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame();
            initFrameDim(frame);
            StartAction startAction = new StartAction();
            initApplication(frame, startAction, WindowConstants.EXIT_ON_CLOSE);
        });
    }

    private static void initFrameDim(JFrame frame) {
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        frame.setSize(400, (int) (screen.getHeight() * .5));
        frame.setLocation(screen.width/2 - frame.getSize().width/2, screen.height/2 - frame.getSize().height/2);
        frame.setResizable(true);
    }

    private static void initApplication(JFrame frame, StartAction startAction, final int onCloseOperation) {
        frame.setTitle("МАШИНА " +
                "ДЕДУКТИВНОГО " +
                "ВЫВОДА");
        frame.setDefaultCloseOperation(onCloseOperation);
        frame.getContentPane().add(startAction.getMasterComponent(), BorderLayout.CENTER);
        JPanel panel = new JPanel();
        JButton openFile = new JButton("Open File");
        JButton start = new JButton("Start");

        openFile.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("/home/qeeqez/IdeaProjects/IIS/"));
            int returnVal = chooser.showOpenDialog(frame);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                System.out.println("You chose to open this file: " +
                        chooser.getSelectedFile().getAbsolutePath());
                pathToRules = chooser.getSelectedFile().getAbsolutePath();
            }
        });

        start.addActionListener(startAction);
        panel.add(openFile);
        panel.add(start);
        frame.getContentPane().add(panel, BorderLayout.SOUTH);
        frame.setVisible(true);
    }
}
