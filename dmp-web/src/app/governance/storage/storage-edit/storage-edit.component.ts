import {Component, OnInit} from '@angular/core';
import {Storage, StorageService} from '../storage.service';
import {FormGroup} from '@angular/forms';

@Component({
  selector: 'app-storage-edit',
  templateUrl: './storage-edit.component.html',
  styleUrls: ['./storage-edit.component.less']
})
export class StorageEditComponent implements OnInit {
  entity = new Storage();
  id: number;

  constructor(private storageService: StorageService) {

  }

  ngOnInit() {
    //
    console.log('id', this.id);
    if (this.id) {
      this.storageService.getById(1).subscribe(data => {
        this.entity = data;
      });
    }
  }


  submitForm(): void {
    //
  }
}
