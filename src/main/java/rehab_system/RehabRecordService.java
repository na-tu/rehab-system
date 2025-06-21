package rehab_system;

import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class RehabRecordService {

  private final RehabRecordRepository rehabRecordRepository;

  public RehabRecordService(RehabRecordRepository rehabRecordRepository) {
    this.rehabRecordRepository = rehabRecordRepository;
  }

  // 指定患者IDのリハビリ記録一覧を取得
  public List<RehabRecord> getRecordsByPatientId(Long patientId) {
    return rehabRecordRepository.findByPatientId(patientId);
  }

  // リハビリ記録の新規登録
  public void addRehabRecord(RehabRecord record) {
    rehabRecordRepository.insertRehabRecord(record);
  }

  // 追加：更新
  public void updateRehabRecord(RehabRecord record) {
    rehabRecordRepository.updateRehabRecord(record);
  }

  // 追加：削除
  public void deleteRehabRecord(Long id) {
    rehabRecordRepository.deleteRehabRecord(id);
  }

  // RehabRecordService クラスの中に追加
  public RehabRecord getRecordById(Long id) {
    return rehabRecordRepository.findById(id);
  }

}
