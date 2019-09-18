import {Component, Input, OnInit} from '@angular/core';
import {StorageService} from '../governance/storage/storage.service';
import {ZenDemo} from './zen-demo.model';
import {ZenConfig} from './zen-config.module';
import {ZenContext} from './zen-context.model';

@Component({
  selector: 'app-zen-chart',
  templateUrl: './zen-chart.component.html',
  styleUrls: ['./zen-chart.component.less']
})
export class ZenChartComponent implements OnInit {
  @Input()
  private appId: number;
  @Input()
  zenContext: ZenContext;
  ZenConfig = ZenConfig;

  constructor(private storageService: StorageService) {
  }

  ngOnInit() {

  }

}





