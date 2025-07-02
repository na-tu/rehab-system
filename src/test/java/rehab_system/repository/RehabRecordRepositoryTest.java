package rehab_system.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;

import rehab_system.data.RehabRecord;

@MybatisTest
@Sql({"/test-schema.sql", "/test-data.sql"})  // DB初期化SQLを指定
public class RehabRecordRepositoryTest {

  @Autowired
  private RehabRecordRepository rehabRecordRepository;

  @BeforeEach
  void setUp() {
    // 必要に応じて初期化処理
  }

  @Test
  void testFindByPatientId() {
    Long patientId = 1L;
    List<RehabRecord> records = rehabRecordRepository.findByPatientId(patientId);
    assertThat(records).isNotEmpty();
    assertThat(records.get(0).getPatientId()).isEqualTo(patientId);
  }

  @Test
  void testInsertRehabRecord() {
    RehabRecord record = new RehabRecord();
    record.setPatientId(2L);
    record.setDate(LocalDate.now());
    record.setContent("新しいリハビリ内容");

    rehabRecordRepository.insertRehabRecord(record);

    List<RehabRecord> records = rehabRecordRepository.findByPatientId(2L);
    assertThat(records).extracting("content").contains("新しいリハビリ内容");
  }

  @Test
  void testUpdateRehabRecord() {
    RehabRecord record = rehabRecordRepository.findById(1L);
    record.setContent("更新された内容");
    rehabRecordRepository.updateRehabRecord(record);

    RehabRecord updated = rehabRecordRepository.findById(1L);
    assertThat(updated.getContent()).isEqualTo("更新された内容");
  }

  @Test
  void testDeleteRehabRecord() {
    rehabRecordRepository.deleteRehabRecord(1L);
    RehabRecord deleted = rehabRecordRepository.findById(1L);
    assertThat(deleted).isNull();
  }

  @Test
  void testGetMonthlyBarthelAverage() {
    Double avg = rehabRecordRepository.getMonthlyBarthelAverage(1L, "2024-07");
    assertThat(avg).isNotNull();
  }
}