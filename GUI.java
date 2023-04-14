import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GUI {
    private JPanel mainPane;
    private JTabbedPane tp;
    private JPanel tp1;
    private JList tp1JList;
    private JScrollPane tp1ScrollPanel;
    private JPanel tp1JPanel2;
    private JTextField tp1TFParkingPlace;
    private JTextField tp1TFNumberParkingPlace;
    private JLabel tp1JLParkingPlace;
    private JLabel tp1JLNumberParkingPlace;
    private JPanel tp2;
    private JButton tp1JButtonAddParkingPlace;
    private JComboBox tp2ComboBox;
    private JButton tp2JButtonBooking;
    private JList tp2JList;
    private JScrollPane tp2ScrollPanel;
    private JButton tp2ButtonSave;
    private JButton tp1JButtonLoadData;
    private DefaultListModel listModel;
    List<ParkingPlace> parkingPlaces;
    List<String> bookings;

    /**
     * Konstruktor bezparametrowy klasy GUI
     */
    public GUI() {

        listModel = new DefaultListModel<>();
        tp1JList.setModel(listModel);
        parkingPlaces = new ArrayList<>();
        bookings = new ArrayList<String>();

        parkingPlaces.add(new ParkingPlace("podziemne", 1));
        parkingPlaces.add(new ParkingPlace("podziemne", 2));
        parkingPlaces.add(new ParkingPlace("podziemne", 3));
        parkingPlaces.add(new ParkingPlace("naziemne", 1));
        parkingPlaces.add(new ParkingPlace("naziemne", 2));
        parkingPlaces.add(new ParkingPlace("naziemne", 3));
        parkingPlaces.add(new ParkingPlace("specjalne", 1));
        parkingPlaces.add(new ParkingPlace("specjalne", 2));
        parkingPlaces.add(new ParkingPlace("specjalne", 3));
        renderParkingPlaceList();

        tp1JButtonAddParkingPlace.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int m = Integer.parseInt(tp1TFNumberParkingPlace.getText());
                } catch (NumberFormatException a) {
                    JOptionPane.showMessageDialog(null, "Bład! Podaj numer miejsca postojowego!");
                }
                if (tp1TFParkingPlace.getText().equals(" ") || tp1TFParkingPlace.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Nie podano typu miejsca postojowego!");
                } else {
                    parkingPlaces.add(new ParkingPlace(tp1TFParkingPlace.getText(), Integer.parseInt(tp1TFNumberParkingPlace.getText())));
                    renderParkingPlaceList();
                    tp1TFParkingPlace.setText("");
                    tp1TFNumberParkingPlace.setText("");
                }
            }
        });

        /**
         * Obsługa ChangeLisenera
        */

        tp.addChangeListener(new ChangeListener() {
            /**
             * Invoked when the target of the listener has changed its state.
             *
             * @param e a ChangeEvent object
             */
            @Override
            public void stateChanged(ChangeEvent e) {
                if (tp.getSelectedIndex() == 0) {
                    renderParkingPlaceList();
                } else {
                    renderParkingPlaceListComboBox();
                }
            }
        });

        /**
         * Obsługa JButtonBooking
         */

        tp2JButtonBooking.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String booking = tp2ComboBox.getSelectedItem().toString();
                bookings.add(booking);
                renderBookingList();
            }
        });
        tp2ButtonSave.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    PrintWriter out = new PrintWriter("Rezerwacje.txt");
                    out.println("Dokonano rezerwacji następujących miejsc parkingowych: ");
                    out.println("");

                    for (String p : bookings) {
                        out.println(p);
                    }
                    out.close();
                    JOptionPane.showMessageDialog(null, "Rezerwacja zapisana w pliku Rezerwacje.txt");
                } catch (IOException a) {
                    JOptionPane.showMessageDialog(null, "Błąd zapisu do pliku");
                }
            }
        });

        /**
         * Obsługa JButtonLoadData
         */

        tp1JButtonLoadData.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                String path = "";
                JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
                j.setDialogTitle("Wybierz plik . txt");
                j.setAcceptAllFileFilterUsed(false);
                FileNameExtensionFilter r = new FileNameExtensionFilter("Tylko pliki .txt", "txt");
                j.addChoosableFileFilter(r);

                int o = j.showOpenDialog(null);
                if (o == JFileChooser.APPROVE_OPTION) {
                    path = j.getSelectedFile().getAbsolutePath();
                }
                System.out.println(path);
                LoadDataFromFile(path);
            }
        });
    }

    /**
     * Metoda renderParkingPlaceList
     */

    public void renderParkingPlaceList () {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (ParkingPlace p : parkingPlaces) {
            listModel.addElement(p.toString());
        }
        tp1JList.setModel(listModel);
    }

    /**
     * Metoda render
     */

    public void renderParkingPlaceListComboBox () {
        tp2ComboBox.removeAllItems();

        for (ParkingPlace p : parkingPlaces) {
            tp2ComboBox.addItem(p);
        }
    }

    /**
     * Metoda renderBookingList
     */

    public void renderBookingList () {
        DefaultListModel<String> listModel = new DefaultListModel<>();
        for (String p : bookings) {
            listModel.addElement(p.toString());
        }
        tp2JList.setModel(listModel);
    }
    /**
     * Metoda LoadDataFromFile
     * @param s
     */
    public void LoadDataFromFile(String s) {
        try {
            File f = new File(s);
            BufferedReader br = new BufferedReader(new FileReader(f));

            String st;
            while ((st = br.readLine()) != null) {
                String[] mp = st.split(" ");
                System.out.println(mp[0] + " " + mp[1] + " " + mp[2] + " " + mp[3] + " " + mp[4]);
                parkingPlaces.add(new ParkingPlace(mp[2], Integer.parseInt(mp[4])));
                System.out.println("");
            }
            renderParkingPlaceList();
        } catch (IOException e) {
        }
    }
    
    /**
     * Funkcja główna klasy GUI
     */

    public static void main(String[] args) {
        JFrame frame = new JFrame("GUI");
        frame.setContentPane(new GUI().mainPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.setSize(500,500);
    }
}
