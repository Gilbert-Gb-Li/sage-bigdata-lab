import {Component, OnInit} from '@angular/core';
import {NzContextMenuService, NzDropdownMenuComponent} from 'ng-zorro-antd/dropdown';
import {NzFormatEmitEvent, NzTreeNode} from 'ng-zorro-antd/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-demo',
  templateUrl: './demo.component.html',
  styleUrls: ['./demo.component.less']
})
export class DemoComponent implements OnInit {
  loading = true;
  activedNode: NzTreeNode;
  treeData = [
    {
      title: '抖音',
      key: '100',
      expanded: true,
      children: [
        {title: '用户', key: '1000', isLeaf: true},
        {title: '视频', key: '1001', isLeaf: true},
        {title: '话题', key: '1002', isLeaf: true},
        {title: '评论', key: '1003', isLeaf: true}
      ]
    },
    {
      title: '快手',
      key: '101',
      children: [
        {title: 'leaf 1-0', key: '1010', isLeaf: true},
        {title: 'leaf 1-1', key: '1011', isLeaf: true}
      ]
    }
  ];
  listData = [
    {
      title: 'Ant Design Title 1'
    },
    {
      title: 'Ant Design Title 2'
    },
    {
      title: 'Ant Design Title 3'
    },
    {
      title: 'Ant Design Title 4'
    }
  ];

  constructor(private nzContextMenuService: NzContextMenuService,
              private router: Router) {
  }

  ngOnInit() {
    this.loading = false;
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
    // TODO
    this.loading = true;
    this.sleep(1000).then(() => this.loading = false);
  }

  sleep(time): Promise<any> {
    return new Promise((resolve) => setTimeout(resolve, time));
  }

  edit(item: any): void {
    console.log(item);
  }
}
