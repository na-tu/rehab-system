package rehab_system;

import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patients") // ←API用にパス変更
public class PatientRestController {

  @Autowired
  private PatientService service;

  @GetMapping("/")
  public String home() {
    return "Welcome to Rehab System !";
  }

  @GetMapping("/{id}")
  public Map<String, Object> getPatient(@PathVariable Long id) {
    return service.getPatient(id);
  }

  @GetMapping
  public List<Map<String, Object>> getAllPatients() {
    return service.getAllPatients();
  }

  @PostMapping
  public void addPatient(@RequestBody Map<String, Object> patient) {
    service.addPatient(patient);
  }

  @GetMapping("/{id}/detail")
  public PatientDetailDTO getPatientDetail(@PathVariable Long id) {
    return service.getPatientDetail(id);
  }

}
