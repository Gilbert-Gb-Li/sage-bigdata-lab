import {Component, OnInit} from '@angular/core';
import {BizAppService} from '../biz-app/biz-app.service';
import {Storage, StorageService} from '../storage/storage.service';
import {ZenContext} from '../../zen-chart/zen-context.model';
import {Lineage} from '../lineage/lineage.model';
import {ZenDemo} from '../../zen-chart/zen-demo.model';
import {BizApp} from '../biz-app/biz-app.model';

@Component({
  selector: 'app-data-index',
  templateUrl: './data-index.component.html',
  styleUrls: ['./data-index.component.less']
})
export class DataIndexComponent implements OnInit {
  app: number;
  apps: BizApp[];
  zenContext = new ZenContext();

  constructor(private appService: BizAppService,
              private storageService: StorageService) {
  }

  ngOnInit() {
    this.appService.getAll().subscribe(data => {
        console.log('appService', data);
        this.apps = data;
        if (this.apps) {
          this.app = this.apps[0].id;
        }
      }
    );

    // TEST
    ZenDemo.storages.forEach(storage => {
      console.log('storage', storage);
      this.zenContext.addNode(storage.id, storage.name, storage.stage);
    });
    ZenDemo.lines.forEach(line => {
      this.zenContext.addLine(line.fromNodes, line.distNode);
    });
  }

  toggleApp(id: number): void {
    console.log('click toggle app');
    this.zenContext.clean();
    this.storageService.getByAppId(id).subscribe(data => {
      console.log(data);
      this.addStorages(data);
    });
  }

  isOtherStage(stage: string): boolean {
    const stages: Array<string> = ['source', 'etl', ''];
    return stages.findIndex(item => item === stage) < 0;
  }

  addLinage(lines: Lineage[]): void {
    if (!lines) {
      return;
    }
    lines.forEach(line => this.zenContext.addLine(line.fromNodes, line.distNode));
  }

  addStorages(storages: Storage[]): void {
    if (!storages) {
      return;
    }
    storages.forEach(storage =>
      this.zenContext.addNode(storage.id, storage.name, storage.stage));
  }

  isVisible = false;

  showModal(): void {
    this.isVisible = true;
  }

  handleOk(): void {
    console.log('Button ok clicked!');
    this.isVisible = false;
  }

  handleCancel(): void {
    console.log('Button cancel clicked!');
    this.isVisible = false;
  }
}
