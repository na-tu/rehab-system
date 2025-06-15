package rehab_system;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

  private final PatientRepository patientRepository;

  public PatientService(PatientRepository patientRepository) {
    this.patientRepository = patientRepository;
  }

  // REST API用
  public Map<String, Object> getPatient(Long id) {
    return patientRepository.findPatientById(id);
  }

  public List<Map<String, Object>> getAllPatients() {
    return patientRepository.findAllPatients();
  }

  public void addPatient(Map<String, Object> patient) {
    patientRepository.insertPatientFromMap(patient);
  }

  // View（Thymeleaf）用
  public void registerPatient(Patient patient) {
    patientRepository.insertPatient(patient);
  }

  public List<Patient> getAllPatientsForView() {
    return patientRepository.findAllPatientsForView();
  }

  // 詳細取得
  public Patient getPatientForView(Long id) {
    return patientRepository.findPatientByIdForView(id);
  }

  // 更新
  public void updatePatient(Patient patient) {
    patientRepository.updatePatient(patient);
  }

  // 削除
  public void deletePatient(Long id) {
    patientRepository.deletePatient(id);
  }
}
