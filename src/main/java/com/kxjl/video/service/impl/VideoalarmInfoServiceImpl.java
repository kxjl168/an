package com.kxjl.video.service.impl;



import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.kxjl.base.util.ExceptionUntil;
import com.kxjl.base.util.UUIDUtil;
import com.kxjl.video.dao.VideoalarmInfoMapper;
import com.kxjl.video.pojo.VideoalarmInfo;
import com.kxjl.video.service.VideoalarmInfoService;

import java.util.*;

@Service
public class VideoalarmInfoServiceImpl implements VideoalarmInfoService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private VideoalarmInfoMapper itemMapper;
	
	@Autowired
	private RedisTemplate redisTemplate;

	/**
	 * 新增或者删除附件url。  附件通过picture_url 字段传入。
	 * 比较    /**

  picture_url;


   video_url;

  
  audio_url; 数据
	 * @param actionType add,remove
	 * @param item
	 * @return
	 * @author zj
	 * @date 2019年11月26日
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject editVideoalarmFile(String actionType,VideoalarmInfo item) {

		JSONObject rtn = new JSONObject();


		try {

			VideoalarmInfo info=selectVideoalarmInfoById(item.getId());
			if(actionType.equals("add"))
			{
				if(item.getPicture_url().endsWith(".mp4")||item.getPicture_url().endsWith(".m4a"))
				{
					if(info.getVideo_url()==null)
						info.setVideo_url("");
					
					 if(!info.getVideo_url().contains(item.getPicture_url()))
					info.setVideo_url(info.getVideo_url()+","+item.getPicture_url());
				}
				else if(item.getPicture_url().endsWith(".mp3"))
				{
					if(info.getAudio_url()==null)
						info.setAudio_url("");
					
					 if(!info.getAudio_url().contains(item.getPicture_url()))
					info.setAudio_url(info.getAudio_url()+","+item.getPicture_url());
				}
				else
				{
					if(info.getPicture_url()==null)
						info.setPicture_url("");
					
					 if(!info.getPicture_url().contains(item.getPicture_url()))
					{
					info.setPicture_url(info.getPicture_url()+","+item.getPicture_url());
					}
				}
				
				itemMapper.updateByPrimaryKeySelective(info);
			}
			else
			{
				

				if(info.getVideo_url()!=null)
					info.setVideo_url(info.getVideo_url().replace(item.getPicture_url(),""));
				if(info.getAudio_url()!=null)
					info.setAudio_url(info.getAudio_url().replace(item.getPicture_url(),""));
				if(info.getPicture_url()!=null)
					info.setPicture_url(info.getPicture_url().replace(item.getPicture_url(),""));
				
				itemMapper.updateByPrimaryKeySelective(info);
			}
			


			rtn.put("bol", true);

			return rtn;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("新增失败", e);
			rtn.put("bol", false);
			rtn.put("message", "新增失败");
			log.error(ExceptionUntil.getMessage(e));
			return rtn;
		}
	}
	
	
	/**
	 * @param item
	 * @return
	 */
	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject saveVideoalarmInfo(VideoalarmInfo item) {
		JSONObject rtn = new JSONObject();


		try {

			item.setId(	UUIDUtil. getLongUUID(redisTemplate));
			

			itemMapper.insertSelective(item);

			rtn.put("bol", true);

			return rtn;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("新增失败", e);
			rtn.put("bol", false);
			rtn.put("message", "新增失败");
			log.error(ExceptionUntil.getMessage(e));
			return rtn;
		}
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public JSONObject updateVideoalarmInfo(VideoalarmInfo item) {
		JSONObject rtn = new JSONObject();

		 if (item.getId()==null) 
		 {
			rtn.put("bol", false);
			rtn.put("message", "id为空");
			return rtn;
		}

		try {
			itemMapper.updateByPrimaryKeySelective(item);

			rtn.put("bol", true);

			return rtn;
		} catch (Exception e) {
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			log.error("更新出错", e);
			rtn.put("bol", false);
			rtn.put("message", "更新出错");
			return rtn;
		}
	}

	@Override
	public List<VideoalarmInfo> selectVideoalarmInfoList(VideoalarmInfo item) {
		List<VideoalarmInfo> itemList = new ArrayList<>();
		try {
			itemList = itemMapper.selectList(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("查询列表出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return itemList;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public int deleteVideoalarmInfo(VideoalarmInfo item) {
		int result = 0;
		try {

			result = itemMapper.delete(item);
		} catch (Exception e) {
			e.printStackTrace();
			log.error("删除出错");
			log.error(ExceptionUntil.getMessage(e));
		}
		return result;
	}

	@Override


	public VideoalarmInfo selectVideoalarmInfoById(Long id) {
		VideoalarmInfo data = null;

		VideoalarmInfo query = new VideoalarmInfo();
		query.setId(id);

		List<VideoalarmInfo> datas = selectVideoalarmInfoList(query);
		if (datas != null && datas.size() > 0) {
			data = datas.get(0);

		}
		return data;

	}

}
