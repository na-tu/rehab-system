package rehab_system.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import rehab_system.data.Patient;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")  // application-test.properties を使う
@Transactional           // テスト後にDBをロールバック
class PatientRepositoryTest {

  @Autowired
  private PatientRepository patientRepository;

  @Test
  void testFindAllPatientsForView() {
    List<Patient> patients = patientRepository.findAllPatientsForView();
    assertThat(patients).isNotEmpty();
    assertThat(patients.get(0).getName()).isEqualTo("山田太郎"); // data.sqlの内容に応じて確認
  }

  @Test
  void testFindPatientByIdForView() {
    Patient patient = patientRepository.findPatientByIdForView(1L);
    assertThat(patient).isNotNull();
    assertThat(patient.getDiseaseName()).isEqualTo("脳梗塞");
  }
}
