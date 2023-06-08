package com.jdbcTest2.main.frame;

import com.jdbcTest2.main.Controller;
import com.jdbcTest2.main.Patient;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.*;
import java.awt.*;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Main Frame class of application that defines every panel content of application.
 */
public class AppFrame extends JFrame {

    /**
     * Global field of JPanel object that wraps every component. Used mostly to easily remove/add components.
     */
    private static JPanel wrapper;
    /**
     * Global field of menu application
     */
    private static JMenu menu;

    /**
     * Constructor sets frame settings and sets starting panel
     */
    public AppFrame(){
        setTitle("Blood Donation");
        int width = (int) (Toolkit.getDefaultToolkit().getScreenSize().getWidth() / 2);
        int height = (int) (Toolkit.getDefaultToolkit().getScreenSize().getHeight() / 2);
        setSize(new Dimension(width,height));
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JMenuBar menuBar = new JMenuBar();
        menu = new JMenu("MENU");

        JMenuItem menuItem = new JMenuItem("ADD DONATOR");
        JMenuItem menuItem2 = new JMenuItem("UPDATE DATA");
        JMenuItem menuItem3 = new JMenuItem("REPORTS");
        menu.add(menuItem);
        menu.add(menuItem2);
        menu.add(menuItem3);
        menu.setEnabled(false);
        menuBar.add(menu);

        menuItem.addActionListener((e) -> Controller.switchPanels(getBdonationPanel("submit")));

        menuItem2.addActionListener((e) -> Controller.switchPanels(getUpdatePanel()));

        menuItem3.addActionListener((e) -> Controller.switchPanels(getReportPanel()));

        setJMenuBar(menuBar);
        wrapper = new JPanel();
        wrapper.setLayout(new BorderLayout());
        wrapper.add(getStartPanel(), BorderLayout.CENTER);
        add(wrapper,BorderLayout.CENTER);
        setVisible(true);
    }

    // getters
    public static JMenu getMenu(){return menu;}
    public static JPanel getStartPanel(){ return new StartPanel();}
    public static JPanel getMenuPanel() {return new MenuPanel();}
    public static JPanel getLoadingScreen(){ return new LoadingScreen();}
    public static JScrollPane getBdonationPanel(String command){return new BDonationPanel(command);}
    public static JPanel getUpdatePanel(){return new UpdatePanel();}
    public static JPanel getReportPanel(){ return new ReportPanel();}
    public static JPanel getWrapper(){ return wrapper;}

    /**
     * Creates starting panel used every time upon launching application, holds only ony active component (button) which clicked on establishes connection to database.
     */
    private static class StartPanel extends JPanel{

        StartPanel(){

            var label = new JLabel("Blood Donation Center");
            var userLabel = new JLabel("User : ");
            var pswdLabel = new JLabel("Password : ");
            setLabelSettings(label,40f);
            setLabelSettings(userLabel,30f);
            setLabelSettings(pswdLabel,30f);

            var btn = new JButton("LOGIN");
            btn.setFont(btn.getFont().deriveFont(30f));
            btn.setFocusable(false);
            btn.setActionCommand("login");
            btn.addActionListener(Controller.action);

            var userName = new JTextField(5);
            setTxtFieldSettings(userName,"Rocky");

            var psswd = new JPasswordField(6);
            setTxtFieldSettings(psswd,"12345");
            psswd.setEchoChar('*');

            var midPanel = new JPanel();
            midPanel.setLayout(new GridLayout(0,1));

            var midTop = new JPanel();
            midTop.setLayout(new FlowLayout());
            midTop.add(userLabel);
            midTop.add(userName);

            var midBtm = new JPanel();
            midBtm.setLayout(new FlowLayout());
            midBtm.add(pswdLabel);
            midBtm.add(psswd);

            midPanel.add(midTop);
            midPanel.add(midBtm);

            var topPanel = new JPanel();
            topPanel.setLayout(new BorderLayout());
            topPanel.add(label,BorderLayout.CENTER);

            var bottomPanel = new JPanel();
            bottomPanel.setLayout(new FlowLayout());
            bottomPanel.add(btn);

            setLayout(new GridLayout(0,1));
            add(topPanel);
            add(midPanel);
            add(bottomPanel);

        }

        /**
         * StartPanel contains several JLabel objects that have similar setting. This method sets those settings with provided JLabel as parameter
         * which helps reduce code.
         * @param label JLabel object to set font and alignment
         * @param txtSize Text size (float number)
         */
        private static void setLabelSettings(JLabel label, float txtSize){
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(label.getFont().deriveFont(txtSize));
        }

        /**
         * StartPanel contains several JTextField objects that have similar setting. This method sets those settings with provided JTextField as parameter
         * which helps reduce code.
         * @param textField JTextField object to set few settings
         * @param txt Text displayed inside text field.
         */
        private static void setTxtFieldSettings(JTextField textField, String txt){
            textField.setHorizontalAlignment(SwingConstants.CENTER);
            textField.setFont(textField.getFont().deriveFont(30f));
            textField.setText(txt);
            textField.setEditable(false);
            textField.setEnabled(false);
        }
    }

    /**
     * Panel that act's placeholder for "loading screen", it just contains label with {@link JProgressBar} object with intermediate (true) setting.
     */
    private static class LoadingScreen extends JPanel{

