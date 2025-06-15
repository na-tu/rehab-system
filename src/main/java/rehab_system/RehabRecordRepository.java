package rehab_system;

import java.util.List;
import java.util.Map;

public interface RehabRecordRepository {
  List<Map<String, Object>> findAllRecords(); // メソッド名は任意（Mapperに合わせる）
}

