CREATE TABLE IF NOT EXISTS bilibili.t_ad_space (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    description varchar(255) NULL comment '广告位描述（如页面位置、尺寸等）',
    price decimal(10, 2) NULL comment '价格',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间') comment '广告位表' charset = utf8mb4;

CREATE TABLE IF NOT EXISTS bilibili.t_auth_element_operation (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    element_name varchar(255) NULL comment '页面元素名称',
    element_code varchar(50) NULL comment '页面元素唯一编码',
    operation_type varchar(5) NULL comment '操作类型：0可点击，1可见',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间') comment '权限控制--页面元素操作表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_auth_menu (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    name varchar(255) NULL comment '菜单项目名称',
    code varchar(50) NULL comment '唯一编码',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间') comment '权限控制--页面访问表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_auth_role (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    name varchar(255) NULL comment '角色名称',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    code varchar(50) NULL comment '唯一编码') comment '权限控制--角色表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_auth_role_element_operation (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    role_id bigint NULL comment '角色id',
    element_operation_id bigint NULL comment '元素操作id',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    CONSTRAINT fk_role_element_operation_element_operation_id FOREIGN KEY (element_operation_id) REFERENCES bilibili.t_auth_element_operation (id),
    CONSTRAINT fk_role_element_operation_role_id FOREIGN KEY (role_id) REFERENCES bilibili.t_auth_role (id)) comment '权限控制--角色与元素操作关联表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_auth_role_menu (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    role_id bigint NULL comment '角色id',
    menu_id bigint NULL comment '页面菜单id',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    CONSTRAINT fk_role_menu_menu_id FOREIGN KEY (menu_id) REFERENCES bilibili.t_auth_menu (id),
    CONSTRAINT fk_role_menu_role_id FOREIGN KEY (role_id) REFERENCES bilibili.t_auth_role (id)) comment '权限控制--角色页面菜单关联表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_file (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    url varchar(500) NULL comment '文件存储路径',
    type varchar(50) NULL comment '文件类型',
    md5 varchar(500) NULL comment '文件MD5唯一标识',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间') comment '上传文件信息表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_following_group (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    user_id bigint NULL comment '用户id',
    name varchar(50) NULL comment '关注分组名称',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    type varchar(5) NULL comment '关注分组类型：0特别关注，1悄悄关注，2默认关注，3用户自定义关注') comment '用户关注分组表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_tag (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    name varchar(255) NULL comment '名称',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间') comment '标签表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_user (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    phone varchar(100) NULL comment '手机号',
    email varchar(100) NULL comment '邮箱',
    user_password varchar(255) NULL comment '密码',
    salt varchar(50) NULL comment '盐值',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间') comment '用户表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_advertisement (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    advertiser_id bigint NOT NULL comment '广告主id',
    content_id bigint NOT NULL comment '广告内容id（关联到t_file表）',
    ad_space_id bigint NOT NULL comment '广告位id（关联到t_ad_space表）',
    start_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '投放开始时间',
    end_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '投放结束时间',
    status varchar(50) NULL comment '状态（如：激活、暂停、结束）',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    advertisement_path varchar(255) charset utf8 NULL,
    CONSTRAINT fk_advertisement_ad_space FOREIGN KEY (ad_space_id) REFERENCES bilibili.t_ad_space (id),
    CONSTRAINT fk_advertisement_advertiser FOREIGN KEY (advertiser_id) REFERENCES bilibili.t_user (id),
    CONSTRAINT fk_advertisement_content FOREIGN KEY (content_id) REFERENCES bilibili.t_file (id)) comment '广告表' charset = utf8mb4;

CREATE TABLE IF NOT EXISTS bilibili.t_ad_performance (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    ad_id bigint NOT NULL comment '广告id（关联到t_advertisement表）',
    clicks int DEFAULT 0 NULL comment '点击次数',
    impressions int DEFAULT 0 NULL comment '展示次数',
    conversions int DEFAULT 0 NULL comment '转化次数',
    date date NOT NULL comment '日期',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    CONSTRAINT fk_ad_performance_advertisement FOREIGN KEY (ad_id) REFERENCES bilibili.t_advertisement (id)) comment '广告性能表' charset = utf8mb4;

