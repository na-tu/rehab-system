package rehab_system.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import rehab_system.data.Patient;
import rehab_system.data.Patient;
import rehab_system.data.RehabRecord;
import rehab_system.Patient_RehabRecordsDTO;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PatientServiceTest {

  @Autowired
  private PatientService patientService;

  // 患者詳細（DTO）取得のテスト
  @Test
  void getPatientDetail_正しくDTOが返される() {
    Patient_RehabRecordsDTO dto = patientService.getPatientDetail(1L);

    assertThat(dto).isNotNull();
    assertThat(dto.getPatient().getName()).isEqualTo("山田太郎");
    assertThat(dto.getRehabRecords()).isNotNull();
  }

  // Map形式で患者取得（REST API）
  @Test
  void getPatient_患者情報がMapで取得できる() {
    Map<String, Object> patient = patientService.getPatient(1L);

    assertThat(patient.get("name")).isEqualTo("山田太郎");
  }

  // 全患者一覧
  @Test
  void getAllPatients_全件取得できる() {
    List<Map<String, Object>> list = patientService.getAllPatients();

    assertThat(list).isNotEmpty();
  }

  // 患者登録
  @Test
  void addPatient_新規登録できる() {
    Map<String, Object> newPatient = Map.of(
        "name", "テスト太郎",
        "age", 65,
        "gender", "男性",
        "diseaseName", "高血圧",
        "startDate", "2024-06-01",
        "endDate", null,
        "notes", "特になし"
    );

    patientService.addPatient(newPatient);

    List<Map<String, Object>> patients = patientService.getAllPatients();
    assertThat(patients).anyMatch(p -> "テスト太郎".equals(p.get("name")));
  }

  // 患者登録
  @Test
  void registerPatient_オブジェクト形式で登録できる() {
    Patient patient = new Patient();
    patient.setName("テスト花子");
    patient.setAge(72);
    patient.setGender("女性");
    patient.setDiseaseName("リウマチ");
    patient.setStartDate(java.sql.Date.valueOf("2024-04-10"));

    patientService.registerPatient(patient);

    List<Patient> all = patientService.getAllPatientsForView();
    assertThat(all).anyMatch(p -> "テスト花子".equals(p.getName()));
  }

  // 更新テスト（nameだけ変更）
  @Test
  void updatePatient_名前を更新できる() {
    Patient patient = patientService.getPatientForView(1L);
    patient.setName("変更済太郎");

    patientService.updatePatient(patient);

    Patient updated = patientService.getPatientForView(1L);
    assertThat(updated.getName()).isEqualTo("変更済太郎");
  }

  // 削除テスト
  @Test
  void deletePatient_指定患者を削除できる() {
    Patient patient = new Patient();
    patient.setName("削除対象");
    patient.setAge(70);
    patient.setGender("男性");
    patient.setDiseaseName("糖尿病");
    patient.setStartDate(java.sql.Date.valueOf("2024-05-01"));
    patientService.registerPatient(patient);

    // 一度全件取得して最新のIDを取得
    List<Patient> all = patientService.getAllPatientsForView();
    Long targetId = all.stream()
        .filter(p -> "削除対象".equals(p.getName()))
        .map(Patient::getId)
        .findFirst().orElseThrow();

    patientService.deletePatient(targetId);

    List<Patient> afterDelete = patientService.getAllPatientsForView();
    assertThat(afterDelete).noneMatch(p -> targetId.equals(p.getId()));
  }
}