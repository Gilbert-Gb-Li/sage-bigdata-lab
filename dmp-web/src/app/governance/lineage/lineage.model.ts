export class Lineage {
  fromNodes: number[];
  distNode: number;

  constructor(fromNodes: number[],
              distNode: number) {
    this.fromNodes = fromNodes;
    this.distNode = distNode;
  }
}