        LoadingScreen(){
            JLabel label = new JLabel("Please wait");
            label.setHorizontalAlignment(SwingConstants.CENTER);
            label.setFont(label.getFont().deriveFont(40f));

            JPanel topPanel = new JPanel();
            topPanel.setLayout(new BorderLayout());
            topPanel.add(label,BorderLayout.CENTER);

            JLabel label2 = new JLabel("processing");
            label2.setHorizontalAlignment(SwingConstants.CENTER);
            label2.setFont(label.getFont().deriveFont(40f));

            JPanel middlePanel = new JPanel();
            middlePanel.setLayout(new BorderLayout());
            middlePanel.add(label2,BorderLayout.CENTER);

            JPanel bottomPanel = new JPanel();
            bottomPanel.setLayout(new FlowLayout());
            JProgressBar jProgressBar = new JProgressBar();
            jProgressBar.setString("Pending...");
            jProgressBar.setStringPainted(true);
            jProgressBar.setPreferredSize(new Dimension(200,30));
            jProgressBar.setIndeterminate(true);

            bottomPanel.add(jProgressBar);

            setLayout(new GridLayout(0,1));
            add(topPanel);
            add(middlePanel);
            add(bottomPanel);
        }
    }

    /**
     * Panel that act's as menu of application.
     */
    private static class MenuPanel extends JPanel{

        MenuPanel(){
            JButton newDonorBtn = new JButton("ADD DONATOR");
            setBtnSettings(newDonorBtn, "newDonor");

            JButton updateDataBtn = new JButton("UPDATE DATA");
            setBtnSettings(updateDataBtn,"updateData");

            JButton showStatsBtn = new JButton("SHOW STATISTICS");
            setBtnSettings(showStatsBtn, "showStats");

            var wrapper = new JPanel();
            wrapper.setLayout(new GridLayout(0,1));
            wrapper.add(newDonorBtn);
            wrapper.add(updateDataBtn);
            wrapper.add(showStatsBtn);

            setLayout(new BorderLayout());
            add(wrapper,BorderLayout.CENTER);
        }

        private static void setBtnSettings(JButton btn, String actionCommand){
            btn.setFont(btn.getFont().deriveFont(30f));
            btn.setFocusable(false);
            btn.setActionCommand(actionCommand);
            btn.addActionListener(Controller.action);
        }
    }

    /**
     * Panel with every input needed for adding new blood donation to database, ie. patient data, address and blood donation itself.
     */
    private static class BDonationPanel extends JScrollPane{

        // every static field is declared here globally to easly access its contents (like Strings) and put insiede patiendData map
        private static final Map<String,String> patientData = new HashMap<>();
        private static JTextField name;
        private static JTextField lastName;
        private static JTextField socialNumber;
        private static JTextField telNumber;
        private static JTextField email;
        private static JTextField street;
        private static JTextField city;
        private static JTextField cityCode;
        private static JTextField country;
        private static JSpinner age;
        private static JSpinner weight;
        private static JSpinner amountOfBlood;
        private static JComboBox<String> sex;
        private static JComboBox<String> bloodType;
        private static JComboBox<String> isRegistered;

        private static JPanel namePanel;
        private static JPanel lastNamePanel;
        private static JPanel socialNumberPanel;
        private static JPanel telNumberPanel;
        private static JPanel emailPanel;
        private static JPanel streetPanel;
        private static JPanel cityPanel;
        private static JPanel cityCodePanel;
        private static JPanel countryPanel;

        private static JButton btn;

        BDonationPanel(String command){

            name = new JTextField(10);
            setFontSize(name,20f);
            name.setBorder(BorderFactory.createTitledBorder("First Name"));
            namePanel = new JPanel();
            namePanel.setLayout(new BorderLayout());
            namePanel.add(name,BorderLayout.CENTER);
            namePanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            PlainDocument documentName = (PlainDocument) name.getDocument();
            documentName.setDocumentFilter(new MyTxtFilter(namePanel,35));

            lastName = new JTextField(10);
            setFontSize(lastName,20f);
            lastName.setBorder(BorderFactory.createTitledBorder("Last Name"));
            lastNamePanel = new JPanel();
            lastNamePanel.setLayout(new BorderLayout());
            lastNamePanel.add(lastName,BorderLayout.CENTER);
            lastNamePanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));;
            PlainDocument documentLastName = (PlainDocument) lastName.getDocument();
            documentLastName.setDocumentFilter(new MyTxtFilter(lastNamePanel,35));

            socialNumber = new JTextField(12);
            setFontSize(socialNumber,20f);
            socialNumber.setBorder(BorderFactory.createTitledBorder("Social Number"));
            socialNumberPanel = new JPanel();
            socialNumberPanel.setLayout(new BorderLayout());
            socialNumberPanel.add(socialNumber,BorderLayout.CENTER);
            socialNumberPanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            PlainDocument documentSocialNumber = (PlainDocument) socialNumber.getDocument();
            documentSocialNumber.setDocumentFilter(new MyIntFilter(socialNumberPanel,12,1));

            age = new JSpinner(new SpinnerNumberModel(30,18,65,1));
            setFontSize(age,20f);
            age.setBorder(BorderFactory.createTitledBorder("Age"));

