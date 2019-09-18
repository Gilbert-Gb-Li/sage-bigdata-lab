import {ZenConfig} from './zen-config.module';

export class ZenNode {
  id: number;
  title: string;
  stage: string;

  // 动态设置
  x: number;
  y: number;
  row: number;

  constructor(id: number, title: string, stage: string, row: number) {
    this.id = id;
    this.title = title;
    this.stage = stage;
    this.setX();
    this.setRow(row);
  }

  private setX(): void {
    switch (this.stage) {
      case ZenConfig.stageEtl:
        this.x = ZenConfig.stageEtlX;
        break;
      case ZenConfig.stageT:
        this.x = ZenConfig.stageTX;
        break;
      case ZenConfig.stageSnapshot:
        this.x = ZenConfig.stageSnapshotX;
        break;
      case ZenConfig.stageMid:
        this.x = ZenConfig.stageMidX;
        break;
      case ZenConfig.stageStats:
        this.x = ZenConfig.stageStatsX;
        break;
      default:
        this.x = ZenConfig.stageRawX;
        this.stage = ZenConfig.stageRaw;
        break;
    }
  }

  private setRow(row: number): void {
    let height = (row) * ZenConfig.nodeHeight + ZenConfig.svgPaddingTop;
    if (row) {
      height += row * ZenConfig.nodeMarginV;
    }
    height += ZenConfig.stageHeight + ZenConfig.stageMarginBottom;
    this.y = height;
    this.row = row;
  }

}
