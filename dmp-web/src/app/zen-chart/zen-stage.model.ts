import {ZenConfig} from './zen-config.module';

export class ZenStage {
  name: string;
  x: number;
  y: number = ZenConfig.svgPaddingTop;

  constructor(name: string, x: number) {
    this.name = name;
    this.x = x;
  }
}
