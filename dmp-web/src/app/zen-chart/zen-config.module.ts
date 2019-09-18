class Config {
  // 节点
  nodeWidth = 120;
  nodeHeight = 50;
  nodeMarginV = 20; // 垂直
  nodeMarginH = 108; // 水平
  // 画布留白：内边距
  svgPaddingLeft = 9;
  svgPaddingTop = 9;
  // 阶段名称 和 节点 之间留白
  stageMarginBottom = 9;

  // 各个阶段名称
  stageHeight = 20;
  stageWidth = this.nodeWidth;
  stageRaw = 'raw';
  stageEtl = 'etl';
  stageT = 't';
  stageSnapshot = 'snapshot';
  stageMid = 'mid';
  stageStats = 'stats';
  stageDefault = this.stageRaw;

  stageRawTips = '原始数据';
  stageEtlTips = '清洗备份';
  stageTTips = 'T级增量';
  stageSnapshotTips = '快照数据';
  stageMidTips = '中间表';
  stageStatsTips = '统计表';
  // 阶段名称提示文本s
  stageNameHeight = 20;
  stageBaseY = this.stageNameHeight;
  // 各个阶段x坐标
  stageRawX = this.svgPaddingLeft;
  stageEtlX = this.svgPaddingLeft + this.nodeWidth + this.nodeMarginH;
  stageTX = this.svgPaddingLeft + this.nodeWidth * 2 + 2 * this.nodeMarginH;
  stageSnapshotX = this.svgPaddingLeft + this.nodeWidth * 3 + 3 * this.nodeMarginH;
  stageMidX = this.svgPaddingLeft + this.nodeWidth * 4 + 4 * this.nodeMarginH;
  stageStatsX = this.svgPaddingLeft + this.nodeWidth * 5 + 5 * this.nodeMarginH;

  constructor() {
  }
}

export const ZenConfig = new Config();

