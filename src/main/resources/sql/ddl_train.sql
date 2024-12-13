
DROP TABLE IF EXISTS train_info;
CREATE TABLE IF NOT EXISTS train_info (
  train_info_id integer primary key,
  train_no varchar(32),
  station_beg varchar(32),
  station_end varchar(32),
  time_departure time,
  time_arrival time,
  cost_time time;
CREATE INDEX ti_ind_01 on train_info (train_no);
CREATE INDEX ti_ind_02 on train_info (station_beg);
CREATE INDEX ti_ind_03 on train_info (station_end);

DROP TABLE IF EXISTS train_station;
CREATE TABLE IF NOT EXISTS train_station (
  train_station_id integer primary key,
  train_no varchar(32),
  station_seq integer,
  station varchar(32),
  time_departure time,
  time_arrival time;
CREATE INDEX ts_ind_01 on train_station (train_no,station_seq);
CREATE INDEX ts_ind_02 on train_station (station);