import { Component, OnInit } from '@angular/core';
import { TemplateDRTDTO } from '../model/templateDRTDTO';
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { TemplateService } from './template-form.service';

@Component({
  selector: 'app-template-category',
  templateUrl: './template-category.component.html',
  styleUrls: ['./template-category.component.css'], 
  providers:[TemplateService]
})
export class TemplateCategoryComponent implements OnInit {

  template: TemplateDRTDTO = new TemplateDRTDTO();

  templateForm = new FormGroup({
    minRent: new FormControl(''),
    maxRent: new FormControl(''),
    bronzeMinRent: new FormControl(''),
    bronzeMaxRent: new FormControl(''),
    silverMinRent: new FormControl(''),
    silverMaxRent: new FormControl(''),
    goldMinRent: new FormControl(''),
    goldMaxRent: new FormControl('')

  });
  


  constructor(private templateService: TemplateService, private formBuilder: FormBuilder) {}


  ngOnInit(): void {
      //this.createForm();

  }

  setCategory() {
    this.template = this.templateForm.value;  
      this.templateService.setCategory(this.template).subscribe(data => {
          alert("Category ranges updated!");
      })

  }

}