package cn.lyc.common.api.enity;

import java.sql.Timestamp;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
public class BaseEntity {
	
	private Integer id;
	private Timestamp created;
	private Timestamp updated;

}
