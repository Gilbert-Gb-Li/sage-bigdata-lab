import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {ListComponent} from '../../BaseComponent';
import {BizAppService} from './biz-app.service';
import {BizApp} from './biz-app.model';

@Component({
  selector: 'app-biz-app',
  templateUrl: './biz-app.component.html',
  styleUrls: ['./biz-app.component.less']
})
export class BizAppComponent extends ListComponent implements OnInit {
  listData: BizApp[];
  entityId: number;
  isVisible = false;

  constructor(private appService: BizAppService,
              private router: Router) {
    super();
  }

  ngOnInit() {
    this.loadData(1);
  }

  edit(id: number): void {
    this.isVisible = true;
    if (id) {
      this.entityId = id;
    } else {
      this.entityId = null;
    }
    console.log(this.entityId);
  }

  loadData(pi: number): void {
    this.loading = true;
    this.appService.getPager(pi, this.pageSize).subscribe(data => {
      this.listData = data;
      this.loading = false;
    });
  }

  delete(item: any): void {
    console.log(item);
  }

  handleOk(): void {
    console.log('Button ok clicked!');
    this.isVisible = false;
  }

  handleCancel(): void {
    console.log('Button cancel clicked!');
    this.isVisible = false;
  }

  closeView(event: any): void {
    this.isVisible = false;
    this.loadData(1);
  }
}
