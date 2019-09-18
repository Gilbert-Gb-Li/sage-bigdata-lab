import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ListComponent} from '../../BaseComponent';
import {EnumService, EnumType} from './enum.service';

@Component({
  selector: 'app-enum-type',
  templateUrl: './enum-type.component.html',
  styleUrls: ['./enum-type.component.less']
})
export class EnumTypeComponent extends ListComponent implements OnInit {
  listData: EnumType[];

  constructor(private router: Router, private enumService: EnumService) {
    super();
  }


  ngOnInit() {
    this.loadData(1);
  }

  edit(item: any): void {
    console.log(item);
  }

  loadData(pi: number): void {
    this.loading = true;
    this.enumService.getPager(pi, this.pageSize).subscribe(data => {
      this.listData = data;
      this.loading = false;
    });
  }
}
