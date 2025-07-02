package rehab_system.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import rehab_system.data.RehabRecord;
import rehab_system.repository.RehabRecordRepository;

public class RehabRecordServiceTest {

  private RehabRecordRepository rehabRecordRepository;
  private RehabRecordService rehabRecordService;

  @BeforeEach
  void setUp() {
    rehabRecordRepository = mock(RehabRecordRepository.class);
    rehabRecordService = new RehabRecordService(rehabRecordRepository);
  }

  @Test
  void testGetRecordsByPatientId_shouldReturnSortedList() {
    RehabRecord r1 = new RehabRecord();
    r1.setDate(LocalDate.of(2023, 1, 1));
    RehabRecord r2 = new RehabRecord();
    r2.setDate(LocalDate.of(2023, 5, 1));

    when(rehabRecordRepository.findByPatientId(1L)).thenReturn(Arrays.asList(r1, r2));

    List<RehabRecord> result = rehabRecordService.getRecordsByPatientId(1L);

    assertEquals(2, result.size());
    // 日付降順なので r2 が先頭
    assertEquals(LocalDate.of(2023, 5, 1), result.get(0).getDate());
    assertEquals(LocalDate.of(2023, 1, 1), result.get(1).getDate());
  }

  @Test
  void testAddRehabRecord_shouldCallInsert() {
    RehabRecord record = new RehabRecord();
    rehabRecordService.addRehabRecord(record);
    verify(rehabRecordRepository, times(1)).insertRehabRecord(record);
  }

  @Test
  void testUpdateRehabRecord_shouldCallUpdate() {
    RehabRecord record = new RehabRecord();
    rehabRecordService.updateRehabRecord(record);
    verify(rehabRecordRepository, times(1)).updateRehabRecord(record);
  }

  @Test
  void testDeleteRehabRecord_shouldCallDelete() {
    Long id = 10L;
    rehabRecordService.deleteRehabRecord(id);
    verify(rehabRecordRepository, times(1)).deleteRehabRecord(id);
  }

  @Test
  void testGetRecordById_shouldReturnRecord() {
    RehabRecord record = new RehabRecord();
    when(rehabRecordRepository.findById(5L)).thenReturn(record);

    RehabRecord result = rehabRecordService.getRecordById(5L);

    assertEquals(record, result);
  }

  @Test
  void testGetMonthlyBarthelAverage_shouldReturnAverage() {
    when(rehabRecordRepository.getMonthlyBarthelAverage(1L, "2023-07")).thenReturn(75.5);

    Double avg = rehabRecordService.getMonthlyBarthelAverage(1L, "2023-07");

    assertEquals(75.5, avg);
  }
}