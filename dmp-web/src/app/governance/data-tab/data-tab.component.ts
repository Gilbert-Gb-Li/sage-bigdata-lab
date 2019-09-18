import {Component, Input, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-data-tab',
  templateUrl: './data-tab.component.html',
  styleUrls: ['./data-tab.component.less']
})
export class DataTabComponent implements OnInit {
  @Input()
  private index = 0;

  constructor(private router: Router) {
  }

  ngOnInit() {
  }

  link(url: string): void {
    this.router.navigate([url]);
  }
}
