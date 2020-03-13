create table tb_app_quartz(
  quartz_id int not null primary key,
  job_name varchar(100) not null comment '任务名称',
  job_group varchar(100) not null comment '任务分组',
  start_time varchar(100) not null comment '任务开始时间',
  cron_expression varchar(100) not null comment 'cron表达式',
  invoke_params varchar(100) comment '需要传递的参数'
);