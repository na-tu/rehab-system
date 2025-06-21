package rehab_system;

import java.util.List;

public class PatientDetailDTO {

  private Patient patient;                  // 患者情報
  private List<RehabRecord> rehabRecords;   // リハビリ記録リスト

  // コンストラクタ（省略可）
  public PatientDetailDTO() {}

  public PatientDetailDTO(Patient patient, List<RehabRecord> rehabRecords) {
    this.patient = patient;
    this.rehabRecords = rehabRecords;
  }

  // Getter & Setter
  public Patient getPatient() {
    return patient;
  }

  public void setPatient(Patient patient) {
    this.patient = patient;
  }

  public List<RehabRecord> getRehabRecords() {
    return rehabRecords;
  }

  public void setRehabRecords(List<RehabRecord> rehabRecords) {
    this.rehabRecords = rehabRecords;
  }
}