            weight = new JSpinner(new SpinnerNumberModel(80,50,200,1));
            setFontSize(weight,20f);
            weight.setBorder(BorderFactory.createTitledBorder("Weight"));

            sex = new JComboBox<>();
            setFontSize(sex,20f);
            sex.addItem("M");
            sex.addItem("F");
            sex.setBorder(BorderFactory.createTitledBorder("Sex"));

            telNumber = new JTextField(9);
            setFontSize(telNumber,20f);
            telNumber.setBorder(BorderFactory.createTitledBorder("Phone Number"));
            telNumberPanel = new JPanel();
            telNumberPanel.setLayout(new BorderLayout());
            telNumberPanel.add(telNumber,BorderLayout.CENTER);
            telNumberPanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            PlainDocument documentTelNumber = (PlainDocument) telNumber.getDocument();
            documentTelNumber.setDocumentFilter(new MyIntFilter(telNumberPanel,10,0));

            email = new JTextField(10);
            setFontSize(email,20f);
            email.setBorder(BorderFactory.createTitledBorder("Email"));
            emailPanel = new JPanel();
            emailPanel.setLayout(new BorderLayout());
            emailPanel.add(email,BorderLayout.CENTER);
            emailPanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            setDocumentFilter(email,emailPanel,0);

            street = new JTextField(10);
            setFontSize(street,20f);
            street.setBorder(BorderFactory.createTitledBorder("Street"));
            streetPanel = new JPanel();
            streetPanel.setLayout(new BorderLayout());
            streetPanel.add(street,BorderLayout.CENTER);
            streetPanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            setDocumentFilter(street,streetPanel,1);

            city = new JTextField(10);
            setFontSize(city,20f);
            city.setBorder(BorderFactory.createTitledBorder("City"));
            cityPanel = new JPanel();
            cityPanel.setLayout(new BorderLayout());
            cityPanel.add(city,BorderLayout.CENTER);
            cityPanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            PlainDocument documentCity = (PlainDocument) city.getDocument();
            documentCity.setDocumentFilter(new MyTxtFilter(cityPanel,35));

            cityCode = new JTextField(10);
            setFontSize(cityCode,20f);
            cityCode.setBorder(BorderFactory.createTitledBorder("City Code"));
            cityCodePanel = new JPanel();
            cityCodePanel.setLayout(new BorderLayout());
            cityCodePanel.add(cityCode,BorderLayout.CENTER);
            cityCodePanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            PlainDocument documentCityCode = (PlainDocument) cityCode.getDocument();
            documentCityCode.setDocumentFilter(new MyZipCodeFilter(cityCodePanel));

            country = new JTextField(10);
            setFontSize(country,20f);
            country.setBorder(BorderFactory.createTitledBorder("Country"));
            countryPanel = new JPanel();
            countryPanel.setLayout(new BorderLayout());
            countryPanel.add(country,BorderLayout.CENTER);
            countryPanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            PlainDocument documentCountry = (PlainDocument) country.getDocument();
            documentCountry.setDocumentFilter(new MyTxtFilter(countryPanel,35));

            amountOfBlood = new JSpinner(new SpinnerNumberModel(450,300,600,10));
            setFontSize(amountOfBlood,20f);
            amountOfBlood.setBorder(BorderFactory.createTitledBorder("Blood Amount"));

            bloodType = new JComboBox<>();
            setFontSize(bloodType,20f);
            bloodType.setBorder(BorderFactory.createTitledBorder("Blood Type"));
            bloodType.addItem("A-");
            bloodType.addItem("A+");
            bloodType.addItem("B-");
            bloodType.addItem("B+");
            bloodType.addItem("AB+");
            bloodType.addItem("AB-");
            bloodType.addItem("0-");
            bloodType.addItem("0+");

            isRegistered = new JComboBox<>();
            setFontSize(isRegistered,20f);
            isRegistered.setBorder(BorderFactory.createTitledBorder("Registered?"));
            isRegistered.addItem("YES");
            isRegistered.addItem("NO");

