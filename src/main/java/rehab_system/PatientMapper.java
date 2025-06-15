package rehab_system;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Insert;

@Mapper
public interface PatientMapper {
  @Select("SELECT * FROM patients WHERE id = #{id}")
  Patient findById(Long id);

  @Select("SELECT * FROM patients")
  List<Patient> findAll();

  @Insert("INSERT INTO patients(name, disease_name, start_date, end_date) VALUES(#{name}, #{diseaseName}, #{startDate}, #{endDate})")
  void insert(Patient patient);
}
