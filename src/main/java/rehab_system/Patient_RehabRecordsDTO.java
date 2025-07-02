package rehab_system;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import rehab_system.data.Patient;
import rehab_system.data.RehabRecord;

@Setter
@Getter
public class Patient_RehabRecordsDTO {

  private Patient patient;                  // 患者情報
  private List<RehabRecord> rehabRecords;   // リハビリ記録リスト

  public Patient_RehabRecordsDTO() {}

  public Patient_RehabRecordsDTO(Patient patient, List<RehabRecord> rehabRecords) {
    this.patient = patient;
    this.rehabRecords = rehabRecords;
  }

}