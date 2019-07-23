package com.qxy.pojo;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@TableName("tb_item_desc")
public class ItemDesc extends BasePojo{
	//item表id 与itemDecd表Id一致
	@TableId
	private Long itemId;
	private String itemDesc;
}
