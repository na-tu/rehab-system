package rehab_system;

import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class PatientService {

  private final PatientRepository patientRepository;
  private final RehabRecordService rehabRecordService;

  public PatientService(PatientRepository patientRepository, RehabRecordService rehabRecordService) {
    this.patientRepository = patientRepository;
    this.rehabRecordService = rehabRecordService;
  }

  // PatientDetailDTO を作成して返す
  public Patient_RehabRecordsDTO getPatientDetail(Long id) {
    Patient patient = patientRepository.findPatientByIdForView(id); // 患者1人
    List<RehabRecord> records = rehabRecordService.getRecordsByPatientId(id); // リハビリ記録

    Patient_RehabRecordsDTO dto = new Patient_RehabRecordsDTO();
    dto.setPatient(patient);
    dto.setRehabRecords(records);

    return dto;
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
    Patient patient = patientRepository.findPatientByIdForView(id);
    List<RehabRecord> rehabRecords = rehabRecordService.getRecordsByPatientId(id);
    patient.setRehabRecords(rehabRecords); // リハビリ記録セット
    //System.out.println("getPatientForView: startDate = " + patient.getStartDate());
    //System.out.println("getPatientForView: endDate = " + patient.getEndDate());
    return patient;
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
