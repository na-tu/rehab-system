package rehab_system.repository;

import java.util.List;
import java.util.Map;
import org.apache.ibatis.annotations.Mapper;
import rehab_system.data.Patient;

@Mapper
public interface PatientRepository {
  // REST API
  Map<String, Object> findPatientById(Long id);
  List<Map<String, Object>> findAllPatients();
  void insertPatientFromMap(Map<String, Object> patient);

  // View
  void insertPatient(Patient patient);
  List<Patient> findAllPatientsForView();
  Patient findPatientByIdForView(Long id);

  void updatePatient(Patient patient);

  void deletePatient(Long id);
  }
