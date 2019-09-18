import {ZenNode} from './zen-node.model';
import {ZenLine} from './zen-line.model';
import {ZenConfig} from './zen-config.module';
import {ZenStage} from './zen-stage.model';

const stages = [
  new ZenStage(ZenConfig.stageRaw, ZenConfig.stageRawX),
  new ZenStage(ZenConfig.stageEtl, ZenConfig.stageEtlX),
  new ZenStage(ZenConfig.stageT, ZenConfig.stageTX),
  new ZenStage(ZenConfig.stageSnapshot, ZenConfig.stageSnapshotX),
  new ZenStage(ZenConfig.stageMid, ZenConfig.stageMidX),
  new ZenStage(ZenConfig.stageStats, ZenConfig.stageStatsX),
];

export class ZenContext {
  stages = stages;
  nodes: ZenNode[] = new Array<ZenNode>();
  lines: ZenLine[] = new Array<ZenLine>();
  current: number;

  private getNodeById(nodeId: number): ZenNode {
    return this.nodes.find(node => node.id === nodeId);
  }

  getClass(id: number, defaultClass: string = ''): string {
    if (this.current === id) {
      return 'current';
    }
    return defaultClass;
  }

  setCurrent(id: number): void {
    this.current = id;
  }

  addLine(fromNodes: number[], distNode: number) {
    // console.log('nodes', this.nodes);
    const from = fromNodes.map(nodeId => this.getNodeById(nodeId));
    const dist = this.getNodeById(distNode);
    const zenLine = new ZenLine(from, dist, this);
    this.lines.push(zenLine);
    // console.log('addLine', fromNodes, from, distNode, dist, zenLine);
  }

  addNode(id: number, title: string, stage: string) {
    const realStage = stage ? stage : ZenConfig.stageDefault;
    const raw = this.nodes.filter(node => node.stage === realStage).length;
    const zenNode = new ZenNode(id, title, realStage, raw);
    this.nodes.push(zenNode);
  }

  clean() {
    this.nodes = new Array<ZenNode>();
    this.lines = new Array<ZenLine>();
  }

  getRowCount(stage: string): number {
    return this.nodes.filter(node => node.stage === stage).length;


    // switch (stage) {
    //   case ZenConfig.stageRaw:
    //     return 1;
    //   case ZenConfig.stageEtl:
    //     return 2;
    //   case ZenConfig.stageT:
    //     return 3;
    //   case ZenConfig.stageSnapshot:
    //     return 4;
    //   case ZenConfig.stageMid:
    //     return 5;
    //   case ZenConfig.stageStats:
    //     return 6;
    //   default:
    //     return 1;
    // }
  }

  getNodeRowIndex(node: ZenNode): number {
    return this.nodes.filter(tmp => tmp.stage === node.stage).findIndex(tmp => tmp.id === node.id);
  }
}