CREATE TABLE IF NOT EXISTS bilibili.t_collection_group (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    user_id bigint NULL comment '用户id',
    name varchar(50) NULL comment '收藏分组名称',
    type varchar(5) NULL comment '收藏分组类型：0默认收藏分组',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    CONSTRAINT fk_collection_group_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id)) comment '用户收藏分组表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_messages (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    sender_id bigint NOT NULL comment '发送者id',
    receiver_id bigint NOT NULL comment '接收者id',
    message text NOT NULL comment '消息内容',
    send_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '发送时间',
    CONSTRAINT fk_messages_receiver FOREIGN KEY (receiver_id) REFERENCES bilibili.t_user (id),
    CONSTRAINT fk_messages_sender FOREIGN KEY (sender_id) REFERENCES bilibili.t_user (id)) comment '消息通信表' charset = utf8mb4;

CREATE TABLE IF NOT EXISTS bilibili.t_partnership (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    advertiser_id bigint NOT NULL comment '广告主id',
    creator_id bigint NOT NULL comment '内容创作者id',
    start_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '合作开始时间',
    end_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '合作结束时间',
    content text NULL comment '合作内容',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    CONSTRAINT fk_partnership_advertiser FOREIGN KEY (advertiser_id) REFERENCES bilibili.t_user (id),
    CONSTRAINT fk_partnership_creator FOREIGN KEY (creator_id) REFERENCES bilibili.t_user (id)) comment '合作关系表' charset = utf8mb4;

CREATE TABLE IF NOT EXISTS bilibili.t_partnership_requests (
    id bigint AUTO_INCREMENT comment '合作请求ID' PRIMARY KEY,
    advertiser_id bigint NOT NULL comment '广告主ID',
    content_creator_id bigint NOT NULL comment '内容创作者ID',
    request_time timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL comment '请求时间',
    status enum ('pending', 'accepted', 'rejected') DEFAULT 'pending' NOT NULL comment '请求状态（等待、接受、拒绝）',
    details text charset utf8 NULL comment '合作详情',
    expected_duration int(5) NULL comment '预期的合作期限（以天为单位）',
    expected_compensation decimal(10, 2) NULL comment '预期的报酬',
    CONSTRAINT fk_partnership_requests_advertiser FOREIGN KEY (advertiser_id) REFERENCES bilibili.t_user (id),
    CONSTRAINT fk_partnership_requests_creator FOREIGN KEY (content_creator_id) REFERENCES bilibili.t_user (id)) comment '合作请求表';

