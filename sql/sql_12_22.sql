create table bilibili.t_ad_space
(
    id          bigint auto_increment comment '主键id'
        primary key,
    description varchar(255) null comment '广告位描述（如页面位置、尺寸等）',
    price       decimal(10, 2) null comment '价格',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '广告位表' charset = utf8mb4;

create table bilibili.t_auth_element_operation
(
    id             bigint auto_increment comment '主键id'
        primary key,
    element_name   varchar(255) null comment '页面元素名称',
    element_code   varchar(50) null comment '页面元素唯一编码',
    operation_type varchar(5) null comment '操作类型：0可点击，1可见',
    create_time    datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time    datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '权限控制--页面元素操作表' charset = utf8;

create table bilibili.t_auth_menu
(
    id          bigint auto_increment comment '主键id'
        primary key,
    name        varchar(255) null comment '菜单项目名称',
    code        varchar(50) null comment '唯一编码',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '权限控制--页面访问表' charset = utf8;

create table bilibili.t_auth_role
(
    id          bigint auto_increment comment '主键id'
        primary key,
    name        varchar(255) null comment '角色名称',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    code        varchar(50) null comment '唯一编码'
) comment '权限控制--角色表' charset = utf8;

create table bilibili.t_auth_role_element_operation
(
    id                   bigint auto_increment comment '主键id'
        primary key,
    role_id              bigint null comment '角色id',
    element_operation_id bigint null comment '元素操作id',
    create_time          datetime default CURRENT_TIMESTAMP null comment '创建时间',
    constraint fk_role_element_operation_element_operation_id
        foreign key (element_operation_id) references bilibili.t_auth_element_operation (id),
    constraint fk_role_element_operation_role_id
        foreign key (role_id) references bilibili.t_auth_role (id)
) comment '权限控制--角色与元素操作关联表' charset = utf8;

create table bilibili.t_auth_role_menu
(
    id          bigint auto_increment comment '主键id'
        primary key,
    role_id     bigint null comment '角色id',
    menu_id     bigint null comment '页面菜单id',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    constraint fk_role_menu_menu_id
        foreign key (menu_id) references bilibili.t_auth_menu (id),
    constraint fk_role_menu_role_id
        foreign key (role_id) references bilibili.t_auth_role (id)
) comment '权限控制--角色页面菜单关联表' charset = utf8;

create table bilibili.t_file
(
    id          bigint auto_increment comment '主键id'
        primary key,
    url         varchar(500) null comment '文件存储路径',
    type        varchar(50) null comment '文件类型',
    md5         varchar(500) null comment '文件MD5唯一标识',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间'
) comment '上传文件信息表' charset = utf8;

create table bilibili.t_following_group
(
    id          bigint auto_increment comment '主键id'
        primary key,
    user_id     bigint null comment '用户id',
    name        varchar(50) null comment '关注分组名称',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    type        varchar(5) null comment '关注分组类型：0特别关注，1悄悄关注，2默认关注，3用户自定义关注'
) comment '用户关注分组表' charset = utf8;

create table bilibili.t_tag
(
    id          bigint auto_increment comment '主键id'
        primary key,
    name        varchar(255) null comment '名称',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间'
) comment '标签表' charset = utf8;

create table bilibili.t_user
(
    id            bigint auto_increment comment '主键id'
        primary key,
    phone         varchar(100) null comment '手机号',
    email         varchar(100) null comment '邮箱',
    user_password varchar(255) null comment '密码',
    salt          varchar(50) null comment '盐值',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间'
) comment '用户表' charset = utf8;

create table bilibili.t_advertisement
(
    id                 bigint auto_increment comment '主键id'
        primary key,
    advertiser_id      bigint not null comment '广告主id',
    content_id         bigint not null comment '广告内容id（关联到t_file表）',
    ad_space_id        bigint not null comment '广告位id（关联到t_ad_space表）',
    start_time         datetime default CURRENT_TIMESTAMP null comment '投放开始时间',
    end_time           datetime default CURRENT_TIMESTAMP null comment '投放结束时间',
    status             varchar(50) null comment '状态（如：激活、暂停、结束）',
    create_time        datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time        datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    advertisement_path varchar(255) charset utf8 null,
    constraint fk_advertisement_ad_space
        foreign key (ad_space_id) references bilibili.t_ad_space (id),
    constraint fk_advertisement_advertiser
        foreign key (advertiser_id) references bilibili.t_user (id),
    constraint fk_advertisement_content
        foreign key (content_id) references bilibili.t_file (id)
) comment '广告表' charset = utf8mb4;

create table bilibili.t_ad_performance
(
    id          bigint auto_increment comment '主键id'
        primary key,
    ad_id       bigint not null comment '广告id（关联到t_advertisement表）',
    clicks      int      default 0 null comment '点击次数',
    impressions int      default 0 null comment '展示次数',
    conversions int      default 0 null comment '转化次数',
    date        date   not null comment '日期',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    constraint fk_ad_performance_advertisement
        foreign key (ad_id) references bilibili.t_advertisement (id)
) comment '广告性能表' charset = utf8mb4;

create table bilibili.t_collection_group
(
    id          bigint auto_increment comment '主键id'
        primary key,
    user_id     bigint null comment '用户id',
    name        varchar(50) null comment '收藏分组名称',
    type        varchar(5) null comment '收藏分组类型：0默认收藏分组',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_collection_group_user_id
        foreign key (user_id) references bilibili.t_user (id)
) comment '用户收藏分组表' charset = utf8;

create table bilibili.t_messages
(
    id          bigint auto_increment comment '主键id'
        primary key,
    sender_id   bigint not null comment '发送者id',
    receiver_id bigint not null comment '接收者id',
    message     text   not null comment '消息内容',
    send_time   datetime default CURRENT_TIMESTAMP null comment '发送时间',
    constraint fk_messages_receiver
        foreign key (receiver_id) references bilibili.t_user (id),
    constraint fk_messages_sender
        foreign key (sender_id) references bilibili.t_user (id)
) comment '消息通信表' charset = utf8mb4;

create table bilibili.t_partnership
(
    id            bigint auto_increment comment '主键id'
        primary key,
    advertiser_id bigint not null comment '广告主id',
    creator_id    bigint not null comment '内容创作者id',
    start_time    datetime default CURRENT_TIMESTAMP null comment '合作开始时间',
    end_time      datetime default CURRENT_TIMESTAMP null comment '合作结束时间',
    content       text null comment '合作内容',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_partnership_advertiser
        foreign key (advertiser_id) references bilibili.t_user (id),
    constraint fk_partnership_creator
        foreign key (creator_id) references bilibili.t_user (id)
) comment '合作关系表' charset = utf8mb4;

create table bilibili.t_partnership_requests
(
    id                    bigint auto_increment comment '合作请求ID'
        primary key,
    advertiser_id         bigint                              not null comment '广告主ID',
    content_creator_id    bigint                              not null comment '内容创作者ID',
    request_time          timestamp default CURRENT_TIMESTAMP not null comment '请求时间',
    status                enum ('pending', 'accepted', 'rejected') default 'pending' not null comment '请求状态（等待、接受、拒绝）',
    details               text charset utf8 null comment '合作详情',
    expected_duration     int(5) null comment '预期的合作期限（以天为单位）',
    expected_compensation decimal(10, 2) null comment '预期的报酬',
    constraint fk_partnership_requests_advertiser
        foreign key (advertiser_id) references bilibili.t_user (id),
    constraint fk_partnership_requests_creator
        foreign key (content_creator_id) references bilibili.t_user (id)
) comment '合作请求表';

create table bilibili.t_refresh_token
(
    id            bigint auto_increment comment '主键id'
        primary key,
    user_id       bigint null comment '用户id',
    refresh_token varchar(500) null comment '刷新令牌',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    constraint fk_refresh_token_user_id
        foreign key (user_id) references bilibili.t_user (id)
) comment '刷新令牌记录表' charset = utf8;

create table bilibili.t_user_coin
(
    id          bigint auto_increment comment '主键id'
        primary key,
    user_id     bigint null comment '用户id',
    amount      bigint null comment '硬币总数',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_user_coin_user_id
        foreign key (user_id) references bilibili.t_user (id)
) comment '用户硬币数量表' charset = utf8;

create table bilibili.t_user_following
(
    id           bigint auto_increment comment '主键id'
        primary key,
    user_id      bigint null comment '用户id',
    following_id bigint null comment '关注用户id',
    group_id     bigint null comment '关注分组id',
    create_time  datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time  datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_user_following_following_id
        foreign key (following_id) references bilibili.t_user (id),
    constraint fk_user_following_group_id
        foreign key (group_id) references bilibili.t_following_group (id),
    constraint fk_user_following_user_id
        foreign key (user_id) references bilibili.t_user (id)
) comment '用户关注表' charset = utf8;

create table bilibili.t_user_info
(
    id          bigint auto_increment comment '主键id'
        primary key,
    user_id     bigint null comment '用户id（关联）',
    nick        varchar(100) null comment '昵称',
    avatar      varchar(1024) null comment '头像',
    sign        text null comment '签名',
    gender      varchar(2) null comment '性别：0男，1女，2未知',
    birth       varchar(20) null comment '生日',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '修改时间',
    constraint fk_user_info_user_id
        foreign key (user_id) references bilibili.t_user (id)
) comment '用户基本信息表' charset = utf8;

create table bilibili.t_user_moments
(
    id          bigint auto_increment comment '主键id'
        primary key,
    user_id     bigint null comment '用户id',
    type        varchar(5) null comment '动态类型：0视频，1直播，2动态专栏',
    content_id  bigint null comment '内容详情id',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_user_moments_user_id
        foreign key (user_id) references bilibili.t_user (id)
) comment '用户动态表' charset = utf8;

create table bilibili.t_user_role
(
    id          bigint auto_increment comment '主键id'
        primary key,
    user_id     bigint null comment '用户id',
    role_id     bigint null comment '角色id',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    constraint fk_user_role_role_id
        foreign key (role_id) references bilibili.t_auth_role (id),
    constraint fk_user_role_user_id
        foreign key (user_id) references bilibili.t_user (id)
) comment '用户角色关联表' charset = utf8;

create table bilibili.t_video
(
    id          bigint auto_increment comment '主键id'
        primary key,
    user_id     bigint       not null comment '用户id',
    url         varchar(500) not null comment '视频链接',
    thumbnail   varchar(500) not null comment '封面链接',
    title       varchar(255) not null comment '视频标题',
    type        varchar(5)   not null comment '视频类型：0原创，1转载',
    duration    varchar(255) not null comment '视频时长',
    area        varchar(255) not null comment '所在分区:0鬼畜，1音乐，2电影',
    description text null comment '视频简介',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    coins       int null comment 'comment ''投币数量''',
    likes       int null comment 'comment ''点赞数量''',
    collections int null comment 'comment ''收藏数量''',
    views       int null comment 'comment ''播放数量''',
    constraint fk_video_user_id
        foreign key (user_id) references bilibili.t_user (id)
) comment '视频投稿记录表' charset = utf8;

create table bilibili.t_danmu
(
    id          bigint auto_increment comment '主键id'
        primary key,
    user_id     bigint null comment '用户id',
    video_id    bigint null comment '视频id',
    content     text null comment '弹幕内容',
    danmu_time  varchar(50) null comment '弹幕出现时间',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    constraint fk_danmu_user_id
        foreign key (user_id) references bilibili.t_user (id),
    constraint fk_danmu_video_id
        foreign key (video_id) references bilibili.t_video (id)
) comment '弹幕表' charset = utf8;

create table bilibili.t_video_coin
(
    id          bigint auto_increment comment '主键id'
        primary key,
    user_id     bigint null comment '用户id',
    video_id    bigint null comment '视频投稿id',
    amount      int null comment '投币数',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_video_coin_user_id
        foreign key (user_id) references bilibili.t_user (id),
    constraint fk_video_coin_video_id
        foreign key (video_id) references bilibili.t_video (id)
) comment '视频投币记录表' charset = utf8;

create table bilibili.t_video_collection
(
    id          bigint auto_increment comment '主键id'
        primary key,
    video_id    bigint null comment '视频投稿id',
    user_id     bigint null comment '用户id',
    group_id    bigint null comment '收藏分组',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    constraint fk_video_collection_group_id
        foreign key (group_id) references bilibili.t_collection_group (id),
    constraint fk_video_collection_user_id
        foreign key (user_id) references bilibili.t_user (id),
    constraint fk_video_collection_video_id
        foreign key (video_id) references bilibili.t_video (id)
) comment '视频收藏表' charset = utf8;

create table bilibili.t_video_comment
(
    id            bigint auto_increment comment '主键id'
        primary key,
    video_id      bigint not null comment '视频id',
    user_id       bigint not null comment '用户id',
    comment       text   not null comment '评论',
    reply_user_id bigint null comment '回复用户id',
    root_id       bigint null comment '根结点评论id',
    create_time   datetime default CURRENT_TIMESTAMP null comment '创建时间',
    update_time   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP comment '更新时间',
    constraint fk_video_comment_reply_user_id
        foreign key (reply_user_id) references bilibili.t_user (id),
    constraint fk_video_comment_user_id
        foreign key (user_id) references bilibili.t_user (id),
    constraint fk_video_comment_video_id
        foreign key (video_id) references bilibili.t_video (id)
) comment '视频评论表' charset = utf8;

create table bilibili.t_video_like
(
    id          bigint auto_increment comment '主键id'
        primary key,
    user_id     bigint not null comment '用户id',
    video_id    bigint not null comment '视频投稿id',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    constraint fk_video_like_user_id
        foreign key (user_id) references bilibili.t_user (id),
    constraint fk_video_like_video_id
        foreign key (video_id) references bilibili.t_video (id)
) comment '视频点赞表' charset = utf8;

create table bilibili.t_video_operation
(
    id             bigint auto_increment comment '主键id'
        primary key,
    user_id        bigint null comment '用户id',
    video_id       bigint null comment '视频id',
    operation_type varchar(5) null comment '操作类型:0点赞，1收藏，2投币',
    create_time    datetime default CURRENT_TIMESTAMP null comment '创建时间',
    constraint fk_video_operation_user_id
        foreign key (user_id) references bilibili.t_user (id),
    constraint fk_video_operation_video_id
        foreign key (video_id) references bilibili.t_video (id)
) comment '用户操作表' charset = utf8;

create table bilibili.t_video_tag
(
    id          bigint auto_increment comment '主键id'
        primary key,
    video_id    bigint not null comment '视频id',
    tag_id      bigint not null comment '标签id',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    constraint fk_video_tag_tag_id
        foreign key (tag_id) references bilibili.t_tag (id),
    constraint fk_video_tag_video_id
        foreign key (video_id) references bilibili.t_video (id)
) comment '视频标签关联表' charset = utf8;

create table bilibili.t_video_view
(
    id          bigint auto_increment comment '主键id'
        primary key,
    video_id    bigint not null comment '视频id',
    user_id     bigint null comment '用户id',
    client_id   varchar(500) null comment '客户端id',
    ip          varchar(50) null comment 'ip',
    create_time datetime default CURRENT_TIMESTAMP null comment '创建时间',
    constraint fk_video_view_user_id
        foreign key (user_id) references bilibili.t_user (id),
    constraint fk_video_view_video_id
        foreign key (video_id) references bilibili.t_video (id)
) comment '视频观看记录表' charset = utf8;

