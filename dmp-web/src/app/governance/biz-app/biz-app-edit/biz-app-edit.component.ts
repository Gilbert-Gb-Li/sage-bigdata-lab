import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {BizApp, BizAppService} from '../biz-app.service';

@Component({
  selector: 'app-biz-app-edit',
  templateUrl: './biz-app-edit.component.html',
  styleUrls: ['./biz-app-edit.component.less']
})
export class BizAppEditComponent implements OnInit, OnChanges {
  @Input()
  isVisible: boolean;
  @Output() isVisibleChange = new EventEmitter<boolean>();
  isVisible2 = true;
  entity = new BizApp();
  @Input()
  id: number;


  constructor(private bizAppService: BizAppService) {
  }

  ngOnInit() {
    this.load();
  }

  private load() {
    if (this.id) {
      this.bizAppService.getById(this.id).subscribe(data => {
        this.entity = data;
      });
    } else {
      this.entity = new BizApp();
    }
  }

  ngOnChanges(changes: SimpleChanges) {
    this.load();
  }

  submitForm(): void {
    console.log(this.entity);
    this.bizAppService.add(this.entity).subscribe(data => {
      if (data === 1) {
        this.isVisible = false;
        this.isVisibleChange.emit(false);
        console.log('成功');
      } else {
        this.isVisible2 = false;
        console.log('失败');
      }
    });
  }
}
