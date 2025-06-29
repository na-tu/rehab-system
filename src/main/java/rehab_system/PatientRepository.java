package rehab_system;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PatientRepository {
  // REST API用
  Map<String, Object> findPatientById(Long id);
  List<Map<String, Object>> findAllPatients();
  void insertPatientFromMap(Map<String, Object> patient);

  // View用
  void insertPatient(Patient patient);
  List<Patient> findAllPatientsForView();
  Patient findPatientByIdForView(Long id);

  void updatePatient(Patient patient);

  void deletePatient(Long id);
  }
