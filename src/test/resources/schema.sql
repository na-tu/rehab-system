CREATE TABLE patients (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255),
  age INT,
  gender VARCHAR(10),
  disease_name VARCHAR(255),
  start_date DATE,
  end_date DATE,
  notes TEXT
);

CREATE TABLE rehab_record (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  patient_id BIGINT,
  date DATE,
  content TEXT,
  barthel_index INT,
  FOREIGN KEY (patient_id) REFERENCES patients(id) ON DELETE CASCADE
);

CREATE TABLE barthel_index (
  patient_id BIGINT,
  year_month VARCHAR(7),
  average INT,
  PRIMARY KEY (patient_id, year_month)
);
