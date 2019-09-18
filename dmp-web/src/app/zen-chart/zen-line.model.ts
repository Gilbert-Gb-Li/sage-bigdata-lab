import {ZenNode} from './zen-node.model';
import {ZenConfig} from './zen-config.module';
import {ZenContext} from './zen-context.model';

export class ZenLine {
  fromNodes: ZenNode[];
  distNode: ZenNode;
  zenContext: ZenContext;

  constructor(fromNodes: ZenNode[], distNode: ZenNode, zenContext: ZenContext) {
    this.fromNodes = fromNodes;
    this.distNode = distNode;
    this.zenContext = zenContext;
  }

  getD(): string {
    // console.log('fromNodes', this.fromNodes);
    // console.log('distNode', this.distNode);


    let d = '';
    this.fromNodes.reverse().forEach(node => {
      const rows1 = this.zenContext.getRowCount(this.distNode.stage);
      const rows2 = this.zenContext.getRowCount(node.stage);
      const rows = (rows1 > rows2 ? rows1 : rows2) + 1;
      const lineOffset = this.getLineOffset();

      // console.log('rows:', rows);
      const stage: number = Number(String(this.distNode.id).substr(0, 1)) - Number(String(node.id).substr(0, 1));
      const level: number = Number(String(this.distNode.id).substr(1)) - Number(String(node.id).substr(1));
      // console.log('from node:' + node.id + ' to node:' + this.distNode.id + ' diff:' + level);
      if (stage === 0) {
        if (level === 0) {
          // console.log('ACTION:→ ↓ ← ↑ →');
          d += `M ${node.x + ZenConfig.nodeWidth} ${node.y + ZenConfig.nodeHeight / 2}`;
          d += `h ${ZenConfig.nodeWidth / rows * 1.5}`;
          d += `v ${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) / 2}`;
          d += `h ${-(ZenConfig.nodeWidth) + (ZenConfig.nodeWidth / rows) * -(1.5 * 2)}`;
          d += `v -${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) / 2}`;
          d += `L ${this.distNode.x} ${this.distNode.y + ZenConfig.nodeHeight / 2}`;
          // console.log(d);
        } else if (level < 0) {
          // console.log('ACTION:→ ↓ ← ↑ →');
          d += `M ${node.x + ZenConfig.nodeWidth} ${node.y + ZenConfig.nodeHeight / 2}`;
          d += `h ${ZenConfig.nodeWidth / rows * 1.5}`;
          d += `v ${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) / 2}`;
          d += `h ${-(ZenConfig.nodeWidth) + (ZenConfig.nodeWidth / rows) * -(1.5 * 2)}`;
          d += `v -${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * (-level) + (ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * 0.5}`;
          d += `L ${this.distNode.x} ${this.distNode.y + ZenConfig.nodeHeight / 2}`;
          // console.log(d);
        } else {
          // console.log('ACTION:→ ↓ ← ↓ →');
          d += `M ${node.x + ZenConfig.nodeWidth} ${node.y + ZenConfig.nodeHeight / 2}`;
          d += `h ${ZenConfig.nodeWidth / rows * 1.5}`;
          d += `v ${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) / 2}`;
          d += `h ${-(ZenConfig.nodeWidth) + (ZenConfig.nodeWidth / rows) * -(1.5 * 2)}`;
          d += `v ${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * (level - 1) + (ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * 0.5}`;
          d += `L ${this.distNode.x} ${this.distNode.y + ZenConfig.nodeHeight / 2}`;
          // console.log(d);
        }
      } else if (stage === 1) {
        if (level === 0) {
          // console.log('平行移动');
          d += `M ${node.x + ZenConfig.nodeWidth} ${node.y + ZenConfig.nodeHeight / 2}`;
          d += `L ${this.distNode.x} ${this.distNode.y + ZenConfig.nodeHeight / 2}`;
          // console.log(d);
        } else if (level < 0) {
          // console.log('向上移动');
          d += `M ${node.x + ZenConfig.nodeWidth} ${node.y + ZenConfig.nodeHeight / 2}`;
          d += `h ${ZenConfig.nodeMarginH / rows * (-level)}`;
          d += `v ${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * level}`;
          d += `L ${this.distNode.x} ${this.distNode.y + ZenConfig.nodeHeight / 2}`;
          // console.log(d);
        } else {
          // console.log('向下移动');
          d += `M ${node.x + ZenConfig.nodeWidth} ${node.y + ZenConfig.nodeHeight / 2}`;
          d += `h ${ZenConfig.nodeWidth / rows * (level)}`;
          d += `v ${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * level}`;
          d += `L ${this.distNode.x} ${this.distNode.y + ZenConfig.nodeHeight / 2}`;
          // console.log(d);
        }
      } else {
        if (level === 0) {
          // console.log('向上移动');
          d += `M ${node.x + ZenConfig.nodeWidth} ${node.y + ZenConfig.nodeHeight / 2}`;
          d += `h ${ZenConfig.nodeWidth / rows}`;
          d += `v ${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * -0.5}`;
          d += `h ${ZenConfig.nodeWidth * (stage - 1) + ZenConfig.nodeMarginH * (stage) - ZenConfig.nodeWidth / rows * (level + 1) * 2}`;
          d += `v ${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * 0.5}`;
          d += `L ${this.distNode.x} ${this.distNode.y + ZenConfig.nodeHeight / 2}`;
          // console.log(d);
        } else if (level < 0) {
          // console.log('向上移动');
          d += `M ${node.x + ZenConfig.nodeWidth} ${node.y + ZenConfig.nodeHeight / 2}`;
          d += `h ${ZenConfig.nodeWidth / rows * (-level)}`;
          d += `v ${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * level + (ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * 0.5}`;
          d += `h ${ZenConfig.nodeWidth * (stage - 1) + ZenConfig.nodeMarginH * (stage) - ZenConfig.nodeWidth / rows * (-level) * 2}`;
          d += `v -${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * 0.5}`;
          d += `L ${this.distNode.x} ${this.distNode.y + ZenConfig.nodeHeight / 2}`;
          // console.log(d);
        } else {
          // console.log('向下移动');
          d += `M ${node.x + ZenConfig.nodeWidth} ${node.y + ZenConfig.nodeHeight / 2}`;
          d += `h ${ZenConfig.nodeWidth / rows * (level)}`;
          d += `v ${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * level - (ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * 0.5}`;
          d += `h ${ZenConfig.nodeWidth * (stage - 1) + ZenConfig.nodeMarginH * (stage) - ZenConfig.nodeWidth / rows * (level) * 2}`;
          d += `v ${(ZenConfig.nodeMarginV + ZenConfig.nodeHeight) * 0.5}`;
          d += `L ${this.distNode.x} ${this.distNode.y + ZenConfig.nodeHeight / 2}`;
          // console.log(d);
        }
      }

    });

    return d;
  }

  private isOneStage() {
    const set = new Set(this.fromNodes.map(node => node.stage));
    return set.size === 1;
  }

  private getLineOffset() {
    this.fromNodes.map(node => this.zenContext.getNodeRowIndex(node));
  }
}
