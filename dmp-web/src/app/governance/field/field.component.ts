import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ListComponent} from '../../BaseComponent';
import {Field, FieldService} from './field.service';

@Component({
  selector: 'app-field',
  templateUrl: './field.component.html',
  styleUrls: ['./field.component.less']
})
export class FieldComponent extends ListComponent implements OnInit {
  listData: Field[];

  constructor(private router: Router, private fieldService: FieldService) {
    super();
    console.log('constructor', fieldService);
  }

  ngOnInit() {
    this.loadData(1);
  }

  edit(item: any): void {
    console.log(item);
  }

  loadData(pi: number): void {
    this.loading = true;
    this.fieldService.getPager(pi, this.pageSize).subscribe(data => {
      this.listData = data;
      this.loading = false;
    });
  }
}
