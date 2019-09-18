import {Component, OnInit} from '@angular/core';
import {BizComponent, BizComponentService} from './biz-component.service';
import {ListComponent} from '../../BaseComponent';

@Component({
  selector: 'app-biz-component',
  templateUrl: './biz-component.component.html',
  styleUrls: ['./biz-component.component.less']
})
export class BizComponentComponent extends ListComponent implements OnInit {
  listData: BizComponent[];

  constructor(private service: BizComponentService) {
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
    this.service.getPager(pi, this.pageSize).subscribe(data => {
      this.listData = data;
      this.loading = false;
    });
  }

}
