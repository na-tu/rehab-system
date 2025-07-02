package rehab_system.controller;

import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;

import java.util.Arrays;
import java.util.Collections;

import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.ui.ConcurrentModel;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributesModelMap;

import rehab_system.Patient_RehabRecordsDTO;
import rehab_system.data.Patient;
import rehab_system.data.RehabRecord;
import rehab_system.service.PatientService;
import rehab_system.service.RehabRecordService;

public class PatientControllerTest {

  @Mock
  private PatientService patientService;

  @Mock
  private RehabRecordService rehabRecordService;

  private PatientController patientController;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
    patientController = new PatientController(patientService);
  }

  @Test
  void testShowRegistrationForm() {
    Model model = new ConcurrentModel();

    String view = patientController.showRegistrationForm(model);

    assertThat(view).isEqualTo("patient_new");
    assertThat(model.containsAttribute("patient")).isTrue();
  }

  @Test
  void testListPatients() {
    when(patientService.getAllPatientsForView()).thenReturn(Collections.emptyList());
    Model model = new ConcurrentModel();

    String view = patientController.listPatients(model);

    assertThat(view).isEqualTo("patient_list");
    assertThat(model.containsAttribute("patients")).isTrue();

    verify(patientService).getAllPatientsForView();
  }

  @Test
  void testShowPatientDetail() {
    Long id = 1L;
    Patient patient = new Patient();
    RehabRecord record = new RehabRecord();
    Patient_RehabRecordsDTO dto = new Patient_RehabRecordsDTO(patient, Arrays.asList(record));

    when(patientService.getPatientDetail(id)).thenReturn(dto);

    Model model = new ConcurrentModel();
    String view = patientController.showPatientDetail(id, model);

    assertThat(view).isEqualTo("patient_detail");
    assertThat(model.getAttribute("patient")).isEqualTo(patient);
    assertThat(model.getAttribute("rehabRecords")).isEqualTo(dto.getRehabRecords());

    verify(patientService).getPatientDetail(id);
  }

  @Test
  void testDeletePatient() {
    Long id = 1L;
    RedirectAttributes redirectAttributes = new RedirectAttributesModelMap();

    String redirect = patientController.deletePatient(id, redirectAttributes);

    assertThat(redirect).isEqualTo("redirect:/patients/list");
    @SuppressWarnings("unchecked")
    Map<String, Object> flashMap = (Map<String, Object>) redirectAttributes.getFlashAttributes();

    assertThat(flashMap).containsEntry("message", "患者情報を削除しました。");

    verify(patientService).deletePatient(id);
  }

  // 他のメソッドも同様に書けます
}