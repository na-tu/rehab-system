package rehab_system;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/patients")
public class PatientViewController {

    private final PatientService patientService;

    @Autowired
    public PatientViewController(PatientService patientService) {
      this.patientService = patientService;
    }

    // 登録フォーム表示
    @GetMapping("/new")
    public String showRegistrationForm(Model model) {
      model.addAttribute("patient", new Patient());
      return "patient_new";
    }

    // 患者登録処理
    @PostMapping("/register")
    public String registerPatient(@ModelAttribute Patient patient) {
      patientService.registerPatient(patient);
      return "redirect:/patients/list";
    }

    // 一覧表示
    @GetMapping("/list")
    public String listPatients(Model model) {
      model.addAttribute("patients", patientService.getAllPatientsForView());
      return "patient_list";
    }
  // 詳細表示フォーム
  @GetMapping("/{id}")
  public String showPatientDetail(@PathVariable Long id, Model model) {
    Patient patient = patientService.getPatientForView(id);
    model.addAttribute("patient", patient);
    return "patient_detail";  // 詳細表示用テンプレート名
  }

  // 更新フォーム表示
  @GetMapping("/{id}/edit")
  public String showEditForm(@PathVariable Long id, Model model) {
    Patient patient = patientService.getPatientForView(id);
    model.addAttribute("patient", patient);
    return "patient_update";
  }

  // 更新処理
  @PostMapping("/{id}/update")
  public String updatePatient(@PathVariable Long id, @ModelAttribute Patient patient) {
    patient.setId(id);  // 念のためIDセット
    patientService.updatePatient(patient);
    return "redirect:/patients/list";
  }

  // 削除処理
  @PostMapping("/{id}/delete")
  public String deletePatient(@PathVariable Long id) {
    patientService.deletePatient(id);
    return "redirect:/patients/list";
  }
  }
