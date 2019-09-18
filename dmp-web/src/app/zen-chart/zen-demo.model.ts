import {Storage} from '../governance/storage/storage.service';
import {Lineage} from '../governance/lineage/lineage.model';

function newStorage(id: number, name: string, stage: string): Storage {
  const storage = new Storage();
  storage.id = id;
  storage.name = name;
  storage.stage = stage;
  return storage;
}

export const ZenDemo = {
  storages: [
    newStorage(11, 'kafka topic1', 'raw'),

    newStorage(21, '用户', 'etl'),
    newStorage(22, '视频', 'etl'),
    newStorage(23, '话题', 'etl'),
    newStorage(24, '视频评论', 'etl'),
    newStorage(25, '粉丝关注', 'etl'),
    newStorage(26, '首页推荐', 'etl'),
    newStorage(27, '热门话题', 'etl'),
    newStorage(28, '热门音乐', 'etl'),
    newStorage(29, '热门推荐详情', 'etl'),
    newStorage(210, '热门推荐排行', 'etl'),
    newStorage(211, '热门搜索榜单', 'etl'),
    newStorage(212, '音乐', 'etl'),
    newStorage(213, '商品列表', 'etl'),
    newStorage(214, '视频页用户信息', 'etl'),

    newStorage(31, '用户ORC', 't'),
    newStorage(32, '视频ORC', 't'),
    newStorage(33, '话题ORC', 't'),
    newStorage(34, '视频评论ORC', 't'),
    newStorage(35, '粉丝关注ORC', 't'),
    newStorage(36, '首页推荐ORC', 't'),
    newStorage(37, '热门话题ORC', 't'),
    newStorage(38, '热门音乐ORC', 't'),
    newStorage(39, '热门推荐详情ORC', 't'),
    newStorage(310, '热门推荐排行ORC', 't'),
    newStorage(311, '热门搜索榜单ORC', 't'),
    newStorage(312, '音乐ORC', 't'),
    newStorage(313, '商品列表ORC', 't'),
    newStorage(314, '视频页用户信息ORC', 't'),
    newStorage(315, '橱窗ORC', 't'),


    newStorage(41, '用户', 'snapshot'),
    newStorage(42, '视频', 'snapshot'),
    newStorage(43, '话题', 'snapshot'),
    newStorage(44, '视频评论', 'snapshot'),
    newStorage(45, '粉丝关注', 'snapshot'),
    newStorage(46, '首页推荐去重', 'snapshot'),
    newStorage(47, '热门话题', 'snapshot'),
    newStorage(48, '热门音乐', 'snapshot'),
    newStorage(49, '热门推荐详情', 'snapshot'),
    newStorage(410, '热门搜索榜单', 'snapshot'),
    newStorage(411, '音乐', 'snapshot'),
    newStorage(412, '商品列表', 'snapshot'),
    newStorage(413, '视频页用户信息', 'snapshot'),
    newStorage(414, '橱窗', 'snapshot'),

    newStorage(51, '用户抽样/头部集', 'mid'),
    newStorage(52, '视频抽样/头部集', 'mid'),
    newStorage(53, '用户全量集', 'mid'),
    newStorage(54, '视频全量集', 'mid'),
    newStorage(55, '用户头部集', 'mid'),
    newStorage(56, '用户抽样集', 'mid'),

    newStorage(61, '用户留存T/R（日、周、月）', 'stats'),
    newStorage(62, '用户留存ALL（月）', 'stats'),
    newStorage(63, '热门话题', 'stats'),
    newStorage(64, '热门分类', 'stats'),
    newStorage(65, '日报统计', 'stats')
  ],
  lines: [
    new Lineage([11], 21),
    new Lineage([11], 22),
    new Lineage([11], 23),
    new Lineage([11], 24),
    new Lineage([11], 25),
    new Lineage([11], 26),
    new Lineage([11], 27),
    new Lineage([11], 28),
    new Lineage([11], 29),
    new Lineage([11], 210),
    new Lineage([11], 211),
    new Lineage([11], 212),
    new Lineage([11], 213),
    new Lineage([11], 214),

    new Lineage([21], 31),
    new Lineage([22], 32),
    new Lineage([23], 33),
    new Lineage([24], 34),
    new Lineage([25], 35),
    new Lineage([26], 36),
    new Lineage([27], 37),
    new Lineage([28], 38),
    new Lineage([29], 39),
    new Lineage([210], 310),
    new Lineage([211], 311),
    new Lineage([212], 312),
    new Lineage([213], 313),
    new Lineage([214], 314),
    new Lineage([213], 315),


    new Lineage([31, 414, 42, 41], 41),
    new Lineage([32, 49, 42], 42),
    new Lineage([33, 43, 42, 37], 43),
    new Lineage([34, 44], 44),
    new Lineage([35, 45], 45),
    new Lineage([36], 46),
    new Lineage([37, 42, 47], 47),
    new Lineage([38, 42, 48], 48),
    new Lineage([39, 310, 49], 49),
    new Lineage([311, 410], 410),
    new Lineage([312, 37, 42, 411], 411),
    new Lineage([313, 412], 412),
    new Lineage([314, 413], 413),
    new Lineage([315, 414], 414),

    new Lineage([41, 42, 44, 412, 55, 56], 51),
    new Lineage([42, 49, 55, 56], 52),
    new Lineage([41, 42, 44, 412], 53),
    new Lineage([42], 54),
    new Lineage([41], 55),
    new Lineage([41], 56),

    new Lineage([51], 61),
    new Lineage([53], 62),
    new Lineage([47], 63),
    new Lineage([42], 64),
    new Lineage([41], 65),
    new Lineage([42], 65)
  ]
};
