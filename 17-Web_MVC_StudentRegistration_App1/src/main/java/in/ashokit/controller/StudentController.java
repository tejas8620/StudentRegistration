
//StudentController.java

package in.ashokit.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import in.ashokit.binding.Student;
import in.ashokit.entity.StudentEntity;
import in.ashokit.repository.StudentRepository;
import jakarta.persistence.metamodel.SetAttribute;

@Controller
public class StudentController {

	@Autowired
	private StudentRepository repo;
	
	//method to load student form
	
	@GetMapping("/")
	public String loadForm(Model model) {
		
		loadFormData(model);
		
		return "index";
	}


	private void loadFormData(Model model) {
		List<String> coursesList = new ArrayList<>();
		coursesList.add("Java");
		coursesList.add("Python");
		coursesList.add("DevOps");
		coursesList.add(".Net");
		coursesList.add("SBMS");
		coursesList.add("AWS");
		
		List<String> timingsList = new ArrayList<>();
		timingsList.add("Morning");
		timingsList.add("Afternoon");
		timingsList.add("Evening");
		
		Student student = new Student();
		
		model.addAttribute("courses",coursesList);
		model.addAttribute("timings",timingsList);
		model.addAttribute("student", student);
	}
	
	
	//method to save student form data
	
	@PostMapping("/save")
	public String handleSubmit(Student s, Model model) {
		
		System.out.println(s);
		
		//load to save 
		StudentEntity entity = new StudentEntity();
		//copy data from binding obj entity obj 
		BeanUtils.copyProperties(s, entity);
		
		//Here both var datatypes diff so we are setting manually value with cnverting arays to toString format
		entity.setTimings(Arrays.toString(s.getTimings()));  
		
		// --> Here we are inserting records in database table...
		repo.save(entity);
		
		loadFormData(model);
		
		model.addAttribute("msg", "Student saved");
		
		return "index";
	}

	//method to display saved data
	
	@GetMapping("/viewStudents")
	public String getStudentData(Model model) {
		
		List<StudentEntity> studentsList = repo.findAll();
		
		model.addAttribute("students", studentsList);
		
		return "data";
	}
	
}
