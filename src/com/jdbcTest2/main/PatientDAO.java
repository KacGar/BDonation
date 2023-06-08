package com.jdbcTest2.main;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PatientDAO {

    private final Patient patientData;
    private  int patientIdToUpdate;

    PatientDAO(Patient patient) {
        this.patientData = patient;
    }

    public void setPatientIdToUpdate(int IDnumber){
        patientIdToUpdate = IDnumber;
    }

    /**
     * Validates phone number by calling prepared procedure in database.
     * @return True value if SQL query returns 1, false otherwise.
     * @throws SQLException Throws exception if access/timeout error happens or ResultSet object is not returned
     */
    public boolean isPhoneNumberValid() throws SQLException {
        String isPhoneNumValid = "CALL sprawdz_numerTel(?)";
        PreparedStatement ps = Controller.conn.prepareStatement(isPhoneNumValid, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setString(1,patientData.getPhoneNumber());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString(1).equals("1");
    }

    /**
     * Tests whether provided address already exists in database.
     * @return True value if SQL query returns 1, false otherwise.
     * @throws SQLException Throws exception if access/timeout error happens or ResultSet object is not returned
     */
    public boolean addressExists() throws SQLException {
        String checkAddress = "SELECT EXISTS(SELECT * FROM adres_pacjenta WHERE ulica = ? AND miasto = ? AND kod_pocztowy = ? AND kraj = ?)";
        PreparedStatement ps = Controller.conn.prepareStatement(checkAddress, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setString(1,patientData.getStreet());
        ps.setString(2,patientData.getCity());
        ps.setString(3,patientData.getCityCode());
        ps.setString(4,patientData.getCountry());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString(1).equals("1");
    }

    /**
     * Tests whether patient already exists in database.
     * @return True value if SQL query returns 1, false otherwise.
     * @throws SQLException Throws exception if access/timeout error happens or ResultSet object is not returned
     */
    public boolean patientExists() throws SQLException {
        String checkIfPatienExists = "SELECT EXISTS(SELECT * FROM pacjent WHERE social_num = ?)";
        PreparedStatement ps = Controller.conn.prepareStatement(checkIfPatienExists, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setString(1,patientData.getSocialNumber());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getString(1).equals("1");
    }

    /**
     * Returns ID number of patient address from data initialized in patientData field.
     * @return ID number of address
     * @throws SQLException Throws exception if access/timeout error happens or ResultSet object is not returned
     */
    public int getAddressId() throws SQLException {
        String getID = "SELECT idadres FROM adres_pacjenta WHERE ulica = ? AND miasto = ? AND kod_pocztowy = ? AND kraj = ?";
        PreparedStatement ps = Controller.conn.prepareStatement(getID, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setString(1,patientData.getStreet());
        ps.setString(2,patientData.getCity());
        ps.setString(3,patientData.getCityCode());
        ps.setString(4,patientData.getCountry());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    /**
     * Returns ID number of patient from data initialized in patientData field.
     * @return ID number of patient
     * @throws SQLException Throws exception if access/timeout error happens or ResultSet object is not returned
     */
    public int getPatientId() throws SQLException {
        String getID = "SELECT idpacjent FROM pacjent WHERE social_num = ?";
        PreparedStatement ps = Controller.conn.prepareStatement(getID, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setString(1,patientData.getSocialNumber());
        ResultSet rs = ps.executeQuery();
        rs.next();
        return rs.getInt(1);
    }

    /**
     * Adds patient data to database from data initialized in patientData field.
     * @param idAdres ID number of address (foreign key in pacjent table)
     * @throws SQLException Throws exception if access/timeout error happens or ResultSet object is not returned
     */
    public void addPatient(int idAdres) throws SQLException {
        String insertPatient = "INSERT INTO pacjent(social_num,imie_pacjenta,nazwisko_pacjenta,wiek,plec,waga,numer_tel,email,adres) VALUES (?,?,?,?,?,?,?,?,?)";
        PreparedStatement ps = Controller.conn.prepareStatement(insertPatient, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setString(1,patientData.getSocialNumber());
        ps.setString(2,patientData.getName());
        ps.setString(3,patientData.getLastName());
        ps.setInt(4,Integer.parseInt(patientData.getAge()));
        ps.setString(5,patientData.getSex());
        ps.setInt(6,Integer.parseInt(patientData.getWeight()));
        ps.setString(7,patientData.getPhoneNumber());
        ps.setString(8,patientData.getEmail());
        ps.setInt(9,idAdres);
        ps.executeUpdate();
    }

    /**
     * Adds patient address to database from data initialized in patientData field.
     * @throws SQLException Throws exception if access/timeout error happens or ResultSet object is not returned
     */
    public void addAddress() throws SQLException {
        String insertAddress = "INSERT INTO adres_pacjenta(ulica,miasto,kod_pocztowy,kraj) VALUES (?,?,?,?)";
        PreparedStatement ps = Controller.conn.prepareStatement(insertAddress, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setString(1,patientData.getStreet());
        ps.setString(2,patientData.getCity());
        ps.setString(3,patientData.getCityCode());
        ps.setString(4,patientData.getCountry());
        ps.executeUpdate();
    }

    /**
     * Adds blood donation record to database.
     * @param idPatient ID of existing patient in database.
     * @throws SQLException Throws exception if access/timeout error happens or ResultSet object is not returned
     */
    public void addBloodDonation(int idPatient) throws SQLException {
        String insertBD = "INSERT INTO pobranie_krwi(id_pacjenta,data_pobrania,czas_pobrania,ilosc_krwi,grupa_krwi,zarejestrowany_w_banku) VALUES(?,?,?,?,?,?)";
        PreparedStatement ps = Controller.conn.prepareStatement(insertBD, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1,idPatient);
        ps.setString(2,patientData.getDate());
        ps.setString(3,patientData.getTime());
        ps.setInt(4,Integer.parseInt(patientData.getAmountOfBlood()));
        ps.setString(5,patientData.getBloodType());
        ps.setInt(6,Integer.parseInt(patientData.isRegistered()));
        ps.executeUpdate();
    }

    /**
     * Updates address data in database from patientData map.
     * @throws SQLException Throws exception if access/timeout error happens or ResultSet object is not returned
     */
    public void updateAddress() throws SQLException {
        String getAddressID = "SELECT adres FROM pacjent WHERE idpacjent = ?";
        PreparedStatement ps = Controller.conn.prepareStatement(getAddressID, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setInt(1, patientIdToUpdate);
        ResultSet rs = ps.executeQuery();
        rs.next();
        int adresID = rs.getInt(1);

        String updateAddress = "UPDATE adres_pacjenta SET ulica = ?, miasto = ?, kod_pocztowy = ?, kraj = ? WHERE idadres = ?";
        ps = Controller.conn.prepareStatement(updateAddress, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setString(1,patientData.getStreet());
        ps.setString(2,patientData.getCity());
        ps.setString(3,patientData.getCityCode());
        ps.setString(4,patientData.getCountry());
        ps.setInt(5,adresID);
        ps.executeUpdate();
    }

    /**
     * Updates patiend data in database.
     * @throws SQLException Throws exception if access/timeout error happens or ResultSet object is not returned
     */
    public void updatePatientData() throws SQLException {
        String updatePatient = "UPDATE pacjent SET social_num = ?, imie_pacjenta = ?, nazwisko_pacjenta = ?, wiek = ?, plec = ?, waga = ?, numer_tel = ?, email = ? WHERE idpacjent = ?";
        PreparedStatement ps = Controller.conn.prepareStatement(updatePatient, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setString(1,patientData.getSocialNumber());
        ps.setString(2,patientData.getName());
        ps.setString(3,patientData.getLastName());
        ps.setInt(4,Integer.parseInt(patientData.getAge()));
        ps.setString(5,patientData.getSex());
        ps.setInt(6,Integer.parseInt(patientData.getWeight()));
        ps.setString(7,patientData.getPhoneNumber());
        ps.setString(8,patientData.getEmail());
        ps.setInt(9,patientIdToUpdate);
        ps.executeUpdate();
    }

    /**
     * Updates data of blood donation in database.
     * @throws SQLException Throws exception if access/timeout error happens or ResultSet object is not returned
     */
    public void updateBloodDono() throws SQLException {
        String updateBloodDono = "UPDATE pobranie_krwi SET data_pobrania = ?, czas_pobrania = ?, ilosc_krwi = ?, grupa_krwi = ? , zarejestrowany_w_banku = ? WHERE id_pacjenta = ?";
        PreparedStatement ps = Controller.conn.prepareStatement(updateBloodDono, ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
        ps.setString(1,patientData.getDate());
        ps.setString(2,patientData.getTime());
        ps.setInt(3,Integer.parseInt(patientData.getAmountOfBlood()));
        ps.setString(4,patientData.getBloodType());
        ps.setInt(5,Integer.parseInt(patientData.isRegistered()));
        ps.setInt(6,patientIdToUpdate);
        ps.executeUpdate();
    }
}
