package cn.edu.imufe.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.edu.imufe.po.*;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import cn.edu.imufe.pojo.UserAnswerHistoryPojo;
import cn.edu.imufe.service.*;
import cn.edu.imufe.util.ComparasionOfSqlUtils;
import cn.edu.imufe.util.RandomList;

public  class ServicTest extends BaseTest{
	@Autowired
	UserService userService;
	@Autowired
	RoleService roleService;
	@Autowired
	UserRoleService userRoleService;
	@Autowired
	AnswerService answerService;
	@Autowired
	AnswerHistoryService answerHistoryService;
	@Autowired
	PaperService paperService;
	@Autowired
	PaperAnswerService paperAnswerService;

	private static final String RESULT_MESSAGE_SAME = "Same";
	private static final String RESULT_MESSAGE_DIFFERENT = "Different";
	private static final Integer STATUS_0 = 0;
	private static final Integer STATUS_1 = 1;
	private static final Integer STATUS_2 = 2;


	@Test
	public void testSelectByPrimaryKey() {
		System.out.println(answerService.selectByPrimaryKey(1L));
	}
	@Test
	public void randomlist()
	{
		List<Long> allid = answerService.selectAllId();
		@SuppressWarnings("unchecked")
		List<Integer> list = RandomList.createRandomList(allid, 5);
		for(Integer i:list)
		{
			System.out.println(i);
		}
	}
//	@Test
//	public void alllist()
//	{
//		List<AnswerPojo> allidwithtitle = answerService.selectAllAnswerBaseInfo();
//		for(AnswerPojo i:allidwithtitle)
//		{
//			System.out.println(i.getId());
//			System.out.println(i.getQuestion());
//		}
//	}
	@Test
	public void addanswerhistory() {
			String sql = "select * from lilei;";
			Answer answer = answerService.selectByPrimaryKey(1L);
			String result = ComparasionOfSqlUtils.SQLOfComparasion(answer.getSolution(),sql);
			Integer status = STATUS_0;
			switch(result) {
				case RESULT_MESSAGE_SAME:
					status = STATUS_1;
					break;
				case RESULT_MESSAGE_DIFFERENT:
					status = STATUS_0;
					break;
				default:
					status = STATUS_2;

			}
			AnswerHistory answerhistory = new AnswerHistory();
			AnswerHistory replace = new AnswerHistory();
			replace = answerHistoryService.selectByUserIdAndAnswerId(6L,1L);

			answerhistory.setUserId(6L);
			answerhistory.setAnswerId(1L);
			answerhistory.setUserAnswers(sql);
			answerhistory.setQuestionStatus(status);

			if(replace == null) {
				answerHistoryService.insert(answerhistory);
			}else {
				answerhistory.setId(replace.getId());
				answerHistoryService.updateByPrimaryKey(answerhistory);
			}
	}
	@Test
	public void getRole() {
		User auser = userService.selectByUsername("172101040");
		System.out.println("Id"+auser.getId());
		System.out.println("RoleId"+userRoleService.selectByUserId(auser.getId()).getRoleId());
		System.out.println("RoleName"+roleService.selectByPrimaryKey(userRoleService.selectByUserId(auser.getId()).getRoleId()).getName());
	}

	@Test
	public void getAnswerHistory() {
		List<UserAnswerHistoryPojo> list = answerHistoryService.selectUserAnswerHistory(6L);
		for(UserAnswerHistoryPojo a : list) {
			System.out.println(a.getAnswerId());
			System.out.println(a.getTitle());
			System.out.println(a.getStatus());
		}
	}
	@Test
	public void compareSqlAddHistory() {
		Map<String,Object> modelMap=new HashMap<>();
		String sqlString = "select * from tbl_student;";
		String id = "1";
		if(sqlString!=null && !sqlString.equals(""))
		{
			Answer answer = answerService.selectByPrimaryKey(Long.parseLong(id));
			String result = ComparasionOfSqlUtils.SQLOfComparasion(answer.getSolution(),sqlString);
			int status;
			switch(result) {
				case "Same":
					status = 1;
					break;
				case "Different":
					status = 0;
					break;
				default:
					status = 2;

			}
			AnswerHistory replace;
			replace = answerHistoryService.selectByUserIdAndAnswerId(6L,Long.parseLong(id));

			AnswerHistory answerhistory = new AnswerHistory(null,6L,Long.parseLong(id),
					sqlString,status);

			if(replace == null) {
				answerHistoryService.insert(answerhistory);
			}else {
				answerhistory.setId(replace.getId());
				answerHistoryService.updateByPrimaryKey(answerhistory);
			}

			System.out.println("success");
		}else
		{
			System.out.println("请填写答案！");
		}
	}


}
