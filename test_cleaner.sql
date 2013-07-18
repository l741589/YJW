use yjw_db;
SET SQL_SAFE_UPDATES=0; 
delete from yjw_img where timestamps>'2013-07-18 00:00:00';
delete from yjw_data where timestamps>'2013-07-18 00:00:00';
delete from yjw_user where timestamps>'2013-07-18 00:00:00';