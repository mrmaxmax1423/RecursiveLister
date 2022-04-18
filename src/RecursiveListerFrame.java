import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;

public class RecursiveListerFrame extends JFrame
{
    JPanel mainPanel;

    JPanel controlPanel;
    JButton startButton;
    JButton quitButton;

    public RecursiveListerFrame()
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.NORTH);

        add(mainPanel);
        setSize(1000,600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }

    public void createControlPanel()
    {
        controlPanel = new JPanel();
        startButton = new JButton("Start");
        quitButton = new JButton("Quit");
        startButton.addActionListener((ActionEvent ae) -> chooseDirectory());
        quitButton.addActionListener((ActionEvent ae) -> System.exit(0));
        startButton.setFont(new java.awt.Font("Serif", 0, 20));
        quitButton.setFont(new java.awt.Font("Serif", 0, 20));

        controlPanel.add(startButton);
        controlPanel.add(quitButton);
    }
    File chosenDirectory;
    public void chooseDirectory()
    {
        JFileChooser directoryChooser = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        directoryChooser.setDialogTitle("Choose a directory to save your file: ");
        directoryChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int returnValue = directoryChooser.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            if (directoryChooser.getSelectedFile().isDirectory()) {
                chosenDirectory = directoryChooser.getSelectedFile();
            }
            //System.out.println(chosenDirectory.getAbsolutePath());
            displayFiles(chosenDirectory);
        }
    }

    public void displayFiles(File directoryToSearch)
    {
        String[] pathnames;
        File tempDirectory;
        pathnames = directoryToSearch.list();
        for (String pathname : pathnames) {
            tempDirectory = new File(directoryToSearch.getAbsolutePath() + "\\" + pathname);
            if(tempDirectory.isDirectory())
            {
                displayFiles(tempDirectory);
            }
            System.out.println(tempDirectory.getAbsolutePath());
        }
    }

}
