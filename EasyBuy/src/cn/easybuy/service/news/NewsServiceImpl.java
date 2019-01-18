package cn.easybuy.service.news;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Service;

import cn.easybuy.dao.news.NewsMapper;
import cn.easybuy.entity.News;
import cn.easybuy.param.NewsParams;


@Service("newsService")
public class NewsServiceImpl implements NewsService {

	@Resource
	private NewsMapper newsMapper;
	
	public void deleteNews(String id) {// 删除新闻
		try {
			newsMapper.deleteById(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public News findNewsById(String id) {// 根据ID获取新闻
		News news = null;
		try {
			news = newsMapper.getNewsById(Integer.parseInt(id));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return news;
	}

	public void addNews(News news) {// 保存新闻
		try {
			newsMapper.add(news);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void updateNews(News news) {// 更新留言
		try {
			newsMapper.update(news);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	
	public List<News> queryNewsPageList(NewsParams param) throws SQLException {
		List<News> newsList=new ArrayList<News>();
		try {
			newsList=newsMapper.queryNewsList(param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return newsList;
	}
	
	@Override
	public List<News> queryNewsList(NewsParams param) {
		List<News> newsList=new ArrayList<News>();
		try {
			newsList=newsMapper.queryNewsList(param);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return newsList;
	}

	@Override
	public Integer queryNewsCount(NewsParams param) {
		Integer count=0;
		try {
			count=newsMapper.queryNewsCount(param);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			return count;
		}
	}

}
