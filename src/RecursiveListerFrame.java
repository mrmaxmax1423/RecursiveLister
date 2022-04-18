import javax.swing.*;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.nio.file.Files;

public class RecursiveListerFrame extends JFrame
{
    JPanel mainPanel;
    JLabel guiLabel;

    JPanel controlPanel;
    JButton startButton;
    JButton quitButton;

    JPanel displayPanel;
    JLabel resultsLabel;
    JTextArea resultsTextArea;
    JScrollPane resultsTextScroll;

    public RecursiveListerFrame()
    {
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        guiLabel = new JLabel("Found Files and Directories:");
        mainPanel.add(guiLabel);

        createControlPanel();
        mainPanel.add(controlPanel, BorderLayout.NORTH);

        createDisplayPanel();
        mainPanel.add(displayPanel, BorderLayout.CENTER);

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

    public void createDisplayPanel()
    {
        displayPanel = new JPanel();
        resultsTextArea = new JTextArea(15,50);
        resultsLabel = new JLabel("Found Files and Directories:");

        resultsTextArea.setFont(new java.awt.Font("Serif", 0, 20));

        resultsTextScroll = new JScrollPane(resultsTextArea);

        displayPanel.add(resultsLabel);
        displayPanel.add(resultsTextScroll);
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
        }
        displayFiles(chosenDirectory);
    }

    public void displayFiles(File directoryToSearch)
    {
        if(directoryToSearch != null) { //make sure code isn't ran if user closes search without picking file
            String[] pathnames;
            File tempDirectory;
            pathnames = directoryToSearch.list();
            for (String pathname : pathnames) {
                tempDirectory = new File(directoryToSearch.getAbsolutePath() + "\\" + pathname); //set a new File equal to current file being checked
                if (tempDirectory.isDirectory()) //if the temp file is a directory
                {
                    resultsTextArea.setText(resultsTextArea.getText() + "Entering Subdirectory: " + tempDirectory.getAbsolutePath() + "\n"); //Show when a new directory is being checked
                    displayFiles(tempDirectory); //recurse on the directory
                }
                else
                {
                    resultsTextArea.setText(resultsTextArea.getText() + "File: " + pathname + "\n"); //if the File isn't a directory, print the file path
                }
            }
        }
    }

}