            var submit = new JButton("SUBMIT");
            setFontSize(submit,25f);
            submit.setFocusable(false);
            submit.addActionListener((e) ->{
                // first check textfields
                // if everything is ok patientData map is filled with values from every input
                boolean status = true;
                if (name.getText().equals("") || name.getText().length() < 3){
                    status = false;
                    namePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.RED));
                }
                if (lastName.getText().equals("") || lastName.getText().length() < 3){
                    status = false;
                    lastNamePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.RED));
                }
                if (telNumber.getText().equals("") || telNumber.getText().length() < 9){
                    status = false;
                    telNumberPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.RED));
                }
                if (socialNumber.getText().equals("") || socialNumber.getText().length() < 11){
                    status = false;
                    socialNumberPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.RED));
                }
                // check if before @ are some letter, once @, after some letter, dot once, and some letter
                // very bad regex, but for now will do (tokens like this will pass       a@b.c (a.b@c.d)       but assumption is user will provide somewhat proper email)
                if (!(email.getText().matches("^[\\w]+[.]?+[\\w]+[@][\\w]+[.][\\w]+$"))){
                    status = false;
                    emailPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.RED));
                }
                if (street.getText().equals("") || street.getText().length() < 3){
                    status = false;
                    streetPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.RED));
                }
                if (city.getText().equals("") || city.getText().length() < 3){
                    status = false;
                    cityPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.RED));
                }
                if (country.getText().equals("") || country.getText().length() < 3){
                    status = false;
                    countryPanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.RED));
                }
                // format xx-xxx
                if (!(cityCode.getText().matches("[\\d]{1,2}[-][\\d]{3}$"))){
                    status = false;
                    cityCodePanel.setBorder(BorderFactory.createMatteBorder(1,1,1,1,Color.RED));
                }

                if(status){
                    patientData.put("name",name.getText());
                    patientData.put("lastName", lastName.getText());
                    patientData.put("telNumber", telNumber.getText());
                    patientData.put("socialNumber", socialNumber.getText());
                    patientData.put("email",email.getText());
                    patientData.put("age", String.valueOf(age.getValue()));
                    patientData.put("weight", String.valueOf(weight.getValue()));
                    patientData.put("sex", String.valueOf(sex.getSelectedItem()));
                    patientData.put("street", street.getText());
                    patientData.put("city", city.getText());
                    patientData.put("country", country.getText());
                    patientData.put("cityCode", cityCode.getText());
                    patientData.put("amountOfBlood", String.valueOf(amountOfBlood.getValue()));
                    patientData.put("bloodType", String.valueOf(bloodType.getSelectedItem()));
                    patientData.put("isRegistered", String.valueOf(isRegistered.getSelectedIndex()));
                    patientData.put("date", LocalDate.now().toString());

                    LocalTime time = LocalTime.now();
                    patientData.put("time", time.getHour() +":"+ time.getMinute()+":"+time.getSecond());
                    Controller.setPatientDAOObject(new Patient(patientData));

                    btn = new JButton();
                    btn.setAction(Controller.action);
                    btn.setActionCommand(command);
                    btn.doClick();
                }
            });

            var wrapper = new JPanel();
            wrapper.setLayout(new GridBagLayout());
            var g = new GridBagConstraints();
            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 1;
            g.weightx = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
            wrapper.add(namePanel,g);
            g.gridx = 1;
            wrapper.add(lastNamePanel,g);
            g.gridx = 0;
            g.gridy = 1;
            g.gridwidth = 1;
            wrapper.add(telNumberPanel,g);
            g.gridx = 1;
            wrapper.add(emailPanel,g);
            g.gridx = 0;
            g.gridy = 2;
            g.gridwidth = 2;
            wrapper.add(socialNumberPanel,g);

            var panel = new JPanel();
            panel.setLayout(new FlowLayout());
            panel.add(age);
            panel.add(weight);
            panel.add(sex);
            g.gridx = 0;
            g.gridy = 3;
            g.gridwidth = 2;
            wrapper.add(panel,g);

            g.gridx = 0;
            g.gridy = 4;
            g.gridwidth = 1;
            g.weightx = 1;
            g.fill = GridBagConstraints.HORIZONTAL;
            wrapper.add(streetPanel,g);
            g.gridx = 1;
            wrapper.add(cityPanel,g);
            g.gridx = 0;
            g.gridy = 5;
            wrapper.add(cityCodePanel,g);
            g.gridx = 1;
            wrapper.add(countryPanel,g);

            var panel2 = new JPanel();
            panel2.setLayout(new FlowLayout());
            panel2.add(amountOfBlood);
            panel2.add(bloodType);
            panel2.add(isRegistered);
            g.gridx = 0;
            g.gridy = 6;
            g.gridwidth = 2;
            wrapper.add(panel2,g);

            g.gridx = 0;
            g.gridy = 7;
            g.weightx = 0;
            g.weighty = 0.1;
            g.gridwidth = 2;
            g.fill = GridBagConstraints.CENTER;
            wrapper.add(submit,g);
            setViewportView(wrapper);
        }

        /**
         * Delegator to set similar setting for every component declared in class.
         * @param component JComponent object
         * @param size Text size
         */
        private static void setFontSize(JComponent component, float size){
            component.setFont(component.getFont().deriveFont(size));
        }

        // setters for every input
        public static void setNameField(String s){ name.setText(s); }
        public static void setLastName(String s){ lastName.setText(s); }
        public static void setAge(String s){ age.setValue(Integer.parseInt(s)); }
        public static void setWeight(String s){ weight.setValue(Integer.parseInt(s)); }
        public static void setSex(String s){ sex.setSelectedIndex(s.equals("M")?0:1); }
        public static void setTelNumber(String s){ telNumber.setText(s); }
        public static void setEmail(String s){ email.setText(s); }
        public static void setSocialNumber(String s){ socialNumber.setText(s); }
        public static void setStreet(String s){ street.setText(s); }
        public static void setCity(String s){ city.setText(s); }
        public static void setCityCode(String s){ cityCode.setText(s); }
        public static void setCountry(String s){ country.setText(s); }
        public static void setAmountOfBlood(String s){ amountOfBlood.setValue(Integer.parseInt(s)); }
        public static void setBloodType(String s){
            switch (s){
                case "A-" -> bloodType.setSelectedIndex(0);
                case "A+" -> bloodType.setSelectedIndex(1);
                case "B-" -> bloodType.setSelectedIndex(2);
                case "B+" -> bloodType.setSelectedIndex(3);
                case "AB-" -> bloodType.setSelectedIndex(4);
                case "AB+" -> bloodType.setSelectedIndex(5);
                case "0-" -> bloodType.setSelectedIndex(6);
                case "0+" -> bloodType.setSelectedIndex(7);
            }
        }
        public static void setIsRegistered(String s){ isRegistered.setSelectedIndex(s.equals("YES")?0:1); }

        /**
         * Method that sets new document filter for provided JTextField object.
         * Filter limits entered characters and clears border of panel that wraps this text field.
         * Panel will have different border color if it is empty but whenever user starts typing, filter will clear it.
         * @param textField JTextField object
         * @param panel JPanel object that wraps provided JTextField
         * @param attr Mode for displaying text (0 - no change, 1 - to uppercase, 2 - to lowercase)
         */
        private static void setDocumentFilter(JTextField textField, JPanel panel, int attr){
            PlainDocument documentName = (PlainDocument) textField.getDocument();
            documentName.setDocumentFilter(new DocumentFilter(){
                @Override
                public void replace(DocumentFilter.FilterBypass fb, int offset, int length, String text, AttributeSet attrs){
                    try {
                        String string = fb.getDocument().getText(0, fb.getDocument().getLength()) + text;
                        if (string.length() < 20) {
                            switch (attr){
                                case 0 -> super.replace(fb, offset, length, text, attrs);
                                case 1 -> super.replace(fb, offset, length, text.toUpperCase(), attrs);
                                case 2 -> super.replace(fb, offset, length, text.toLowerCase(), attrs);
                            }
                            panel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
                        }
                    }
                    catch (BadLocationException e){ e.printStackTrace(); }
                }
            });
        }

        /**
         * Class extending {@link DocumentFilter} class which overrides its methods to provide custom filtering of JTextFields.
         * Filter allows entering only numeric characters. Constructor takes 3 parameters, JPanel upon which border behavior is set (clearing after user types),
         * limit - which tells how many characters' user can enter and isLong parameter which used in internal test, since one of the text field contains values
         * exceeding Integer max value.
         */
        private static class MyIntFilter extends DocumentFilter {

            private final JPanel panel;
            private final int limit;
            private final int isLong;

            MyIntFilter(JPanel panel, int limit, int isLong){
                this.panel = panel;
                this.limit = limit;
                this.isLong = isLong;
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string,
                                     AttributeSet attr) throws BadLocationException {

                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.insert(offset, string);

                if (test(sb.toString())) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            private boolean test(String text) {
                if (text.isBlank()){ return true; }
                else{
                    if (isLong == 0){
                        try {
                            Integer.parseInt(text);
                            return true;
                        } catch (NumberFormatException e) { return false;}
                    } else {
                        try {
                            Long.parseLong(text);
                            return true;
                        } catch (NumberFormatException e) { return false;}
                    }
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text,
                                AttributeSet attrs) throws BadLocationException {

                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.replace(offset, offset + length, text);

                if (sb.toString().length() < limit){
                    if (test(sb.toString())) {
                        super.replace(fb, offset, length, text, attrs);
                        panel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
                    }
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length)
                    throws BadLocationException {
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.delete(offset, offset + length);

                if (test(sb.toString())) {
                    super.remove(fb, offset, length);
                }

            }
        }

        /**
         * Class extending {@link DocumentFilter} class which overrides its methods to provide custom filtering of JTextFields.
         * Filter allows entering only letter characters to avoid for example entering names with numbers. Constructor takes 2 parameters, JPanel upon which border behavior is set (clearing after user types),
         * limit - which tells how many characters' user can enter.
         */
        private static class MyTxtFilter extends DocumentFilter {

            private final JPanel panel;
            private final int limit;

            MyTxtFilter(JPanel panel, int limit){
                this.panel = panel;
                this.limit = limit;
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string,
                                     AttributeSet attr) throws BadLocationException {

                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.insert(offset, string);

                if (test(sb.toString())) {
                    super.insertString(fb, offset, string.toUpperCase(), attr);
                }
            }

            private boolean test(String text) {
                if (text.isBlank()){
                    return true;
                } else {
                    return text.matches("^[a-zA-Z]+$");
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text,
                                AttributeSet attrs) throws BadLocationException {

                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.replace(offset, offset + length, text);

                if (sb.toString().length() < limit){
                    if (test(sb.toString())) {
                        super.replace(fb, offset, length, text.toUpperCase(), attrs);
                        panel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
                    }
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length)
                    throws BadLocationException {
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.delete(offset, offset + length);

                if (test(sb.toString())) {
                    super.remove(fb, offset, length);
                }
            }
        }

        /**
         * Class extending {@link DocumentFilter} class which overrides its methods to provide custom filtering of JTextFields.
         * Filter allows entering only numeric characters. Constructor takes 1 parameter, JPanel upon which border behavior is set (clearing after user types),
         * internally limit of characters is set to 6 since format of zip code is xx-xxx;
         */
        private static class MyZipCodeFilter extends DocumentFilter {

            private final JPanel panel;

            MyZipCodeFilter(JPanel panel){
                this.panel = panel;
            }

            @Override
            public void insertString(FilterBypass fb, int offset, String string,
                                     AttributeSet attr) throws BadLocationException {

                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.insert(offset, string);

                if (test(sb.toString())) {
                    super.insertString(fb, offset, string, attr);
                }
            }

            private boolean test(String text) {
                if (text.isBlank()){
                    return true;
                } else {
                    if (text.length() < 3){
                        return text.matches("^[\\d]+$");
                    }
                    if (text.length() == 3){
                        return text.matches("^[\\d]{2}[-]{1}");
                    }
                    if (text.length() < 7){
                        return text.matches("^[\\d]{2}[-]{1}[\\d]+$");
                    } else {
                        return false;
                    }
                }
            }

            @Override
            public void replace(FilterBypass fb, int offset, int length, String text,
                                AttributeSet attrs) throws BadLocationException {

                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.replace(offset, offset + length, text);

                if (sb.toString().length() < 7){
                    if (test(sb.toString())) {
                        super.replace(fb, offset, length, text, attrs);
                        panel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
                    }
                }
            }

            @Override
            public void remove(FilterBypass fb, int offset, int length)
                    throws BadLocationException {
                Document doc = fb.getDocument();
                StringBuilder sb = new StringBuilder();
                sb.append(doc.getText(0, doc.getLength()));
                sb.delete(offset, offset + length);

                if (test(sb.toString())) {
                    super.remove(fb, offset, length);
                }
            }
        }
    }

    /**
     * Panel which holds 2 components, first with inputs used to retrieve data from database and second to display retrieved data.
     * Inputs used for updating are already set with retrieved data that user can change.
     */
    private static class UpdatePanel extends JPanel{

        private static JTextField name;
        private static JTextField lastName;
        private static JPanel namePanel;
        private static JPanel lastNamePanel;

        UpdatePanel() {

            name = new JTextField(10);
            setFontSize(name);
            name.setBorder(BorderFactory.createTitledBorder("First Name"));
            namePanel = new JPanel();
            namePanel.setLayout(new BorderLayout());
            namePanel.add(name, BorderLayout.CENTER);
            namePanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            PlainDocument documentName = (PlainDocument) name.getDocument();
            documentName.setDocumentFilter(new BDonationPanel.MyTxtFilter(namePanel, 35));

            lastName = new JTextField(10);
            setFontSize(lastName);
            lastName.setBorder(BorderFactory.createTitledBorder("Last Name"));
            lastNamePanel = new JPanel();
            lastNamePanel.setLayout(new BorderLayout());
            lastNamePanel.add(lastName, BorderLayout.CENTER);
            lastNamePanel.setBorder(BorderFactory.createEmptyBorder(1,1,1,1));
            PlainDocument documentLastName = (PlainDocument) lastName.getDocument();
            documentLastName.setDocumentFilter(new BDonationPanel.MyTxtFilter(lastNamePanel, 35));

            var retrieve = new JButton("RETRIEVE");
            setFontSize(retrieve);
            retrieve.setFocusable(false);

            setLayout(new GridBagLayout());
            var g = new GridBagConstraints();
            g.gridx = 0;
            g.gridy = 0;
            g.gridwidth = 1;
            g.weightx = 1;
            g.weighty = 0.1;
            g.fill = GridBagConstraints.HORIZONTAL;
            add(namePanel, g);
            g.gridx = 1;
            add(lastNamePanel, g);
            g.gridx = 0;
            g.gridy = 1;
            g.gridwidth = 2;
            g.weightx = 0;
            g.weighty = 0.1;
            g.fill = GridBagConstraints.CENTER;
            add(retrieve, g);
            var panel = new JPanel();
            g.gridx = 0;
            g.gridy = 2;
            g.gridwidth = 2;
            g.weightx = 1;
            g.weighty = 1;
            g.fill = GridBagConstraints.BOTH;
            add(panel,g);

            retrieve.addActionListener((e -> {
                //first checks if text fields arent empty or less than 3 characters
                boolean status = true;
                if (name.getText().equals("") || name.getText().length() < 3) {
                    status = false;
                    namePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED));
                }
                if (lastName.getText().equals("") || lastName.getText().length() < 3) {
                    status = false;
                    lastNamePanel.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.RED));
                }
                // if all is correct then get new object of donation panel (to reuse code) and set inputs values with data retrieved from database
                if (status){
                    try {
                        panel.removeAll();
                        panel.setLayout(new BorderLayout());
                        panel.add(getBdonationPanel("updateDataOfPatient"), BorderLayout.CENTER);

                        String query = "CALL pokaz_dane_pacjenta(?,?)";
                        PreparedStatement ps = Controller.conn.prepareStatement(query,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ps.setString(1,name.getText());
                        ps.setString(2,lastName.getText());

                        ResultSet rs = ps.executeQuery();
                        rs.next();
                        BDonationPanel.setSocialNumber(rs.getString(1));
                        BDonationPanel.setNameField(rs.getString(2));
                        BDonationPanel.setLastName(rs.getString(3));
                        BDonationPanel.setAge(rs.getString(4));
                        BDonationPanel.setSex(rs.getString(5));
                        BDonationPanel.setWeight(rs.getString(6));
                        BDonationPanel.setTelNumber(rs.getString(7));
                        BDonationPanel.setEmail(rs.getString(8));
                        BDonationPanel.setStreet(rs.getString(9));
                        BDonationPanel.setCity(rs.getString(10));
                        BDonationPanel.setCityCode(rs.getString(11));
                        BDonationPanel.setCountry(rs.getString(12));

                        String query2 = "CALL pokaz_dane_badania(?,?)";
                        PreparedStatement ps2 = Controller.conn.prepareStatement(query2,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ps2.setString(1,name.getText());
                        ps2.setString(2,lastName.getText());

                        ResultSet rs2 = ps2.executeQuery();
                        rs2.next();
                        BDonationPanel.setAmountOfBlood(rs2.getString(1));
                        BDonationPanel.setBloodType(rs2.getString(2));
                        BDonationPanel.setIsRegistered(rs2.getString(3));

                        String query3 = "SELECT idpacjent FROM pacjent WHERE imie_pacjenta = ? AND nazwisko_pacjenta = ?";
                        PreparedStatement ps3 = Controller.conn.prepareStatement(query3,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                        ps3.setString(1,name.getText());
                        ps3.setString(2,lastName.getText());
                        ResultSet rs3 = ps3.executeQuery();
                        rs3.next();
                        Controller.setPatientIdToUpdate(rs3.getInt(1));

                        panel.updateUI();

                    } catch (SQLException throwable) {
                        // bad handling of exception (for now inform user and "reset")
                        // note for future - error loggin feature
                        JOptionPane.showMessageDialog(null,"Couldn't retrieve information from database.");
                        Controller.switchPanels(getUpdatePanel());
                    }
                }
            }));
        }


        private static void setFontSize(JComponent component){
            component.setFont(component.getFont().deriveFont(20f));
        }
    }

    /**
     * Panel that holds {@link JTabbedPane} object, every tab is a JTable object that displays respond from specified SQL query.
     */
    private static class ReportPanel extends JPanel{

        private static JTabbedPane tabbedPane;

        ReportPanel(){
            tabbedPane = new JTabbedPane();
            tabbedPane.addTab("All patients",null,getPatients(),"Displays all patients in database");
            tabbedPane.addTab("Addresses of all Patients",null,getPatientsAddresses(),"Displays all patients addresses in database");
            tabbedPane.addTab("All donations",null,getAllBloodDonos(),"Displays all blood donations in database");

            setLayout(new BorderLayout());
            add(tabbedPane, BorderLayout.CENTER);
        }

        /**
         * Fetches basic data about all patient from database in form of table displaying name,last name, phone number and email.
         * @return JScrollPane object which wraps JTable object.
         */
        private static JScrollPane getPatients(){
            String[] columns = {"NAME", "LASTNAME", "PHONE NUMBER", "EMAIL"};
            String query = "SELECT imie_pacjenta, nazwisko_pacjenta, numer_tel, email FROM pacjent";
            List<ArrayList<String>> rows = new ArrayList<>();
            try{
                PreparedStatement ps = Controller.conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = ps.executeQuery();
                ResultSetMetaData rsmd = resultSet.getMetaData();

                int numOfColumns = rsmd.getColumnCount();
                resultSet.last();
                int numOfRows = resultSet.getRow();
                resultSet.beforeFirst();

                for (int i = 1; i <= numOfRows; i++) {
                    resultSet.next();
                    ArrayList<String> row = new ArrayList<>();
                    for (int j = 1; j <= numOfColumns; j++) {
                        row.add(resultSet.getString(j));
                    }
                    rows.add(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Object[][] data = new String[rows.size()][columns.length];

            for (int i = 0; i < rows.size(); i++){
                for (int j = 0; j < columns.length; j++){
                    data[i][j] = rows.get(i).get(j);
                }
            }
            TableModel model = new DefaultTableModel(data, columns){
                public boolean isCellEditable(int row, int column){return false;}
            };
            JTable jTable = new JTable(model);
            jTable.setRowSelectionAllowed(true);
            jTable.setColumnSelectionAllowed(true);
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < jTable.getColumnCount(); i++){
                jTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }
            jTable.setShowHorizontalLines(true);
            jTable.setFont(jTable.getFont().deriveFont(14f));
            jTable.setRowHeight(jTable.getRowHeight()+10);

            var panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(jTable.getTableHeader(),BorderLayout.NORTH);
            panel.add(jTable, BorderLayout.CENTER);

            return new JScrollPane(panel);
        }

        /**
         * Fetches data about patients addresses from database in form of table displaying street,city,city code and country.
         * @return JScrollPane object which wraps JTable object.
         */
        private static JScrollPane getPatientsAddresses(){
            String[] columns = {"STREET", "CITY", "CITY CODE", "COUNTRY"};
            String query = "SELECT ulica,miasto,kod_pocztowy,kraj FROM adres_pacjenta";

            List<ArrayList<String>> rows = new ArrayList<>();
            try{
                PreparedStatement ps = Controller.conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = ps.executeQuery();
                ResultSetMetaData rsmd = resultSet.getMetaData();

                int numOfColumns = rsmd.getColumnCount();
                resultSet.last();
                int numOfRows = resultSet.getRow();
                resultSet.beforeFirst();

                for (int i = 1; i <= numOfRows; i++) {
                    resultSet.next();
                    ArrayList<String> row = new ArrayList<>();
                    for (int j = 1; j <= numOfColumns; j++) {
                        row.add(resultSet.getString(j));
                    }
                    rows.add(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Object[][] data = new String[rows.size()][columns.length];

            for (int i = 0; i < rows.size(); i++){
                for (int j = 0; j < columns.length; j++){
                    data[i][j] = rows.get(i).get(j);
                }
            }
            TableModel model = new DefaultTableModel(data, columns){
                public boolean isCellEditable(int row, int column){return false;}
            };
            JTable jTable = new JTable(model);
            jTable.setRowSelectionAllowed(true);
            jTable.setColumnSelectionAllowed(true);
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < jTable.getColumnCount(); i++){
                jTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }
            jTable.setShowHorizontalLines(true);
            jTable.setFont(jTable.getFont().deriveFont(14f));
            jTable.setRowHeight(jTable.getRowHeight()+10);

            var panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(jTable.getTableHeader(),BorderLayout.NORTH);
            panel.add(jTable, BorderLayout.CENTER);

            return new JScrollPane(panel);
        }

        /**
         * Fetches data about all blood donations from database in form of table displaying name and last name of patient, type and amount of blood donated and date/time of donation.
         * @return JScrollPane object which wraps JTable object.
         */
        private static JScrollPane getAllBloodDonos(){
            String[] columns = {"NAME", "LAST NAME", "AMOUNT", "TYPE","DATE","TIME"};
            String query = "SELECT imie_pacjenta,nazwisko_pacjenta,ilosc_krwi,grupa_krwi,data_pobrania,czas_pobrania FROM pobranie_krwi LEFT JOIN pacjent ON pobranie_krwi.id_pacjenta = pacjent.idpacjent";

            List<ArrayList<String>> rows = new ArrayList<>();
            try{
                PreparedStatement ps = Controller.conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = ps.executeQuery();
                ResultSetMetaData rsmd = resultSet.getMetaData();

                int numOfColumns = rsmd.getColumnCount();
                resultSet.last();
                int numOfRows = resultSet.getRow();
                resultSet.beforeFirst();

                for (int i = 1; i <= numOfRows; i++) {
                    resultSet.next();
                    ArrayList<String> row = new ArrayList<>();
                    for (int j = 1; j <= numOfColumns; j++) {
                        row.add(resultSet.getString(j));
                    }
                    rows.add(row);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }

            Object[][] data = new String[rows.size()][columns.length];

            for (int i = 0; i < rows.size(); i++){
                for (int j = 0; j < columns.length; j++){
                    data[i][j] = rows.get(i).get(j);
                }
            }
            TableModel model = new DefaultTableModel(data, columns){
                public boolean isCellEditable(int row, int column){return false;}
            };
            JTable jTable = new JTable(model);
            jTable.setRowSelectionAllowed(true);
            jTable.setColumnSelectionAllowed(true);
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < jTable.getColumnCount(); i++){
                jTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }
            jTable.setShowHorizontalLines(true);
            jTable.setFont(jTable.getFont().deriveFont(14f));
            jTable.setRowHeight(jTable.getRowHeight()+10);

            var panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(jTable.getTableHeader(),BorderLayout.NORTH);
            panel.add(jTable, BorderLayout.CENTER);

            return new JScrollPane(panel);
        }

        /**
         * Method which will fetch data from database with provided SQL query as parameter and display records in form of JTable wrapper in JScrollPane component.
         * Cells in table are not editable.
         * @param query SQL query
         * @param columns String array of column names.
         * @return JScrollPane object which wraps JTable object.
         */
        private static JScrollPane getReport(String query, String[] columns){
            List<ArrayList<String>> rows = new ArrayList<>();
            try{
                PreparedStatement ps = Controller.conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
                ResultSet resultSet = ps.executeQuery();
                ResultSetMetaData rsmd = resultSet.getMetaData();

                int numOfColumns = rsmd.getColumnCount();
                resultSet.last();
                int numOfRows = resultSet.getRow();
                resultSet.beforeFirst();

                for (int i = 1; i <= numOfRows; i++) {
                    resultSet.next();
                    ArrayList<String> row = new ArrayList<>();
                    for (int j = 1; j <= numOfColumns; j++) {
                        row.add(resultSet.getString(j));
                    }
                    rows.add(row);
                }
            } catch (SQLException e) {
               JOptionPane.showMessageDialog(null, e.getMessage());
            }

            Object[][] data = new String[rows.size()][columns.length];

            for (int i = 0; i < rows.size(); i++){
                for (int j = 0; j < columns.length; j++){
                    data[i][j] = rows.get(i).get(j);
                }
            }
            TableModel model = new DefaultTableModel(data, columns){
                public boolean isCellEditable(int row, int column){return false;}
            };
            JTable jTable = new JTable(model);
            jTable.setRowSelectionAllowed(true);
            jTable.setColumnSelectionAllowed(true);
            DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
            cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
            for (int i = 0; i < jTable.getColumnCount(); i++){
                jTable.getColumnModel().getColumn(i).setCellRenderer(cellRenderer);
            }
            jTable.setShowHorizontalLines(true);
            jTable.setFont(jTable.getFont().deriveFont(14f));
            jTable.setRowHeight(jTable.getRowHeight()+10);

            var panel = new JPanel();
            panel.setLayout(new BorderLayout());
            panel.add(jTable.getTableHeader(),BorderLayout.NORTH);
            panel.add(jTable, BorderLayout.CENTER);

            return new JScrollPane(panel);
        }
    }

}
