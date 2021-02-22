package repositories.interfaces;

import entities.Medicine;

import java.util.ArrayList;
import java.util.List;

public interface IMedicineRepository {
    List<Medicine> searchMedicineByName(String name);
    Medicine getMedicineById(int id);
    boolean addMedicine(Medicine medicine);
    boolean deleteMedicineById(int id);
    ArrayList<Medicine> cheaperMedThanX(int fixprice);
    List<Medicine> getAllMed();
    ArrayList<Medicine> searchMedByManufacturer(String manufacturer);

}
