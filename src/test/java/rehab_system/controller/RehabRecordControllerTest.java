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

import rehab_system.data.BarthelIndex;
import rehab_system.data.RehabRecord;
import rehab_system.dto.RehabRecordListWrapper;
import rehab_system.service.RehabRecordService;

public class RehabRecordControllerTest {

  @Mock
  private RehabRecordService rehabRecordService;

  private RehabRecordController controller;

  @BeforeEach
  void setUp() throws Exception {
    MockitoAnnotations.openMocks(this);
    controller = new RehabRecordController(rehabRecordService);
  }

  @Test
  void testAddRehabRecord() {
    Long patientId = 1L;
    RehabRecord record = new RehabRecord();
    RedirectAttributes attrs = new RedirectAttributesModelMap();

    String view = controller.addRehabRecord(patientId, record, attrs);

    assertThat(view).isEqualTo("redirect:/patients/1/edit");
    assertThat(record.getPatientId()).isEqualTo(patientId);
    verify(rehabRecordService).addRehabRecord(record);

    @SuppressWarnings("unchecked")
    Map<String, Object> flashMap = (Map<String, Object>) attrs.getFlashAttributes();
    assertThat(flashMap).containsEntry("message", "リハビリ情報を追加しました。");
  }

  @Test
  void testUpdateRehabRecords() {
    Long patientId = 2L;
    RehabRecord record1 = new RehabRecord();
    RehabRecord record2 = new RehabRecord();
    RehabRecordListWrapper wrapper = new RehabRecordListWrapper();
    wrapper.setRehabRecords(Arrays.asList(record1, record2));

    RedirectAttributes attrs = new RedirectAttributesModelMap();

    String view = controller.updateRehabRecord(patientId, wrapper, attrs);

    assertThat(view).isEqualTo("redirect:/patients/2");
    assertThat(record1.getPatientId()).isEqualTo(patientId);
    assertThat(record2.getPatientId()).isEqualTo(patientId);
    verify(rehabRecordService).updateRehabRecord(record1);
    verify(rehabRecordService).updateRehabRecord(record2);

    @SuppressWarnings("unchecked")
    Map<String, Object> flashMap = (Map<String, Object>) attrs.getFlashAttributes();
    assertThat(flashMap).containsEntry("message", "リハビリ情報を更新しました。");
  }

  @Test
  void testDeleteRehabRecord() {
    Long patientId = 3L;
    Long recordId = 10L;
    RedirectAttributes attrs = new RedirectAttributesModelMap();

    String view = controller.deleteRehabRecord(patientId, recordId, attrs);

    assertThat(view).isEqualTo("redirect:/patients/3/edit");
    verify(rehabRecordService).deleteRehabRecord(recordId);

    @SuppressWarnings("unchecked")
    Map<String, Object> flashMap = (Map<String, Object>) attrs.getFlashAttributes();
    assertThat(flashMap).containsEntry("message", "リハビリ情報を削除しました。");
  }

  @Test
  void testGetMonthlyBarthelIndex_withNullParam() {
    Long patientId = 4L;
    Model model = new ConcurrentModel();

    when(rehabRecordService.getMonthlyBarthelAverage(eq(patientId), anyString()))
        .thenReturn(70.0);

    String view = controller.getMonthlyBarthelIndex(patientId, null, model);

    assertThat(view).isEqualTo("barthelIndex");
    assertThat(model.containsAttribute("barthelIndex")).isTrue();
    assertThat(model.containsAttribute("yearMonth")).isTrue();
    assertThat(model.containsAttribute("newRecord")).isTrue();

    BarthelIndex barthel = (BarthelIndex) model.getAttribute("barthelIndex");
    assertThat(barthel.getAverage()).isEqualTo(70);
    assertThat(barthel.getPatientId()).isEqualTo(patientId);
  }

  @Test
  void testGetMonthlyBarthelIndex_withEmptyParam() {
    Long patientId = 5L;
    Model model = new ConcurrentModel();

    when(rehabRecordService.getMonthlyBarthelAverage(eq(patientId), anyString()))
        .thenReturn(null); // 平均がnullの場合

    String view = controller.getMonthlyBarthelIndex(patientId, "", model);

    assertThat(view).isEqualTo("barthelIndex");

    BarthelIndex barthel = (BarthelIndex) model.getAttribute("barthelIndex");
    assertThat(barthel.getAverage()).isEqualTo(0); // null → 0に変換されているか確認
  }
}