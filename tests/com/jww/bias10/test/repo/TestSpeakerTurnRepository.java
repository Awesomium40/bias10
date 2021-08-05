package com.jww.bias10.test.repo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.sql.Statement;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jww.bias10.model.Interview;
import com.jww.bias10.model.SpeakerTurn;
import com.jww.bias10.repo.SpeakerTurnRepository;

public class TestSpeakerTurnRepository extends BaseRepositoryTest
{
	private SpeakerTurnRepository repo;
	private Integer interviewId;
	private String role = "TR1";
	private String roleDesc = "TEST ROLE 1";
	
	@Before
	public void setUp() throws ClassNotFoundException, SQLException
	{
		this.repo = new SpeakerTurnRepository(con);
		PreparedStatement ps = con.prepareStatement("INSERT INTO interview (interview_name) VALUES ('TEST_INTERVIEW')", Statement.RETURN_GENERATED_KEYS);
		PreparedStatement ps2 = con.prepareStatement("INSERT INTO speaker_role (speaker_role, speaker_description) VALUES (?, ?)");
		ps2.setString(1, this.role);
		ps2.setString(2, this.roleDesc);
		ps.executeUpdate();
		ResultSet r = ps.getGeneratedKeys();
		r.next();
		this.interviewId = r.getInt(1);
		ps2.executeUpdate();
		con.commit();
	}
	
	@After
	public void tearDown() throws SQLException
	{
		PreparedStatement ps = con.prepareStatement("DELETE FROM interview");
		PreparedStatement ps2 = con.prepareStatement("DELETE FROM speaker_role");
		ps.executeUpdate();
		ps2.executeUpdate();
		con.commit();
	}
	
	@Test
	public void testInsert() throws SQLException
	{
		//Test that St with null data cannot be inserted
		SpeakerTurn s = new SpeakerTurn();
		assertThrows(SQLException.class, () -> repo.insert(s));
		con.rollback();
		
		s.setInterviewId(this.interviewId);
		assertThrows(SQLException.class, () -> repo.insert(s));
		con.rollback();
		
		s.setLine(1);
		assertThrows(SQLException.class, () -> repo.insert(s));
		con.rollback();
		
		s.setText("TEST_ST_TEXT");
		
		assertDoesNotThrow(() -> repo.insert(s));
		con.rollback();
		
		//Test that ST with invalid FK on interview cannot be inserted
		s.setSpeakerRole("TR2");
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> repo.insert(s));
		
		SpeakerTurn s2 = new SpeakerTurn();
		s2.setInterviewId(this.interviewId);
		s2.setSpeakerRole(this.role);
		s2.setText("TEST_SR_TEXT");
		s2.setLine(1);
		s2.setInterviewId(99);
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> repo.insert(s2));
		con.rollback();
		
		//Test that valid insertion succeeds
		s2.setInterviewId(this.interviewId);
		assertDoesNotThrow(() -> repo.insert(s2));
		con.rollback();
		
	}
	
	@Test 
	public void testGet() throws SQLException
	{
		Interview iv = new Interview();
		iv.setId(99);
		PreparedStatement ps;
		
		//Test that retrieving a ST with bad PK yields no result
		assertNull(repo.get(99));
		
		//Test that retrieving ST with good PK yields result
		SpeakerTurn s = new SpeakerTurn();
		s.setInterviewId(this.interviewId);
		s.setLine(1);
		s.setSpeakerRole(this.role);
		s.setText("TEST_TEXT");
		assertDoesNotThrow(() -> repo.insert(s));
		con.rollback();
		
		//Test that retrieving an ST with bad FK for interview yields no result
		assertEquals(0, repo.getByInterview(iv.getId()).size());
		assertEquals(0, repo.getByInterview(iv).size());
		
		//Test that retrieval of ST with good FK yields result
		iv.setId(this.interviewId);
		for(int i = 0; i < 3; i++)
		{
			SpeakerTurn s2 = new SpeakerTurn();
			s2.setInterviewId(this.interviewId);
			s2.setLine(i);
			s2.setSpeakerRole(this.role);
			s2.setText(String.format("TEST_LINE_%s", i));
			assertDoesNotThrow(() -> repo.insert(s2));
		}
		
		assertEquals(repo.getByInterview(iv).size(), 2);
		assertEquals(repo.getByInterview(iv.getId()).size(), 2);
		assertEquals(repo.getAll().size(), 2);
		con.rollback();
		
	}
	
	@Test
	public void testUpdate() throws SQLException
	{
		//Test that updating to a bad FK on interview fails
		SpeakerTurn s = new SpeakerTurn();
		s.setInterviewId(this.interviewId);
		s.setSpeakerRole(this.role);
		s.setLine(1);
		s.setText("TEST_TEXT");
		
		assertDoesNotThrow(() -> repo.insert(s));
		s.setInterviewId(0);
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> repo.insert(s));
		con.rollback();
		
		//Test that updating to a bad FK on speaker_role fails
		s.setInterviewId(this.interviewId);
		assertDoesNotThrow(() -> s.setId(repo.insert(s)));
		
		s.setSpeakerRole("ABC");
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> repo.update(s));
		con.rollback();
		s.setSpeakerRole(this.role);
		
		//Test that updating to set interview_id to null fails
		assertDoesNotThrow(() -> s.setId(repo.insert(s)));
		s.setInterviewId(null);
		assertThrows(SQLIntegrityConstraintViolationException.class, () -> repo.update(s));
		con.rollback();
		
		//Test that updating to set role null works
		s.setSpeakerRole(this.role);
		s.setInterviewId(this.interviewId);
		assertDoesNotThrow(() -> s.setId(repo.insert(s).intValue()));
		
		s.setSpeakerRole(null);
		assertDoesNotThrow(() -> assertEquals(1, repo.update(s).intValue()));
	}
	
	@Test
	public void testDelete() throws SQLException
	{
		//Test that deleting an ST with non-existant PK yields no results
		SpeakerTurn st = new SpeakerTurn();
		st.setInterviewId(this.interviewId);
		st.setSpeakerRole(this.role);
		st.setLine(1);
		st.setText("TEST_TEXT");
		
		assertDoesNotThrow(() -> st.setId(repo.insert(st)));
		assertNotNull(st.getId());
		
		st.setId(0);
		assertEquals(repo.delete(st).intValue(), 0);
		con.rollback();
		
		//Test taht deleting an ST with valid FK works
		st.setInterviewId(this.interviewId);
		st.setSpeakerRole(this.role);
		st.setLine(1);
		st.setText("TEST_TEXT");
		
		assertDoesNotThrow(() -> st.setId(repo.insert(st)));
		assertEquals(repo.delete(st).intValue(), 1);
		
	}
}
