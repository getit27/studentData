/*
 * Copyright 2012-2019 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cn.edu.nju.studentdata.controller;

import java.util.Collection;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import cn.edu.nju.studentdata.model.Student;
import cn.edu.nju.studentdata.repository.StudentRepository;

/**
 * @author Juergen Hoeller
 * @author Ken Krebs
 * @author Arjen Poutsma
 * @author Michael Isvy
 */
@Controller
class StudentController {

	private static final String VIEWS_OWNER_CREATE_OR_UPDATE_FORM = "owners/createOrUpdateOwnerForm";

	//private final OwnerRepository owners;

	private final StudentRepository students;

	//private VisitRepository visits;

	/*public OwnerController(OwnerRepository clinicService, VisitRepository visits) {
		this.owners = clinicService;
		this.visits = visits;
	}*/

	public StudentController(StudentRepository students){
		this.students=students;
	}

	@InitBinder
	public void setAllowedFields(WebDataBinder dataBinder) {
		dataBinder.setDisallowedFields("id");
	}

	@GetMapping("/owners/new")
	public String initCreationForm(Map<String, Object> model) {
		Student student = new Student();
		model.put("student", student);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/owners/new")
	public String processCreationForm(@Valid Student student, BindingResult result) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			this.students.save(student);
			return "redirect:/owners/" + student.getId();
		}
	}

	@GetMapping("/student/find")
	public String initFindForm(Map<String, Object> model) {
		model.put("student", new Student());
		return "owners/findStudent";
	}


	@GetMapping("/owners")
	public String processFindForm(Student student, BindingResult result, Map<String, Object> model) {

		// allow parameterless GET request for /owners to return all records
		if (student.getName() == null) {
			student.setName(""); // empty string signifies broadest possible search
		}

		// find owners by last name
		Collection<Student> results = this.students.findByName(student.getName());
		if (results.isEmpty()) {
			// no owners found
			result.rejectValue("name", "notFound", "not found");
			return "owners/findStudent";
		}
		else if (results.size() == 1) {
			// 1 owner found
			student = results.iterator().next();
			return "redirect:/owners/" + student.getId();
		}
		else {
			// multiple owners found
			model.put("selections", results);
			return "owners/ownersList";
		}
	}

	@GetMapping("/owners/{ownerId}/edit")
	public String initUpdateOwnerForm(@PathVariable("ownerId") int studentId, Model model) {
		Student student= this.students.findById(studentId);
		model.addAttribute(student);
		return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
	}

	@PostMapping("/owners/{ownerId}/edit")
	public String processUpdateOwnerForm(@Valid Student student, BindingResult result,
			@PathVariable("ownerId") int studentId) {
		if (result.hasErrors()) {
			return VIEWS_OWNER_CREATE_OR_UPDATE_FORM;
		}
		else {
			student.setId(studentId);
			this.students.save(student);
			return "redirect:/owners/{ownerId}";
		}
	}


	@GetMapping("/owners/{studentId}")
	public ModelAndView showOwner(@PathVariable("studentId") int studentId) {
		ModelAndView mav = new ModelAndView("owners/ownerDetails");
		Student student = this.students.findById(studentId);
		/*for (Pet pet : owner.getPets()) {
			pet.setVisitsInternal(visits.findByPetId(pet.getId()));
		}*/
		mav.addObject(student);
		return mav;
	}


}
