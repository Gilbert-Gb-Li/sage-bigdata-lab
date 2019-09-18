import {Component, OnInit} from '@angular/core';
import {NzContextMenuService, NzDropdownMenuComponent, NzFormatEmitEvent, NzTreeNode} from 'ng-zorro-antd';
import {Router} from '@angular/router';
import {ListComponent} from '../../BaseComponent';
import {Storage, StorageService} from './storage.service';
import { BizAppService} from '../biz-app/biz-app.service';
import {BizApp} from '../biz-app/biz-app.model';

@Component({
  selector: 'app-data-store',
  templateUrl: './storage.component.html',
  styleUrls: ['./storage.component.less']
})
export class StorageComponent extends ListComponent implements OnInit {
  activedNode: NzTreeNode;
  treeData: BizApp[];
  listData: Storage[];

  constructor(private nzContextMenuService: NzContextMenuService,
              private storageService: StorageService,
              private appService: BizAppService,
              private router: Router) {
    super();
  }

  ngOnInit() {
    this.loadData(1);
    this.appService.getAll().subscribe(data => {
        console.log('appService', data);
        this.treeData = data;
      }
    );
  }

  link(url: string): void {
    this.router.navigate([url]);
  }


  toAddDataType(): void {
    // TODO
  }

  selectNode(data: NzFormatEmitEvent): void {
    // tslint:disable-next-line:no-non-null-assertion
    this.activedNode = data.node!;
    console.log('select node', data);
  }

  contextMenu($event: MouseEvent, menu: NzDropdownMenuComponent): void {
    this.nzContextMenuService.create($event, menu);
  }

  selectDropdown(): void {
    // do something
  }

  loadData(pi: number): void {
    this.loading = true;
    this.storageService.getPager(pi, this.pageSize).subscribe(data => {
      this.listData = data;
      this.loading = false;
    });
  }


  edit(item: any): void {
    console.log(item);
  }

}