CREATE TABLE IF NOT EXISTS bilibili.t_refresh_token (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    user_id bigint NULL comment '用户id',
    refresh_token varchar(500) NULL comment '刷新令牌',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    CONSTRAINT fk_refresh_token_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id)) comment '刷新令牌记录表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_user_coin (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    user_id bigint NULL comment '用户id',
    amount bigint NULL comment '硬币总数',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    CONSTRAINT fk_user_coin_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id)) comment '用户硬币数量表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_user_following (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    user_id bigint NULL comment '用户id',
    following_id bigint NULL comment '关注用户id',
    group_id bigint NULL comment '关注分组id',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    CONSTRAINT fk_user_following_following_id FOREIGN KEY (following_id) REFERENCES bilibili.t_user (id),
    CONSTRAINT fk_user_following_group_id FOREIGN KEY (group_id) REFERENCES bilibili.t_following_group (id),
    CONSTRAINT fk_user_following_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id)) comment '用户关注表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_user_info (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    user_id bigint NULL comment '用户id（关联）',
    nick varchar(100) NULL comment '昵称',
    avatar varchar(1024) NULL comment '头像',
    sign text NULL comment '签名',
    gender varchar(2) NULL comment '性别：0男，1女，2未知',
    birth varchar(20) NULL comment '生日',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '修改时间',
    CONSTRAINT fk_user_info_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id)) comment '用户基本信息表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_user_moments (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    user_id bigint NULL comment '用户id',
    type varchar(5) NULL comment '动态类型：0视频，1直播，2动态专栏',
    content_id bigint NULL comment '内容详情id',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    CONSTRAINT fk_user_moments_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id)) comment '用户动态表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_user_role (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    user_id bigint NULL comment '用户id',
    role_id bigint NULL comment '角色id',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    CONSTRAINT fk_user_role_role_id FOREIGN KEY (role_id) REFERENCES bilibili.t_auth_role (id),
    CONSTRAINT fk_user_role_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id)) comment '用户角色关联表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_video (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    user_id bigint NOT NULL comment '用户id',
    url varchar(500) NOT NULL comment '视频链接',
    thumbnail varchar(500) NOT NULL comment '封面链接',
    title varchar(255) NOT NULL comment '视频标题',
    type varchar(5) NOT NULL comment '视频类型：0原创，1转载',
    duration varchar(255) NOT NULL comment '视频时长',
    area varchar(255) NOT NULL comment '所在分区:0鬼畜，1音乐，2电影',
    description text NULL comment '视频简介',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    CONSTRAINT fk_video_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id)) comment '视频投稿记录表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_danmu (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    user_id bigint NULL comment '用户id',
    video_id bigint NULL comment '视频id',
    content text NULL comment '弹幕内容',
    danmu_time varchar(50) NULL comment '弹幕出现时间',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    CONSTRAINT fk_danmu_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id),
    CONSTRAINT fk_danmu_video_id FOREIGN KEY (video_id) REFERENCES bilibili.t_video (id)) comment '弹幕表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_video_coin (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    user_id bigint NULL comment '用户id',
    video_id bigint NULL comment '视频投稿id',
    amount int NULL comment '投币数',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    CONSTRAINT fk_video_coin_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id),
    CONSTRAINT fk_video_coin_video_id FOREIGN KEY (video_id) REFERENCES bilibili.t_video (id)) comment '视频投币记录表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_video_collection (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    video_id bigint NULL comment '视频投稿id',
    user_id bigint NULL comment '用户id',
    group_id bigint NULL comment '收藏分组',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    CONSTRAINT fk_video_collection_group_id FOREIGN KEY (group_id) REFERENCES bilibili.t_collection_group (id),
    CONSTRAINT fk_video_collection_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id),
    CONSTRAINT fk_video_collection_video_id FOREIGN KEY (video_id) REFERENCES bilibili.t_video (id)) comment '视频收藏表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_video_comment (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    video_id bigint NOT NULL comment '视频id',
    user_id bigint NOT NULL comment '用户id',
    comment text NOT NULL comment '评论',
    reply_user_id bigint NULL comment '回复用户id',
    root_id bigint NULL comment '根结点评论id',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    update_time datetime DEFAULT CURRENT_TIMESTAMP NULL ON UPDATE CURRENT_TIMESTAMP comment '更新时间',
    CONSTRAINT fk_video_comment_reply_user_id FOREIGN KEY (reply_user_id) REFERENCES bilibili.t_user (id),
    CONSTRAINT fk_video_comment_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id),
    CONSTRAINT fk_video_comment_video_id FOREIGN KEY (video_id) REFERENCES bilibili.t_video (id)) comment '视频评论表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_video_like (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    user_id bigint NOT NULL comment '用户id',
    video_id bigint NOT NULL comment '视频投稿id',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    CONSTRAINT fk_video_like_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id),
    CONSTRAINT fk_video_like_video_id FOREIGN KEY (video_id) REFERENCES bilibili.t_video (id)) comment '视频点赞表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_video_operation (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    user_id bigint NULL comment '用户id',
    video_id bigint NULL comment '视频id',
    operation_type varchar(5) NULL comment '操作类型:0点赞，1收藏，2投币',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    CONSTRAINT fk_video_operation_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id),
    CONSTRAINT fk_video_operation_video_id FOREIGN KEY (video_id) REFERENCES bilibili.t_video (id)) comment '用户操作表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_video_tag (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    video_id bigint NOT NULL comment '视频id',
    tag_id bigint NOT NULL comment '标签id',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    CONSTRAINT fk_video_tag_tag_id FOREIGN KEY (tag_id) REFERENCES bilibili.t_tag (id),
    CONSTRAINT fk_video_tag_video_id FOREIGN KEY (video_id) REFERENCES bilibili.t_video (id)) comment '视频标签关联表' charset = utf8;

CREATE TABLE IF NOT EXISTS bilibili.t_video_view (
    id bigint AUTO_INCREMENT comment '主键id' PRIMARY KEY,
    video_id bigint NOT NULL comment '视频id',
    user_id bigint NULL comment '用户id',
    client_id varchar(500) NULL comment '客户端id',
    ip varchar(50) NULL comment 'ip',
    create_time datetime DEFAULT CURRENT_TIMESTAMP NULL comment '创建时间',
    CONSTRAINT fk_video_view_user_id FOREIGN KEY (user_id) REFERENCES bilibili.t_user (id),
    CONSTRAINT fk_video_view_video_id FOREIGN KEY (video_id) REFERENCES bilibili.t_video (id)) comment '视频观看记录表' charset = utf8;
