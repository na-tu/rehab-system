-- 初期患者
INSERT INTO patients (id, name, age, gender, disease_name, start_date, end_date, notes)
VALUES
  (1, '山田太郎', 75, '男性', '脳梗塞', '2024-01-10', NULL, '右片麻痺あり'),
  (2, '佐藤花子', 80, '女性', 'パーキンソン病', '2024-03-15', NULL, '転倒歴あり');

-- 初期リハビリ記録
INSERT INTO rehab_record (id, patient_id, date, content, barthel_index)
VALUES
  (1, 1, '2024-02-01', '歩行訓練（杖使用）', 70),
  (2, 1, '2024-02-08', 'バランストレーニング', 75),
  (3, 2, '2024-03-20', '筋力トレーニング', 65);

-- Barthel Index 月別平均（テスト用）
INSERT INTO barthel_index (patient_id, year_month, average)
VALUES
  (1, '2024-02', 72),
  (2, '2024-03', 65);
