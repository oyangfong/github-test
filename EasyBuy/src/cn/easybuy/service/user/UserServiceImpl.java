package cn.easybuy.service.user;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.Resource;

import cn.easybuy.dao.user.UserMapper;
import cn.easybuy.utils.Pager;

import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.easybuy.entity.User;

@Service("userService")
public class UserServiceImpl implements UserService {
	
	private Logger logger = Logger.getLogger(UserServiceImpl.class);
	
	@Resource
	private UserMapper userMapper;
	
	@Override
	public boolean add(User user){
		Integer count=0;
		try {
			count=userMapper.add(user);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return  count>0;
		}
	}

	@Override
	public boolean update(User user) {
		Integer count=0;
		try {
			count=userMapper.update(user);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return  count>0;
		}
	}

	@Override
	public boolean deleteUserById(Integer userId) {
		Integer count=0;
		try {
			count=userMapper.deleteUserById(userId+"");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return  count>0;
		}
	}

	@Override
	public User getUser(Integer id, String loginName) {
		User user=null;
		try {
			user=userMapper.getUser(id,loginName);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return user;
		}
	}

	@Override
	public List<User> getUserList(Integer currentPageNo, Integer pageSize) {
		List<User> userList=null;
		try {
			int total = count();
			Pager pager = new Pager(total, pageSize, currentPageNo);
			userList=userMapper.getUserList((pager.getCurrentPage() - 1) * pager.getRowPerPage(), pager.getRowPerPage());
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return userList;
		}
	}

	@Override
	public int count() {
		Integer count=null;
		try {
			count=userMapper.count();
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			return count;
		}
	}
}
