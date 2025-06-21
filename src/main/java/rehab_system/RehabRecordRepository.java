package rehab_system;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface RehabRecordRepository {
  List<RehabRecord> findByPatientId(Long patientId);
  void insertRehabRecord(RehabRecord record);

  // 追加：更新用
  void updateRehabRecord(RehabRecord record);

  // 追加：削除用
  void deleteRehabRecord(@Param("id") Long id);

  RehabRecord findById(Long id);
}
