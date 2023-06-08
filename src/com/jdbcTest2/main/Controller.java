package com.jdbcTest2.main;

import com.formdev.flatlaf.intellijthemes.FlatNordIJTheme;
import com.jdbcTest2.main.frame.AppFrame;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.sql.*;
import java.util.Map;

/**
 * Main class of application. Controls flow of application and communicates with database.
 */
public class Controller extends AbstractAction {

    /**
     * Global public static object of Action type used to setup action listeners
     */
    public final static Action action = new Controller();
    /**
     * Global access to Connection object initilized after "login" action. Driver manager uses MYSQL driver
     */
    public static Connection conn;
    /**
     * Map of all patient data (ie. name, lastname, age etc.) used in SQL queries. Initilized in {@link AppFrame} class
     */
    private static Map<String,String> patientData;
    /**
     * ID number of patient primary key which was retrieved to perform UPDATE operations
     */
    private static int patientIdToUpdate = 0;

    private static PatientDAO patientDAO;

    /**
     * Private constructor to prevent creating new objects. Public final static field is created.
     */
    private Controller() {}

    /**
     * Main method launching application. Installs custom look and feel and starts application.
     * @param args No arguments are handled (Future note : parameter may be used to setup custom look and feel)
     */
    public static void main(String[] args){
        SwingUtilities.invokeLater(() ->{
            try {
                FlatNordIJTheme.setup();
                new AppFrame();
            } catch (Exception e){
                new AppFrame();
            }
        });
    }

    /**
     * Setter of patient ID field userd in "UPDATE" operations
     * @param id Value of Integer type to assign.
     */
    public static void setPatientIdToUpdate(int id){
        patientIdToUpdate = id;
    }

    public static void setPatientDAOObject(Patient patient){
        patientDAO = new PatientDAO(patient);
        patientDAO.setPatientIdToUpdate(patientIdToUpdate);
    }

    /**
     * Implementation of the only method from {@link AbstractAction}class which Main class extends.
     * @param e ActionEvent object
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        switch (e.getActionCommand()){
            case "login" -> {
                try {
                    //in case of quick connect loading screen won't be visible
                    switchPanels(AppFrame.getLoadingScreen());
                    conn = DatabaseConnectionManager.getConnection();
                    switchPanels(AppFrame.getMenuPanel());
                    //enables JMenuBar - as a default it is disabled
                    AppFrame.getMenu().setEnabled(true);
                } catch (SQLException ex) {
                    //if something went wrong inform user and come back to beginning
                    JOptionPane.showMessageDialog(null,"Couldn't connect to database. Contact your administrator to resolve problem.");
                    switchPanels(AppFrame.getStartPanel());
                }
            }
            // simple calls to switch panels
            case "newDonor" -> switchPanels(AppFrame.getBdonationPanel("submit"));
            case "updateData" -> switchPanels(AppFrame.getUpdatePanel());
            case "showStats" -> switchPanels(AppFrame.getReportPanel());
            // operation performs whenever user adds new record
            case "submit" -> {
                int idAddress;    // id of primary key from adres_pacjenta table
                int idPatient;  // id of primary key from pacjent table
                try {
                    if (patientDAO.isPhoneNumberValid()){
                        //add address if it doesn't exist
                        if (!(patientDAO.addressExists())){ patientDAO.addAddress(); }
                        idAddress = patientDAO.getAddressId();
                        // add patient is doesn't exist
                        if (!(patientDAO.patientExists())){ patientDAO.addPatient(idAddress); }
                        idPatient = patientDAO.getPatientId();
                        //add actual blood donation entry
                        patientDAO.addBloodDonation(idPatient);
                        //comes back to menu after operation is done
                        switchPanels(AppFrame.getMenuPanel());
                    } else {
                        // info to user if phone number is incorrect
                        JOptionPane.showMessageDialog(null,"Please enter correct phone number");
                    }
                } catch (SQLException ex) {
                    // bad handling of exception (for now inform user something went wrong and come back to menu)
                    // note for future - add at least error logs
                    JOptionPane.showMessageDialog(null,"Couldn't connect to database. Contact your administrator to resolve problem.");
                    switchPanels(AppFrame.getMenuPanel());
                }
            }
            case "updateDataOfPatient" -> {
                try {
                    patientDAO.updateAddress();
                    patientDAO.updatePatientData();
                    patientDAO.updateBloodDono();
                    switchPanels(AppFrame.getUpdatePanel());
                    JOptionPane.showMessageDialog(null,"Data was updated");
                } catch (SQLException ex) {
                    // bad handling of exception (for now inform user something went wrong)
                    // note for future - add at least error logs
                    JOptionPane.showMessageDialog(null,"Updating operation failed.");
                }
            }
        }
    }



    /**
     * Setter for patient map object (ie. reference, not initializing)
     * @param map Map object of String,String type
     */
    public static void setPatientDataMap(Map<String,String> map){ patientData = map; }

    /**
     * Method handling "switching" panel to provided panel as argument.
     * @param panel Object of JComponent object (ideally JPanel or JScrollPane)
     */
    public static void switchPanels(JComponent panel){
        AppFrame.getWrapper().removeAll();
        AppFrame.getWrapper().add(panel);
        AppFrame.getWrapper().updateUI();
    }
}

