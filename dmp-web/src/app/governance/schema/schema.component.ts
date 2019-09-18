import {Component, OnInit} from '@angular/core';
import {NzContextMenuService, NzDropdownMenuComponent, NzFormatEmitEvent, NzTreeNode} from 'ng-zorro-antd';
import {Router} from '@angular/router';
import {Schema, SchemaService} from './schema.service';
import {ListComponent} from '../../BaseComponent';
import {BizAppService} from '../biz-app/biz-app.service';
import {BizApp} from '../biz-app/biz-app.model';

@Component({
  selector: 'app-data-schema',
  templateUrl: './schema.component.html',
  styleUrls: ['./schema.component.less']
})
export class SchemaComponent extends ListComponent implements OnInit {
  activedNode: NzTreeNode;
  treeData: BizApp[];
  listData: Schema[];


  constructor(private nzContextMenuService: NzContextMenuService,
              private schemaService: SchemaService,
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
    this.schemaService.getPager(pi, this.pageSize).subscribe(data => {
      this.listData = data;
      this.loading = false;
    });
  }

  log(data: any): void {
    console.log('log', data);
  }

  edit(item: any): void {
    console.log(item);
  }
}
