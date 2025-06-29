package rehab_system;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;

@Service
public class RehabRecordService {

  private final RehabRecordRepository rehabRecordRepository;

  public RehabRecordService(RehabRecordRepository rehabRecordRepository) {
    this.rehabRecordRepository = rehabRecordRepository;
  }

  // 指定患者IDのリハビリ記録一覧を取得
  public List<RehabRecord> getRecordsByPatientId(Long patientId) {
    List<RehabRecord> records = rehabRecordRepository.findByPatientId(patientId);
    // ★日付の降順（新しい日付が上）で並び替え
    records.sort((r1, r2) -> r2.getDate().compareTo(r1.getDate()));

    return records;
  }

  // リハビリ記録の新規登録
  public void addRehabRecord(RehabRecord record) {
    rehabRecordRepository.insertRehabRecord(record);
  }

  // 更新
  public void updateRehabRecord(RehabRecord record) {
    rehabRecordRepository.updateRehabRecord(record);
  }

  // 削除
  public void deleteRehabRecord(Long id) {
    rehabRecordRepository.deleteRehabRecord(id);
  }

  // RehabRecordService クラスの中に追加
  public RehabRecord getRecordById(Long id) {
    return rehabRecordRepository.findById(id);
  }
  // 月ごとのBarthelIndex平均を取得（Map方式）
  public Double getMonthlyBarthelAverage(Long patientId, String yearMonth) {
    Map<String, Object> params = new HashMap<>();
    params.put("patientId", patientId);
    params.put("yearMonth", yearMonth);
    return rehabRecordRepository.getMonthlyBarthelAverage(patientId, yearMonth);
  }


}
