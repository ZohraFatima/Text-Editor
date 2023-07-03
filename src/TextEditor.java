import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.UndoManager;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.*;

import static java.awt.SystemColor.text;
import static java.awt.SystemColor.window;

public class TextEditor implements ActionListener{
    JFrame frame;
    JMenuBar menuBar;
    JMenu file,edit,format,font,fontSize,Colors;
    //file menu items
    JMenuItem newFile,openFile,saveFile;
    //edit menu items
    JMenuItem cut,copy,paste,selectAll,close,undo,redo;
    JMenuItem wordWrap;
    JMenuItem fontArial,fontCSMS,fontTNR;
    JMenuItem fs8,fs12,fs16,fs20,fs24,fs28;
    JMenuItem white,black,blue;

    JTextArea textArea;
    boolean wordWrapOn=false;
    Font arial,cns,tnr;

    UndoManager um=new UndoManager();

    TextEditor(){
     frame =new JFrame();
     menuBar= new JMenuBar();
     file =new JMenu("File");
     edit=new JMenu("Edit");
     format=new JMenu("Format");
     font =new JMenu("Font");
     fontSize=new JMenu("Font Size");
     Colors=new JMenu("Colors");
     menuBar.add(file);
     menuBar.add(edit);
     menuBar.add(format);
     format.add(font);
     format.add(fontSize);
     menuBar.add(Colors);
     newFile = new JMenuItem("New");
     openFile =new JMenuItem("Open");
     saveFile =new JMenuItem("Save");
     newFile.addActionListener(this);
     openFile.addActionListener(this);
     saveFile.addActionListener(this);
     file.add(newFile);
     file.add(openFile);
     file.add(saveFile);
     cut=new JMenuItem("Cut");
     copy=new JMenuItem("Copy");
     paste=new JMenuItem("Paste");
     selectAll =new JMenuItem("SelectAll");
     close =new JMenuItem("Close");
     undo=new JMenuItem("Undo");
     redo=new JMenuItem("redo");
     cut.addActionListener(this);
     copy.addActionListener(this);
     paste.addActionListener(this);
     selectAll.addActionListener(this);
     close.addActionListener(this);
     undo.addActionListener(this);
     redo.addActionListener(this);
     edit.add(cut);
     edit.add(copy);
     edit.add(paste);
     edit.add(selectAll);
     edit.add(close);
     edit.add(undo);
     edit.add(redo);
     wordWrap=new JMenuItem("Word Wrap");
     wordWrap.addActionListener(this);
     format.add(wordWrap);
     fontArial=new JMenuItem("Arial");
     fontCSMS=new JMenuItem("Comic Sans MS");
     fontTNR=new JMenuItem("Times new Roman");
     font.add(fontArial);
     font.add(fontCSMS);
     font.add(fontTNR);
     fontArial.addActionListener(this);
     fontCSMS.addActionListener(this);
     fontTNR.addActionListener(this);
     fs8=new JMenuItem("8");
     fs12=new JMenuItem("12");
     fs16=new JMenuItem("16");
     fs20=new JMenuItem("20");
     fs24=new JMenuItem("24");
     fs28=new JMenuItem("28");
     fontSize.add(fs8);
     fontSize.add(fs12);
     fontSize.add(fs16);
     fontSize.add(fs20);
     fontSize.add(fs24);
     fontSize.add(fs28);
     fs8.addActionListener(this);
     fs12.addActionListener(this);
     fs16.addActionListener(this);
     fs20.addActionListener(this);
     fs24.addActionListener(this);
     fs28.addActionListener(this);
     white=new JMenuItem("White");
     black=new JMenuItem("Black");
     blue=new JMenuItem("Blue");
     Colors.add(white);
     Colors.add(black);
     Colors.add(blue);
     white.addActionListener(this);
     black.addActionListener(this);
     blue.addActionListener(this);
     textArea=new JTextArea();
        arial=new Font("Arial", Font.PLAIN,12);
        textArea.setFont(arial);
     textArea.getDocument().addUndoableEditListener(
         new UndoableEditListener() {
             @Override
             public void undoableEditHappened(UndoableEditEvent e) {
                 um.addEdit(e.getEdit());
             }

    });
     frame.setJMenuBar(menuBar);
     JPanel jPanel =new JPanel();
     jPanel.setBorder(new EmptyBorder(5,5,5,5));
     jPanel.setLayout(new BorderLayout(0,0));
     jPanel.add(textArea,BorderLayout.CENTER);
     JScrollPane jScrollPane =new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
     jPanel.add(jScrollPane);
     frame.add(jPanel);
     frame.setBounds(0,0,400,400);
     frame.setVisible(true);
     frame.setLayout(null);
    }
    @Override
    public void actionPerformed(ActionEvent e){
        if(e.getSource()==cut){
            textArea.cut();
        }
        if(e.getSource()==copy){
            textArea.copy();
        }
        if(e.getSource()==paste){
            textArea.paste();
        }
        if(e.getSource()==selectAll){
            textArea.selectAll();
        }
        if(e.getSource()==close){
            System.exit(0);
        }
        if(e.getSource()==openFile){
            JFileChooser fileChooser =new JFileChooser("C:");
            int chooseOption =fileChooser.showOpenDialog(null);
           // System.out.println(chooseOption);
            if(chooseOption == JFileChooser.APPROVE_OPTION){
                File file=fileChooser.getSelectedFile();
                String filePath=file.getPath();
                try{
                    FileReader fileReader =new FileReader(filePath);
                    BufferedReader bufferedReader =new BufferedReader(fileReader);
                    String intermediate = "", output = "";
                    while((intermediate=bufferedReader.readLine())!=null){
                        output+=intermediate+"\n";
                    }
                     textArea.setText(output);
                }
                catch(IOException fileNotFoundException){
                    fileNotFoundException.printStackTrace();
                }
            }
        }
        if(e.getSource()==saveFile){
            JFileChooser fileChooser=new JFileChooser("C:");
            int chooseOption = fileChooser.showSaveDialog(null);

            if(chooseOption==JFileChooser.APPROVE_OPTION){
                File file=new File(fileChooser.getSelectedFile().getAbsolutePath()+".txt");
                try {
                    FileWriter fileWriter = new FileWriter(file);
                    BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                    textArea.write(bufferedWriter);
                    bufferedWriter.close();
                }
                catch(IOException ioException){
                    ioException.printStackTrace();
                }
            }

        }

        if(e.getSource()==newFile){
            TextEditor newtextEditor =new TextEditor();
        }
        if(e.getSource()==undo){
            um.undo();
        }
        if(e.getSource()==redo){
           um.redo();
        }
        if(e.getSource()==wordWrap){
              if(wordWrapOn==false){
                  wordWrapOn=true;
                  textArea.setLineWrap(true);
                  textArea.setWrapStyleWord(true);
                  wordWrap.setText("Word Wrap:ON");
              }
              else if(wordWrapOn==true){
                  wordWrapOn=false;
                  textArea.setLineWrap(false);
                  textArea.setWrapStyleWord(false);
                  wordWrap.setText("Word Wrap:OFF");
              }
        }
        if(e.getSource()==fontArial){
            textArea.setFont(arial);
        }
        if(e.getSource()==fontCSMS){
            textArea.setFont(cns);
        }
        if(e.getSource()==fontTNR){
           textArea.setFont(tnr);
        }
        if(e.getSource()==fs8){
                 arial=new Font("Arial", Font.PLAIN,8);
                 cns=new Font("Comic Sans MS", Font.PLAIN,8);
                 tnr=new Font("Times New Roman", Font.PLAIN,8);
        }
        if(e.getSource()==fs12){
            arial=new Font("Arial", Font.PLAIN,12);
            cns=new Font("Comic Sans MS", Font.PLAIN,12);
            tnr=new Font("Times New Roman", Font.PLAIN,12);
        }
        if(e.getSource()==fs16){
            arial=new Font("Arial", Font.PLAIN,16);
            cns=new Font("Comic Sans MS", Font.PLAIN,16);
            tnr=new Font("Times New Roman", Font.PLAIN,16);
        }
        if(e.getSource()==fs20){
            arial=new Font("Arial", Font.PLAIN,20);
            cns=new Font("Comic Sans MS", Font.PLAIN,20);
            tnr=new Font("Times New Roman", Font.PLAIN,20);
        }
        if(e.getSource()==fs24){
            arial=new Font("Arial", Font.PLAIN,24);
            cns=new Font("Comic Sans MS", Font.PLAIN,24);
            tnr=new Font("Times New Roman", Font.PLAIN,24);
        }
        if(e.getSource()==fs28){
            arial=new Font("Arial", Font.PLAIN,28);
            cns=new Font("Comic Sans MS", Font.PLAIN,28);
            tnr=new Font("Times New Roman", Font.PLAIN,28);
        }
        if(e.getSource()==white){
            frame.setBackground(Color.WHITE);
            textArea.setBackground(Color.WHITE);
            textArea.setForeground(Color.BLACK);

        }
        if(e.getSource()==black){
            frame.setBackground(Color.BLACK);
            textArea.setBackground(Color.BLACK);
            textArea.setForeground(Color.WHITE);

        }
        if(e.getSource()==blue){
            frame.setBackground(Color.BLUE);
            textArea.setBackground(Color.BLUE);
            textArea.setForeground(Color.WHITE);

        }
    }
    public static void main(String[] args) {
        TextEditor texteditor =new TextEditor();
    }
}