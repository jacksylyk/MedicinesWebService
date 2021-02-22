package repositories;

import data.interfaces.IDBManager;
import entities.Medicine;
import repositories.interfaces.IMedicineRepository;

import javax.inject.Inject;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MedicineRepository implements IMedicineRepository {
    @Inject
    private IDBManager idbManager;

    @Override
    public ArrayList<Medicine> searchMedicineByName(String name) {
        Connection connection = null;

        try {
            connection = idbManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM medicines WHERE name LIKE '%"+ name + "%'");

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Medicine> medicines = new ArrayList<>();

            while (resultSet.next()) {
                Medicine medicine = new Medicine(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getDate("expirationDate").toLocalDate(),
                        resultSet.getString("manufacturer"));

                medicines.add(medicine);
            }

            return medicines;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public Medicine getMedicineById(int id) {
        Connection connection = null;

        try {
            connection = idbManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM medicines WHERE id=?");

            preparedStatement.setInt(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                Medicine medicine = new Medicine(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getDate("expirationDate").toLocalDate(),
                        resultSet.getString("manufacturer"));

                return medicine;
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    public boolean addMedicine(Medicine medicine) {
        Connection connection = null;

        try {
            connection = idbManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO medicines(name, price, expirationDate, manufacturer) VALUES (?,?,?,?)");

            preparedStatement.setString(1, medicine.getName());
            preparedStatement.setDouble(2, medicine.getPrice());
            preparedStatement.setDate(3, Date.valueOf(medicine.getExpirationDate()));
            preparedStatement.setString(4, medicine.getManufacturer());

            preparedStatement.execute();

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }

    @Override
    public boolean deleteMedicineById(int id) {
        Connection connection = null;

        try {
            connection = idbManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM medicines WHERE id=?");

            preparedStatement.setInt(1, id);

            preparedStatement.execute();

            return true;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                connection.close();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    public static ArrayList<Medicine> searchMedByManufacturer(String manufacturer) {
        Connection connection = null;

        try {
            connection = idbManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM medicines WHERE manufacturer LIKE '%" + manufacturer + "%'");

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Medicine> medicines = new ArrayList<>();

            while (resultSet.next()) {
                Medicine medicine = new Medicine(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getDate("expirationDate").toLocalDate(),
                        resultSet.getString("manufacturer"));

                medicines.add(medicine);
            }

            return medicines;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public ArrayList<Medicine> cheaperMedThanX(int fixprice) {
        Connection connection = null;

        try {
            connection = idbManager.getConnection();

            PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM medicines WHERE price < '" + fixprice + "'");

            ResultSet resultSet = preparedStatement.executeQuery();

            ArrayList<Medicine> medicines = new ArrayList<>();

            while (resultSet.next()) {
                Medicine medicine = new Medicine(resultSet.getInt("id"),
                        resultSet.getString("name"),
                        resultSet.getDouble("price"),
                        resultSet.getDate("expirationDate").toLocalDate(),
                        resultSet.getString("manufacturer"));

                medicines.add(medicine);
            }

            return medicines;
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public List<Medicine> getAllMed() {
        Connection con = null;
        try {
            con = idbManager.getConnection();
            String sql = "SELECT * FROM medicines";
            Statement st = con.createStatement();

            ResultSet rs = st.executeQuery(sql);
            List<Medicine> medicines = new LinkedList<>();
            while (rs.next()) {
                Medicine medicine = new Medicine(rs.getString("name"),
                        rs.getDouble("price"),
                        rs.getDate("LocalDate"),
                        rs.getString("manufacturer"));

                medicines.add(medicine);
            }

            return medicines;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            try {
                con.close();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        return null;
    }
}
